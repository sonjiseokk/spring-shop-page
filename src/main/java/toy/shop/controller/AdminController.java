package toy.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AdminController {
    @GetMapping("/admin/main")
    public String adminMainPage(){
        return "admin/main";
    }
}
