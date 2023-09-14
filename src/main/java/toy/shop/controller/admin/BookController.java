package toy.shop.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.shop.entity.Author;
import toy.shop.entity.Book;
import toy.shop.entity.Category;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.entity.dto.BookDto;
import toy.shop.entity.dto.CategoryDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.entity.web.AttachImage;
import toy.shop.service.AuthorService;
import toy.shop.service.BookService;
import toy.shop.service.CategoryService;
import toy.shop.service.UploadService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final UploadService uploadService;
    @Value("${image.path}")
    private String uploadFolder;
    @GetMapping("/goodsEnroll")
    public String goodsEnrollPage(Model model) throws JsonProcessingException {
        ObjectMapper objm = new ObjectMapper();
        objm.registerModule(new JavaTimeModule());
        List<Category> allList = categoryService.search(null);
        String cateList = objm.writeValueAsString(allList);
        model.addAttribute("cateList", cateList);
        return "admin/goodsEnroll";
    }
    @PostMapping("/goodsEnroll")
    public String goodsEnrollPOST(BookDto dto, RedirectAttributes rttr) {
        Author relatedAuthor = authorService.findById(dto.getAuthorId()).get();
        Category relatedCategory = categoryService.findById(dto.getCateCode());

        LocalDate date = dto.convertStringToLocalDate(dto.getPubleYear());
        Book book = new Book(dto.getBookName(),date,dto.getPublisher(),dto.getBookPrice(), dto.getBookStock(), dto.getBookDiscount(),dto.getBookIntro(),dto.getBookContents());
        book.setAuthor(relatedAuthor);
        book.setCategory(relatedCategory);

        bookService.bookEnroll(book);

        rttr.addFlashAttribute("enroll_result", book.getBookName());

        return "redirect:/admin/goodsManage";
    }
    @GetMapping("/goodsManage")
    public String goodsManagePage(Pageable pageable, @RequestParam(required = false,defaultValue = "") String keyword, Model model){
        PageDto pageDto = new PageDto(pageable, keyword);
        Page<Book> result = bookService.pagingAllBook(pageDto);

        if (!result.isEmpty()) {
            model.addAttribute("list", result.getContent());
        } else{
            model.addAttribute("listCheck", "empty");
        }
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("limit", result.getTotalPages());

        return "admin/goodsManage";
    }

    @GetMapping("/goodsDetail/{id}")
    public String goodsDetailPage(@PathVariable Long id, Pageable pageable, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model) throws JsonProcessingException {
        PageDto pageDto = new PageDto(pageable, keyword);
        Book book = bookService.findBookById(id);

        String cateList = getCateList();

        model.addAttribute("cateList", cateList);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("goodsInfo", book);

        return "admin/goodsDetail";
    }

    @GetMapping("/goodsModify/{id}")
    public String goodsModifyPage(@PathVariable Long id,Pageable pageable,@RequestParam(required = false,defaultValue = "") String keyword, Model model) throws JsonProcessingException {
        PageDto pageDto = new PageDto(pageable, keyword);
        Book findBook = bookService.findBookById(id);
        log.info("findBook.getCategory() = {}",findBook.getCategory());
        String cateList = getCateList();

        model.addAttribute("cateList", cateList);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("goodsInfo", findBook);

        return "admin/goodsModify";
    }
    @PostMapping("/goodsModify/{id}")
    public String goodsModify(@ModelAttribute BookDto dto, @PathVariable Long id, Model model){
        LocalDate publeYear = dto.convertStringToLocalDate(dto.getPubleYear());
        Author author = authorService.findById(dto.getAuthorId()).get();
        Category category = categoryService.findById(dto.getCateCode());

        bookService.changeValues(id, dto, author, category, publeYear);

        return "redirect:/admin/goodsDetail/{id}";
    }

    @PostMapping("/goodsDelete/{id}")
    public String goodsDelete(@PathVariable Long id,RedirectAttributes rttr) {
        boolean result;
        try {
            bookService.goodsDelete(id);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        log.info("result = {}", result);
        //result는 true로 잘 나옴

        rttr.addFlashAttribute("delete_result", result);

        return "redirect:/admin/goodsManage";
    }

    @PostMapping("/uploadAjaxAction")
    public ResponseEntity<List<AttachImage>> uploadAjax(MultipartFile[] uploadFile){
        List<AttachImage> list = uploadService.upload(uploadFile);
        if (list == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private String getCateList() throws JsonProcessingException {
        ObjectMapper objm = new ObjectMapper();
        objm.registerModule(new JavaTimeModule());
        List<Category> allList = categoryService.search(null);

        CategoryDto categoryDto = new CategoryDto();
        List<CategoryDto> categoryDtos = categoryDto.entityToDto(allList);
        return objm.writeValueAsString(categoryDtos);
    }


}
