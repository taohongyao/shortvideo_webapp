package shortvideo.declantea.me.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import shortvideo.declantea.me.Enum.VideoStateEnum;
import shortvideo.declantea.me.entity.Favorite;
import shortvideo.declantea.me.entity.ShortVideo;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.model.CommentInfo;
import shortvideo.declantea.me.model.FavoriteInfo;
import shortvideo.declantea.me.model.VideoInfo;
import shortvideo.declantea.me.service.CommentService;
import shortvideo.declantea.me.service.FavoriteService;
import shortvideo.declantea.me.service.UserService;
import shortvideo.declantea.me.service.VideoService;
import shortvideo.declantea.me.util.MultipartFileSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FavoriteService favoriteService;

    private static Logger logger= LoggerFactory.getLogger(VideoController.class);

    @GetMapping
    public String videoPage(){
        return "redirect:/";
    }

    @GetMapping(value = "/upload")
    public String uploadProduct() throws IOException {
        return "video_upload";
    }



    @GetMapping(value = "/{videoUUID}/watch")
    public ModelAndView videoPage(ModelAndView mv,@PathVariable String videoUUID) throws IOException {
        mv.setViewName("video_page");
        mv.addObject("shortVideo",videoService.convertShortVideo2VideoInfo(videoService.getShortVideoByVideoId(videoUUID)));
        return mv;
    }


    @GetMapping(value = "/{videoUUID}/cover")
    @ResponseBody
    public byte[] img(HttpServletResponse response, @PathVariable String videoUUID) throws IOException {
        return this.videoService.loadImgFromFile(videoUUID);
    }

