package shortvideo.declantea.me.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shortvideo.declantea.me.dao.UserAccountDAO;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.exception.UsernameAlreadyExistException;
import shortvideo.declantea.me.model.UserAccountInfo;
import shortvideo.declantea.me.model.UserInfo;
import shortvideo.declantea.me.service.UserService;
import shortvideo.declantea.me.service.VideoService;

import javax.validation.Valid;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    private static Logger logger= LoggerFactory.getLogger(AccountController.class);


    @PostMapping(value = "/{user_id}/videos_list")
    @ResponseBody
    public Object getAccountVideoList(@PathVariable long user_id) {
        return videoService.getApprovedVideosByUserId(user_id)
                .stream()
                .map(t->videoService.convertShortVideo2VideoInfo(t))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/{user_id}/favorite_videos_list")
    @ResponseBody
    public Object getAccountFavoriteVideoList(@PathVariable long user_id) {
        return videoService.getApprovedFavoriteVideosByUserId(user_id)
                .stream()
                .map(t->videoService.convertShortVideo2VideoInfo(t))
                .collect(Collectors.toList());
    }

    @GetMapping(value = {"/signup"})
    public ModelAndView signupPage(ModelAndView mv, UserAccountInfo userAccountInfo){
        mv.setViewName("registration");
        mv.addObject("useraccountinfo", userAccountInfo);
        return mv;
    }

    @GetMapping(value = {"/info"})
    @ResponseBody
    public Object signupPage(Principal principal){
        UserInfo userInfo= userService.convertUserAccount2UserInfo(userService.getUserAccountByUsername(principal.getName()));
        return userInfo;
    }

    @PostMapping("/signup")
    public ModelAndView registration(ModelAndView mv, @ModelAttribute("useraccountinfo") @Valid UserAccountInfo userAccountInfo, BindingResult result) throws IOException {
        logger.debug("UserAccount:{}",userAccountInfo);
        if(userAccountInfo.getGRecaptchaResponse()!=null){
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            String data="secrete:6LeswMIaAAAAAPG3gIKhgFHyQ_uXYshEfGzm4oeK&response="+userAccountInfo.getGRecaptchaResponse()
                    +"&remoteip="+InetAddress.getLocalHost().getHostAddress();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(data);
            logger.debug(con.getResponseMessage());
        }

        if (result.hasErrors()) {
            mv.setViewName("registration");
            return mv;
        }
        try {
            this.userService.registerCustomer(userAccountInfo);
        } catch (UsernameAlreadyExistException usernameAlreadyExistException) {
            usernameAlreadyExistException.printStackTrace();
            mv.addObject("useraccountinfo", userAccountInfo);
            mv.addObject("usernameexist", true);
            mv.setViewName("registration");
            return mv;
        }
        mv.setViewName("redirect:/home");
        return mv;
    }

    @GetMapping(value = {"/{user_id}/page"})
    public ModelAndView accountPage(ModelAndView mv){
        mv.setViewName("person_page");
        return mv;
    }

    @GetMapping(value = {"/videos_manage"})
    public ModelAndView accountManage(ModelAndView mv){
        mv.setViewName("person_page");
        mv.addObject("customer_manage_page",true);
        return mv;
    }


    @PostMapping(value = "/videos_list")
    @ResponseBody
    public Object getMyAccountVideoList(Principal principal) {
        UserAccount userAccount=userService.getUserAccountByUsername(principal.getName());
        return videoService.getApprovedVideosByUserId(userAccount.getUserID())
                .stream()
                .map(t->videoService.convertShortVideo2VideoInfo(t))
                .collect(Collectors.toList());
    }

}
