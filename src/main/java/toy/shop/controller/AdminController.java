package toy.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.service.AuthorService;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthorService authorService;

    @GetMapping("/main")
    public String adminMainPage(){
        return "admin/main";
    }
    @GetMapping("/goodsManage")
    public String goodsManagePage(){
        return "admin/goodsManage";
    }
    @GetMapping("/goodsEnroll")
    public String goodsEnrollPage(){
        return "admin/goodsEnroll";
    }

    @GetMapping("/authorEnroll")
    // dto로 쪼개야함
    public String authorEnrollPage() {
        return "admin/authorEnroll";
    }
    @PostMapping("/authorEnroll")
    public String authorEnroll(@ModelAttribute Author author, @RequestParam String nation, RedirectAttributes rttr) {
        log.info("author= {}", author);
        author.setNation(AuthorNation.valueOf(nation)); // 문자열을 Enum으로 변환

        authorService.authorEnroll(author);
        rttr.addFlashAttribute("enroll_result", author.getAuthorName());
        return "redirect:/admin/authorManage";
    }
    @GetMapping("/authorManage")
    public String authorMangePage(){
        return "admin/authorManage";
    }
}
