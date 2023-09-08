package toy.shop.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.shop.entity.Author;
import toy.shop.entity.Book;
import toy.shop.entity.Category;
import toy.shop.entity.dto.BookDto;
import toy.shop.service.AuthorService;
import toy.shop.service.BookService;
import toy.shop.service.CategoryService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    @GetMapping("/goodsEnroll")
    public String goodsEnrollPage(Model model) throws JsonProcessingException {
        ObjectMapper objm = new ObjectMapper();
        List<Category> allList = categoryService.search(null);
        String cateList = objm.writeValueAsString(allList);
        model.addAttribute("cateList", cateList);
        return "admin/goodsEnroll";
    }
    @PostMapping("/goodsEnroll")
    public String goodsEnrollPOST(BookDto dto, RedirectAttributes rttr) {
        Author relatedAuthor = authorService.findById(dto.getAuthorId());
        Category relatedCategory = categoryService.findById(dto.getCateCode());

        LocalDate date = dto.convertStringToLocalDate(dto.getPubleYear());
        Book book = new Book(dto.getBookName(),date,dto.getPublisher(),dto.getBookPrice(), dto.getBookPrice(), dto.getBookDiscount(),dto.getBookIntro(),dto.getBookContents());
        book.setAuthor(relatedAuthor);
        book.setCategory(relatedCategory);

        bookService.bookEnroll(book);

        rttr.addFlashAttribute("enroll_result", book.getBookName());

        return "redirect:/admin/goodsManage";
    }
}
