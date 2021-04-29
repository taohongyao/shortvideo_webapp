package shortvideo.declantea.me.serviceimpl;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import shortvideo.declantea.me.Enum.VideoStateEnum;
import shortvideo.declantea.me.dao.ShortVideoDAO;
import shortvideo.declantea.me.entity.Favorite;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.UserInfo;
import shortvideo.declantea.me.model.VideoInfo;
import shortvideo.declantea.me.service.CommentService;
import shortvideo.declantea.me.service.FavoriteService;
import shortvideo.declantea.me.service.UserService;
import shortvideo.declantea.me.service.VideoService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Getter(AccessLevel.PROTECTED)
public class VideoServiceImpl implements VideoService {

    private static Logger logger= LoggerFactory.getLogger(VideoServiceImpl.class);

    @Value("${PATH_TO_USER_VIDEO_FOLDER}")
    @Getter(AccessLevel.PRIVATE)
    private String userVideoFolderPath;

    @Value("${PATH_TO_USER_IMAGE_FOLDER}")
    @Getter(AccessLevel.PRIVATE)
    private String userVideoCoverFolderPath;


    private void saveMultipartToFile(@NotNull CommonsMultipartFile file, String fileName, String dir) throws IOException {
        Path filepath = Paths.get(dir, fileName);
        File folder= new File(dir);
        if(!folder.exists()){
            folder.mkdirs();
        }
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(file.getBytes());
            os.flush();
        }
    }



    @Autowired
    private ShortVideoDAO shortVideoDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CommentService commentService;




    @Override
    public ShortVideo saveVideo(VideoInfo videoInfo) throws IOException {
        CommonsMultipartFile videoFile=videoInfo.getVideoFile();
        CommonsMultipartFile coverFIle=videoInfo.getVideoCoverFile();
        String videoFileSuffix = Objects.requireNonNull(videoFile.getOriginalFilename()).substring(videoFile.getOriginalFilename().lastIndexOf("."));
        String coverFIleSuffix = Objects.requireNonNull(coverFIle.getOriginalFilename()).substring(coverFIle.getOriginalFilename().lastIndexOf("."));

        String fileUUID= UUID.randomUUID().toString();
        String videoFileName=fileUUID+videoFileSuffix;
        String videoCoverName=fileUUID+coverFIleSuffix;

        saveMultipartToFile(videoFile,videoFileName,userVideoFolderPath);
        saveMultipartToFile(coverFIle,videoCoverName,userVideoCoverFolderPath);

        ShortVideo shortVideo=new ShortVideo().setUserAccount(userService.getUserAccountByUsername(videoInfo.getUsername()))
                .setVideoState(VideoStateEnum.Approve)
                .setVideoTitle(videoInfo.getVideoTitle())
                .setVideoDescription(videoInfo.getVideoDescription())
                .setVideoCoverPath(videoCoverName)
                .setVideoPath(videoFileName);

        logger.debug(userService.getUserAccountByUsername(videoInfo.getUsername()).toString());
        logger.debug(userService.getUserAccountByUsername(videoInfo.getUsername()).getUsername());
        logger.debug(videoInfo.toString());
        logger.debug(shortVideo.toString());
        logger.debug("Count: "+commentService.getCommentCounterByVideoId(shortVideo));
        return shortVideoDAO.create(shortVideo);

    }

    @Override
    public ShortVideo updateVideo(VideoInfo videoInfo, String uuid) throws IOException {
        CommonsMultipartFile videoFile=videoInfo.getVideoFile();
        CommonsMultipartFile coverFIle=videoInfo.getVideoCoverFile();
        String videoFileSuffix = Objects.requireNonNull(videoFile.getOriginalFilename()).substring(videoFile.getOriginalFilename().lastIndexOf("."));
        String coverFIleSuffix = Objects.requireNonNull(coverFIle.getOriginalFilename()).substring(coverFIle.getOriginalFilename().lastIndexOf("."));

        String fileUUID= UUID.randomUUID().toString();
        String videoFileName=fileUUID+videoFileSuffix;
        String videoCoverName=fileUUID+coverFIleSuffix;

        saveMultipartToFile(videoFile,videoFileName,userVideoFolderPath);
        saveMultipartToFile(coverFIle,videoCoverName,userVideoCoverFolderPath);
        ShortVideo shortVideo=shortVideoDAO.findOne(uuid);

        shortVideo.setVideoState(VideoStateEnum.Approve)
                .setVideoTitle(videoInfo.getVideoTitle())
                .setVideoDescription(videoInfo.getVideoDescription())
                .setVideoCoverPath(videoCoverName)
                .setVideoPath(videoFileName);

        return shortVideo;
    }

    @Override
    public  VideoInfo convertShortVideo2VideoInfo(ShortVideo shortVideo){
        return new VideoInfo().setVideoId(shortVideo.getVideoId().toString())
                .setUserID(""+shortVideo.getUserAccount().getUserID())
                .setCommentCounter(commentService.getCommentCounterByVideoId(shortVideo))
                .setFavoriteCounter(favoriteService.getFavoriteCounter(shortVideo))
                .setVideoState(shortVideo.getVideoState().getVideoState())
                .setVideoDescription(shortVideo.getVideoDescription())
                .setVideoTitle(shortVideo.getVideoTitle())
                .setCreateDate(Optional.ofNullable(shortVideo.getCreateDate()).orElse(new Date()).toString())
                .setDisplayName(shortVideo.getUserAccount().getDisplayName())
                .setUsername(shortVideo.getUserAccount().getUsername())
                .setVideoFilePath(shortVideo.getVideoPath())
                .setVideoCoverFilePath(shortVideo.getVideoCoverPath());
    }


    @Override
    public byte[] loadImgFromFile(String videoUUID) throws IOException {
        try {
            ShortVideo shortVideo = getShortVideoByVideoId(videoUUID);

            Path path = Paths.get(userVideoCoverFolderPath, shortVideo.getVideoCoverPath());
//            System.out.println(path);
            InputStream inputStream = Files.newInputStream(path);
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            InputStream imgNotFound=new ClassPathResource("img/img_not_found.png").getInputStream();
            return IOUtils.toByteArray(imgNotFound);
        }
    }

    @Override
    public byte[] loadVideoFromFile(String videoUUID) throws IOException {
        try {
            ShortVideo shortVideo = getShortVideoByVideoId(videoUUID);
            Path path = Paths.get(userVideoFolderPath, shortVideo.getVideoPath());
            InputStream inputStream = Files.newInputStream(path);
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            InputStream imgNotFound=new ClassPathResource("video/video_not_found.mp4").getInputStream();
            return IOUtils.toByteArray(imgNotFound);
        }
    }

    @Override
    public ShortVideo getShortVideo(ShortVideo shortVideo) {
        return getShortVideoByVideoId(shortVideo.getVideoId());
    }

    @Override
    public ShortVideo getShortVideo(VideoInfo videoInfo) {
        return getShortVideoByVideoId(videoInfo.getVideoId());
    }

    @Override
    public ShortVideo getShortVideoByVideoId(String videoId) {
        return shortVideoDAO.findOne(videoId);
    }


    @Override
    public List<ShortVideo> getAllApprovedVideosByKeyword(String keyword){
        return shortVideoDAO.findAllApprovedShortVideoListByKeyword(keyword);
    }

    @Override
    public List<ShortVideo> getAllApprovedVideos() {
        logger.debug(shortVideoDAO.findAllApproveShortVideo().toString());
        return shortVideoDAO.findAllApproveShortVideo();
    }

    @Override
    public List<ShortVideo> getAllNotApprovedVideos() {
        return shortVideoDAO.findAllNotApproveShortVideo();
    }

    @Override
    public List<ShortVideo> getAllPendingVideos() {
        return shortVideoDAO.findAllPendingShortVideo();
    }

    @Override
    public List<ShortVideo> getDeletedPendingVideos() {
        return shortVideoDAO.findAllDeletedShortVideo();
    }

    @Override
    public List<ShortVideo> getAllVideosByUserId(UserInfo userInfo) {
        return getAllVideosByUserId(userInfo.getUserID());
    }

    @Override
    public List<ShortVideo> getAllVideosByUserId(long userId) {
        return shortVideoDAO.findAllShortVideoListByUserId(userId);
    }

    @Override
    public List<ShortVideo> getNotDeletedVideosByUser(UserInfo userInfo) {
        return getNotDeletedVideosByUserId(userInfo.getUserID());
    }

    @Override
    public List<ShortVideo> getNotDeletedVideosByUserId(long userId) {
        return shortVideoDAO.findNotDeletedShortVideoListByUserId(userId);
    }

    @Override
    public List<ShortVideo> getApprovedVideosByUser(UserInfo userInfo) {
        return getApprovedVideosByUserId(userInfo.getUserID());
    }

    @Override
    public List<ShortVideo> getApprovedVideosByUserId(long userId) {
        return shortVideoDAO.findApproveShortVideoListByUserId(userId);
    }

    @Override
    public List<ShortVideo> getNotApprovedVideosByUser(UserInfo userInfo) {
        return getNotApprovedVideosByUserId(userInfo.getUserID());
    }

    @Override
    public List<ShortVideo> getNotApprovedVideosByUserId(long userId) {
        return shortVideoDAO.findNotApproveShortVideoListByUserId(userId);
    }

    @Override
    public List<ShortVideo> getPendingVideosByUser(UserInfo userInfo) {
        return getPendingVideosByUserId(userInfo.getUserID());
    }

    @Override
    public List<ShortVideo> getPendingVideosByUserId(long userId) {
        return shortVideoDAO.findPendingShortVideoListByUserId(userId);
    }

    @Override
    public List<ShortVideo> getFavoriteVideosByUser(UserInfo userInfo) {
        return getFavoriteVideosByUserId(userInfo.getUserID());
    }

    @Override
    public List<ShortVideo> getFavoriteVideosByUserId(long userId) {
        List<Favorite> list=favoriteService.getFavoritesByUserId(userId);
        List<ShortVideo> shortVideos=new ArrayList<>();
        list.forEach(t->shortVideos.add(t.getShortVideo()));
        return shortVideos;
    }

    @Override
    public List<ShortVideo> getApprovedFavoriteVideosByUser(UserInfo userInfo) {
        return getApprovedFavoriteVideosByUserId(userInfo.getUserID());
    }

    @Override
    public List<ShortVideo> getApprovedFavoriteVideosByUserId(long userId) {
        List<ShortVideo> shortVideos=getFavoriteVideosByUserId(userId);
        return shortVideos.stream().filter(t->t.getVideoState().equals(VideoStateEnum.Approve)).collect(Collectors.toList());
    }

    @Override
    public ShortVideo makeVideoDeleteState(ShortVideo shortVideo) {
        return makeVideoDeleteStateById(shortVideo.getVideoId());
    }

    @Override
    public ShortVideo makeVideoDeleteStateById(String videoId) {
        return shortVideoDAO.makeVideoDeleteState(videoId);
    }


    @Override
    public ShortVideo makeVideoNotApproveState(ShortVideo shortVideo) {
        return makeVideoNotApproveStateById(shortVideo.getVideoId());
    }

    @Override
    public ShortVideo makeVideoNotApproveStateById(String videoId) {
        return shortVideoDAO.makeVideoNotApprovedState(videoId);
    }


    @Override
    public ShortVideo makeVideoApproveState(ShortVideo shortVideo) {
        return makeVideoApproveStateById(shortVideo.getVideoId());
    }

    @Override
    public ShortVideo makeVideoApproveStateById(String videoId) {
        return shortVideoDAO.makeVideoApprovedState(videoId);
    }

    @Override
    public ShortVideo makeVideoPendingState(ShortVideo shortVideo) {
        return makeVideoPendingStateById(shortVideo.getVideoId());
    }

    @Override
    public ShortVideo makeVideoPendingStateById(String videoId) {
        return shortVideoDAO.makeVideoPendingState(videoId);
    }

}
