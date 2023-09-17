package toy.shop.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.service.AuthorService;
import toy.shop.service.AuthorWebService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorWebService authorWebService;

    @GetMapping("/main")
    public String adminMainPage() {
        return "admin/main";
    }

    @GetMapping("/authorEnroll")
    // dto로 쪼개야함
    public String authorEnrollPage() {
        return "admin/authorEnroll";
    }

    @PostMapping("/authorEnroll")
    public String authorEnroll(
//            @ModelAttribute AuthorDto authorDto,
            @RequestParam String authorName,
            @RequestParam String nation,
            @RequestParam String authorIntro,
            @RequestParam(required = false) Long id,
            RedirectAttributes rttr) {
        AuthorNation authorNation = AuthorNation.valueOf(nation);
        AuthorDto authorDto = new AuthorDto(authorName, authorNation, authorIntro);
        log.info("author= {}", authorDto);
        authorDto.setNation(AuthorNation.valueOf(nation)); // 문자열을 Enum으로 변환

        log.info("authorDto = {}", authorDto);
        authorService.authorEnroll(authorDto);

        rttr.addFlashAttribute("enroll_result", authorDto.getAuthorName());
        return "redirect:/admin/authorManage";
    }

    @GetMapping("/authorManage")
    public String authorManagePage(Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model) {
        PageDto pageDto = new PageDto(pageable, keyword);
        Page<AuthorDto> result = authorWebService.authorList(pageDto);
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
        model.addAttribute("authorInfo", authorService.findByIdReturnDto(id).get());

        return "admin/authorDetail";

    }

    @GetMapping("/authorModify/{id}")
    public String authorModifyPage(Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, @PathVariable Long id, Model model) {
        PageDto pageDto = new PageDto(pageable, keyword);
        log.info("detail in pageDto = {}", pageDto);
        model.addAttribute("pageDto", pageDto);

        AuthorDto authorDto = authorService.findByIdReturnDto(id).get();
        model.addAttribute("authorInfo", authorDto);

        return "admin/authorModify";
    }

    @PostMapping("/authorModify/{id}")
    public String authorModify(
//            @ModelAttribute AuthorDto authorDto,
            @RequestParam String authorName,
            @RequestParam String nation,
            @RequestParam String authorIntro,
            @PathVariable Long id, Model model) {
        AuthorNation authorNation = AuthorNation.valueOf(nation);
        AuthorDto authorDto = new AuthorDto(authorName, authorNation, authorIntro);

        authorService.update(authorDto,id);
        model.addAttribute("modify_result", 1);
        return "redirect:/admin/authorManage";
    }

    @GetMapping("/authorPop")
    public String authorPopPage(@PageableDefault(page = 0, size = 5) Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model) {
        PageDto pageDto = new PageDto(pageable, keyword);
        int totalPages = authorWebService.authorList(pageDto).getTotalPages();
        List<AuthorDto> list = authorWebService.authorList(pageDto).getContent();
        if (list.isEmpty()) {
            model.addAttribute("listCheck", "empty");
        } else {
            model.addAttribute("list", list);
            model.addAttribute("limit", totalPages);
        }
        model.addAttribute("pageDto", pageDto);
        return "admin/authorPop";
    }

    @PostMapping("/authorDelete/{id}")
    public String authorDelete(@PathVariable Long id,RedirectAttributes rttr) {
        boolean result;
        try {
            authorService.authorDelete(id);
            result = true;
        } catch (Exception e){
            result = false;
        }
        rttr.addFlashAttribute("delete_result", result);
        return "redirect:/admin/authorManage";
    }

}
