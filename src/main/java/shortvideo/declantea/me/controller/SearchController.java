package shortvideo.declantea.me.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shortvideo.declantea.me.model.KeywordInfo;
import shortvideo.declantea.me.model.VideoInfo;
import shortvideo.declantea.me.service.VideoService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private VideoService videoService;

    @GetMapping()
    public ModelAndView personPage(ModelAndView mv, @RequestParam(name = "keyword") String keyWord) {
        mv.setViewName("search");
        mv.addObject("keyword",keyWord);
        return mv;
    }


    @PostMapping()
    @ResponseBody
    public Object getSearchVideoList(@RequestParam(name = "keyword") String keyWord) {
        List<VideoInfo> list=videoService.getAllApprovedVideosByKeyword(keyWord).stream()
                .map(T->videoService.convertShortVideo2VideoInfo(T)).collect(Collectors.toList());
        return list;
    }

    @PostMapping(value = "/videos_list")
    @ResponseBody
    public Object getSearchVideoList2(@RequestParam(name = "keyword") String keyWord) {
        List<VideoInfo> list=videoService.getAllApprovedVideosByKeyword(keyWord).stream()
                .map(T->videoService.convertShortVideo2VideoInfo(T)).collect(Collectors.toList());
        return list;
    }
}
