package shortvideo.declantea.me.service;

import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.model.UserInfo;
import shortvideo.declantea.me.model.VideoInfo;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface VideoService {

    VideoInfo convertShortVideo2VideoInfo(ShortVideo shortVideo);
    ShortVideo saveVideo(VideoInfo videoInfo) throws IOException;

    byte[] loadImgFromFile(String filePath) throws IOException;

    byte[] loadVideoFromFile(String videoUUID) throws IOException;

    ShortVideo getShortVideo(ShortVideo shortVideo);
    ShortVideo getShortVideo(VideoInfo videoInfo);
    ShortVideo getShortVideoByVideoId(String videoId);

    List<ShortVideo> getAllApprovedVideosByKeyword(String keyword);

    List<ShortVideo> getAllApprovedVideos();
    List<ShortVideo> getAllNotApprovedVideos();
    List<ShortVideo> getAllPendingVideos();
    List<ShortVideo> getDeletedPendingVideos();

    List<ShortVideo> getAllVideosByUserId(UserInfo userInfo);

    List<ShortVideo> getAllVideosByUserId(long userId);

    List<ShortVideo> getNotDeletedVideosByUser(UserInfo userInfo);
    List<ShortVideo> getNotDeletedVideosByUserId(long userId);

    List<ShortVideo> getApprovedVideosByUser(UserInfo userInfo);
    List<ShortVideo> getApprovedVideosByUserId(long userId);

    List<ShortVideo> getNotApprovedVideosByUser(UserInfo userInfo);
    List<ShortVideo> getNotApprovedVideosByUserId(long userId);

    List<ShortVideo> getPendingVideosByUser(UserInfo userInfo);
    List<ShortVideo> getPendingVideosByUserId(long userId);

    List<ShortVideo> getFavoriteVideosByUser(UserInfo userInfo);
    List<ShortVideo> getFavoriteVideosByUserId(long userId);

    List<ShortVideo> getApprovedFavoriteVideosByUser(UserInfo userInfo);
    List<ShortVideo> getApprovedFavoriteVideosByUserId(long userId);

    ShortVideo makeVideoDeleteState(ShortVideo shortVideo);
    ShortVideo makeVideoDeleteStateById(String videoId);

    ShortVideo makeVideoNotApproveState(ShortVideo shortVideo);
    ShortVideo makeVideoNotApproveStateById(String videoId);

    ShortVideo makeVideoApproveState(ShortVideo shortVideo);
    ShortVideo makeVideoApproveStateById(String videoId);


    ShortVideo makeVideoPendingState(ShortVideo shortVideo);

    ShortVideo makeVideoPendingStateById(String videoId);
}
