package toy.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toy.shop.entity.Member;
import toy.shop.service.MailService;
import toy.shop.service.MemberService;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute Member member) {
        log.info("member ={}", member);
        memberService.memberJoin(member);
        return "redirect:/main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/memberIdChk")
    public String duplicateMemberNameChk(String loginId){
        if (memberService.idCanUseChk(loginId)){
            return "success";
        }
        else {
            return "fail";
        }
    }

    @ResponseBody
    @GetMapping("/mailCheck")
    public String mailCheck(@RequestParam("email") String email) {
        log.info("이메일 전송 확인");
        log.info("email = {}",email);

        String checkNum = mailService.sendEmail(email);
        return checkNum;
    }
}