//    @GetMapping(value = "/{videoUUID}/file")
//    @ResponseBody
//    public byte[] video(HttpServletResponse response, @PathVariable String videoUUID) throws IOException {
//        return this.videoService.loadVideoFromFile(videoUUID);
//    }

    @GetMapping(value = "/{videoUUID}/file")
    @ResponseBody
    public void video(HttpServletRequest request, HttpServletResponse response, @PathVariable String videoUUID) throws Exception {
        File vide=videoService.getVideResourceFile(videoUUID);
        MultipartFileSender.fromPath(vide.toPath())
                .with(request)
                .with(response)
                .serveResource();
    }



    @GetMapping(value = "/{videoUUID}/comments")
    @ResponseBody
    public Object getComments(@PathVariable String videoUUID) {
        List<CommentInfo> comments=commentService.getActiveCommentsByVideoId(videoUUID).stream().map(t->commentService.convertComment2CommentInfo(t)).collect(Collectors.toList());
        Map<String,Object> data=new HashMap<>();
        data.put("comment_counter",commentService.getActiveCommentCounterByVideoId(videoUUID));
        data.put("list",comments);
        return data;
    }

    @PostMapping(value = "/{videoUUID}/comments")
    @ResponseBody
    public Object postComments(@RequestBody @Valid CommentInfo commentInfo, @PathVariable String videoUUID,BindingResult result,Principal principal)  {
        if (result.hasErrors()) {
            new RuntimeException("Bad input");
        }
        commentInfo.setUsername(principal.getName())
        .setVideoId(videoUUID);
        commentInfo=commentService.convertComment2CommentInfo(commentService.saveComment(commentInfo));
        return commentInfo;
    }

    @DeleteMapping(value = "/{videoUUID}/comments")
    @ResponseBody
    public Object deleteComments(@RequestBody CommentInfo commentInfo, @PathVariable String videoUUID,Principal principal)  {
        commentInfo=commentService.convertComment2CommentInfo(commentService.makeCommentDeleteStateByIdWithSameUser(Long.parseLong(commentInfo.getCommentId()),principal.getName()));
        return commentInfo;
    }

    @GetMapping(value = "/{videoUUID}/favorite")
    @ResponseBody
    public Object getFavorite(@PathVariable String videoUUID,Principal principal)  {
        FavoriteInfo favoriteInfo=new FavoriteInfo().setShortVideoId(videoUUID);
        Favorite favorite=favoriteService.getFavoriteByVideoIdAndUserName(videoUUID,principal.getName());
        if(favorite!=null){
            favoriteInfo.setFavorite(true);
        }
        return favoriteInfo;
    }

    @PostMapping(value = "/{videoUUID}/favorite")
    @ResponseBody
    public Object postFavorite(@PathVariable String videoUUID,Principal principal)  {
        FavoriteInfo favoriteInfo= favoriteService.convertFavorite2FavoriteInfo(favoriteService.saveFavorite(videoUUID,principal.getName()));
        favoriteInfo.setFavorite(true);
        VideoInfo videoInfo=videoService.convertShortVideo2VideoInfo(videoService.getShortVideoByVideoId(videoUUID));
        favoriteInfo.setVideoInfo(videoInfo);
        return favoriteInfo;
    }

    @DeleteMapping(value = "/{videoUUID}/favorite")
    @ResponseBody
    public Object deleteFavorite(@PathVariable String videoUUID,Principal principal)  {
        FavoriteInfo favoriteInfo= new FavoriteInfo().setShortVideoId(videoUUID).setFavorite(false);
        favoriteService.cancelFavorite(videoUUID,principal.getName());
        VideoInfo videoInfo=videoService.convertShortVideo2VideoInfo(videoService.getShortVideoByVideoId(videoUUID));
        favoriteInfo.setVideoInfo(videoInfo);
        return favoriteInfo;
    }


    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView uploadVideo(@ModelAttribute("videoInfo") @Valid VideoInfo videoInfo, BindingResult result,
                                      ModelAndView mv, Principal principal) throws IOException {
//        System.out.println("in post");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            mv.addObject("videoInfo", videoInfo);
            mv.setViewName("video_upload");
        } else {
            UserAccount userAccount=userService.getUserAccountByUsername(principal.getName());
            logger.debug("UserAccount:{}",userAccount);
            videoInfo.setUsername(principal.getName());
            videoInfo=videoService.convertShortVideo2VideoInfo(videoService.saveVideo(videoInfo));
            mv.setViewName("redirect:/account/"+userAccount.getUserID()+"/page?upload_video_successfully=true");
        }
        return mv;
    }

    @GetMapping(value = "/{videoUUID}/upload")
    public ModelAndView magageVideo(ModelAndView mv,@PathVariable String videoUUID,Principal principal) throws IOException {
        VideoInfo dataInfo=videoService.convertShortVideo2VideoInfo(videoService.getShortVideoByVideoId(videoUUID));
        if(dataInfo.getVideoState()== VideoStateEnum.Delete.getVideoState()||!dataInfo.getUsername().equals(principal.getName())){
            throw new RuntimeException("No permission to manage.");
        }else {
            mv.addObject("videoInfo",dataInfo);
            mv.setViewName("video_upload");
        }
        return mv;
    }

    @PostMapping(value = "/{videoUUID}/upload")
    @ResponseBody
    public ModelAndView updateVideoInfo(@ModelAttribute("videoInfo") @Valid VideoInfo videoInfo, BindingResult result,
                               ModelAndView mv, @PathVariable String videoUUID,Principal principal) throws IOException {
        VideoInfo dataInfo=videoService.convertShortVideo2VideoInfo(videoService.getShortVideoByVideoId(videoUUID));
        if(dataInfo.getVideoState()== VideoStateEnum.Delete.getVideoState()||!dataInfo.getUsername().equals(principal.getName())){
            throw new RuntimeException("No permission to manage.");
        }else if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            mv.addObject("videoInfo", videoInfo);
            mv.setViewName("video_upload");
        } else{
            UserAccount userAccount=userService.getUserAccountByUsername(principal.getName());
            videoService.convertShortVideo2VideoInfo(videoService.updateVideo(videoInfo,videoUUID));
            mv.setViewName("redirect:/account/"+userAccount.getUserID()+"/page?upload_video_successfully=true");
        }
        return mv;
    }

    @DeleteMapping(value = "/{videoUUID}/delete")
    @ResponseBody
    public Object deleteVideoInfo(@PathVariable String videoUUID,Principal principal) throws IOException {
        VideoInfo dataInfo = videoService.convertShortVideo2VideoInfo(videoService.getShortVideoByVideoId(videoUUID));
        if (dataInfo.getVideoState() == VideoStateEnum.Delete.getVideoState() || !dataInfo.getUsername().equals(principal.getName())) {
            throw new RuntimeException("No permission to manage.");
        } else{
            dataInfo=videoService.convertShortVideo2VideoInfo(videoService.makeVideoDeleteStateById(videoUUID));
        }
        return dataInfo;
    }


//    @PostMapping(value = "/{videoUUID}/manage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ModelAndView manageVideo(ModelAndView mv, @PathVariable String videoUUID,Principal principal) throws IOException {
//        VideoInfo videoInfo=videoService.convertShortVideo2VideoInfo(videoService.getShortVideoByVideoId(videoUUID));
//        if(videoInfo.getVideoState()== VideoStateEnum.Delete.getVideoState()||!videoInfo.getUsername().equals(principal.getName())){
//            throw new RuntimeException("No permission to manage.");
//        }else{
//            mv.setViewName("video_upload");
//            mv.addObject("videoInfo",videoInfo);
//        }
//        return mv;
//    }

}
