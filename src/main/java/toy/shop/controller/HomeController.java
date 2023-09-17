package toy.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(String fileName){
        File file = new File("C:\\Users\\michi\\Desktop\\images\\" + fileName);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        }catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
