package toy.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toy.shop.entity.Member;
import toy.shop.entity.dto.LoginMemberDto;
import toy.shop.entity.dto.SessionMemberDto;
import toy.shop.service.MailService;
import toy.shop.service.MemberService;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
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

    @PostMapping("/login")
    public String login(@ModelAttribute LoginMemberDto dto, Model model, HttpServletRequest request, RedirectAttributes rttr) {
        HttpSession session = request.getSession();

        Optional<Member> loginResult = memberService.login(dto);
        if (loginResult.isEmpty()) {
            boolean result = false;
            rttr.addFlashAttribute("result", result);
            return "redirect:/member/login";
        }

        Member successLoginMember = loginResult.get();
        SessionMemberDto sessionMemberDto = new SessionMemberDto();
        sessionMemberDto.convertToSessionMemberDto(successLoginMember);
        sessionMemberDto.setPassword(dto.getPassword());

        session.setAttribute("member",sessionMemberDto);
        return "redirect:/main";
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

    @GetMapping("/logout.do")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/main";
    }
    @PostMapping("/logout.do")
    @ResponseBody
    public void logout_async(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();

    }
}
