package toy.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.service.AuthorService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthorService authorService;

    @GetMapping("/main")
    public String adminMainPage() {
        return "admin/main";
    }

    @GetMapping("/goodsManage")
    public String goodsManagePage() {
        return "admin/goodsManage";
    }

    @GetMapping("/goodsEnroll")
    public String goodsEnrollPage() {
        return "admin/goodsEnroll";
    }

    @GetMapping("/authorEnroll")
    // dto로 쪼개야함
    public String authorEnrollPage() {
        return "admin/authorEnroll";
    }

    @PostMapping("/authorEnroll")
    public String authorEnroll(@ModelAttribute AuthorDto authorDto, @RequestParam String nation, RedirectAttributes rttr) {
        log.info("author= {}", authorDto);
        authorDto.setNation(AuthorNation.valueOf(nation)); // 문자열을 Enum으로 변환

        // 서비스에는 진짜 객체가 들어가는게 나음
        Author author = new Author(authorDto.getAuthorName(), authorDto.getNation(), authorDto.getAuthorIntro());

        authorService.authorEnroll(author);
        rttr.addFlashAttribute("enroll_result", author.getAuthorName());
        return "redirect:/admin/authorManage";
    }

    @GetMapping("/authorManage")
    public String authorManagePage(Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model) {
        PageDto pageDto = new PageDto(pageable, keyword);
        log.info("pageDto = {}", pageDto);
        Page<Author> result = authorService.authorList(pageDto);
        if (result.hasContent()) {
            model.addAttribute("list", result.getContent());    // 작가 존재 경우
        } else {
            model.addAttribute("listCheck", "empty");    // 작가 존재하지 않을 경우
        }
        model.addAttribute("pageMaker", pageDto);
        model.addAttribute("limit", result.getTotalPages());
        return "admin/authorManage";
    }

    @GetMapping("/authorDetail/{id}")
    public String authorDetailPage(Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, @PathVariable Long id, Model model) {
        PageDto pageDto = new PageDto(pageable, keyword);
        log.info("detail in pageDto = {}", pageDto);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("authorInfo", authorService.findById(id));

        return "admin/authorDetail";

    }

    @GetMapping("/authorModify/{id}")
    public String authorModifyPage(Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, @PathVariable Long id, Model model) {
        PageDto pageDto = new PageDto(pageable, keyword);
        log.info("detail in pageDto = {}", pageDto);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("authorInfo", authorService.findById(id));

        return "admin/authorModify";
    }
    @PostMapping("/authorModify/{id}")
    public String authorModify(@ModelAttribute AuthorDto authorDto, @RequestParam String nation, @PathVariable Long id, Model model){
        authorDto.setNation(AuthorNation.valueOf(nation)); // 문자열을 Enum으로 변환
        Author authorById = authorService.findById(id);
        authorById.changeAuthorValues(authorDto);

        authorService.update(authorById);
        model.addAttribute("modify_result", 1);
        return "redirect:/admin/authorManage";
    }


}
