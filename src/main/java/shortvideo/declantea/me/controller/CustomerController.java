package shortvideo.declantea.me.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import shortvideo.declantea.me.dao.UserAccountDAO;
import shortvideo.declantea.me.entity.UserAccount;
import shortvideo.declantea.me.model.UserAccountInfo;
import shortvideo.declantea.me.security.authority.AuthorityEnum;
import shortvideo.declantea.me.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/account")
public class CustomerController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/add")
    @ResponseBody
    public Object addUser() {
        UserAccountInfo userAccountInfo=new UserAccountInfo().setUsername("A").setPassword("123456").setRetypePassword("123456")
                .setDisplayName("A");
        userService.registerCustomer(userAccountInfo);
        return userAccountInfo;
    }

}
