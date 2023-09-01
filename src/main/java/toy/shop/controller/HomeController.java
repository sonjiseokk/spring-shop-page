package toy.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "main";
    }
    @GetMapping("/main")
    public String mainPage(Model model){
        return "main";
    }
}
