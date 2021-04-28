package shortvideo.declantea.me.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.model.CommentInfo;
import shortvideo.declantea.me.model.VideoInfo;
import shortvideo.declantea.me.service.UserService;
import shortvideo.declantea.me.service.VideoService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

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

    @PostMapping(value = "/{videoUUID}/comment")
    @ResponseBody
    public Object comment(@RequestBody CommentInfo commentInfo){
            return commentInfo;
    }


    @GetMapping(value = "/{videoUUID}/cover")
    @ResponseBody
    public byte[] img(HttpServletResponse response, @PathVariable String videoUUID) throws IOException {
        return this.videoService.loadImgFromFile(videoUUID);
    }

    @GetMapping(value = "/{videoUUID}/file")
    @ResponseBody
    public byte[] video(HttpServletResponse response, @PathVariable String videoUUID) throws IOException {
        return this.videoService.loadVideoFromFile(videoUUID);
    }


    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ModelAndView uploadProduct(@ModelAttribute("videoinfoform") @Valid VideoInfo videoInfo, BindingResult result,
                                      ModelAndView mv, Principal principal) throws IOException {
//        System.out.println("in post");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            mv.addObject("videoinfoform", videoInfo);
            mv.setViewName("vide_upload");
        } else {
            UserAccount userAccount=userService.getUserAccountByUsername(principal.getName());
            logger.debug("UserAccount:{}",userAccount);
            videoInfo.setUsername(userAccount.getUsername());
            videoInfo=videoService.convertShortVideo2VideoInfo(videoService.saveVideo(videoInfo));
            mv.setViewName("redirect:/account/person/"+userAccount.getUserID()+"/?upload_video_successfully=true");
        }
        return mv;
    }

}
