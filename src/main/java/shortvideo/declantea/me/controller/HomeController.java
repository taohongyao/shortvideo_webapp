package shortvideo.declantea.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shortvideo.declantea.me.model.VideoInfo;
import shortvideo.declantea.me.service.UserService;
import shortvideo.declantea.me.service.VideoService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    VideoService videoService;
    @Autowired
    UserService userService;

    @GetMapping({"home",""})
    public ModelAndView homePage(ModelAndView mv)
    {
        mv.setViewName("home");
        return mv;
    }

    @PostMapping(value = "/videos_list")
    @ResponseBody
    public Object homePageVideoList() {
        List<VideoInfo> list=videoService.getAllApprovedVideos().stream()
                .map(T->videoService.convertShortVideo2VideoInfo(T)).collect(Collectors.toList());
        return list;
    }

}
