package shortvideo.declantea.me.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorPageController {

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/404")
    public String resourcesNotFound() {
        return "404";
    }

    @GetMapping()
    public String errorPage() {
        return "error";
    }

}
