package kr.co.lovefans.devel.controller;

import kr.co.lovefans.devel.domain.Member;
import kr.co.lovefans.devel.form.LoginForm;
import kr.co.lovefans.devel.form.MemberForm;
import kr.co.lovefans.devel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MemberControrller {

    private final MemberService memberService;

    @Autowired
    public MemberControrller(MemberService memberService){
        this.memberService = memberService;
    }


    @GetMapping("/members/auth")
    public String auth(){
        return "views/subscr/join_auth";
    }


    @GetMapping("/members/new")
    public String join(){
        return "views/subscr/join_page";
    }

    @GetMapping("/members/forgot")
    public String findPwd(){
        return "views/subscr/password_forgot";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){

            System.out.println("입력창 확인요망");
            model.addAttribute("message","입력란을 확인해주세요");
            return null;
        }

        System.out.println("form = " + form.getMi_id());
        Member member = new Member();
        member.setMiId(form.getMi_id());
        member.setMiNick(form.getMi_nick());
        member.setMiPwd(form.getMi_pwd());

        memberService.join(member);



        return "redirect:/";
    }

    @PostMapping("/members/login")
    public String login(@Valid LoginForm form, BindingResult bindingResult, Model model, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            System.out.println("아이디 또는 비밀번호를 확인해주세요");
            model.addAttribute("message","아이디 또는 비밀번호를 확인해주세요");
            return "login";
        }

        System.out.println("form = " + form.getMi_id());
        Member member = new Member();
        member.setMiId(form.getMi_id());
        member.setMiPwd(form.getMi_pwd());

        if(memberService.login(member)){

            Optional<Member> memberInfo = memberService.findById(form.getMi_id());
            System.out.println("회원닉 = " + memberInfo.get().getMiNick());

            model.addAttribute("model", memberInfo.get());

            HttpSession session = request.getSession();
            session.setAttribute("session",memberInfo.get().getMiSeq());

            return "views/subscr/subscr_mypage";

        }else {
            System.out.println("아이디 또는 비밀번호를 확인해주세요");
            model.addAttribute("message","아이디 또는 비밀번호를 확인해주세요");
            } return "forward:/login";
        }

    @GetMapping("/members/logout")
    public String logout(HttpSession session){
        System.out.println("세션삭제" + session.getAttribute("session"));

        session.removeAttribute("session");

        return "redirect:/";

    }

    @PostMapping("/members/checkNick")
    @ResponseBody
    public boolean checkNick(@RequestParam("mi_nick") String mi_nick){

        boolean result = memberService.checkNick(mi_nick);

        return result;
    }

    @PostMapping("/members/checkMail")
    @ResponseBody
    public boolean checkMail(@RequestParam("mi_id") String mi_id){

        boolean result = memberService.checkMail(mi_id);

        return result;
    }




}
