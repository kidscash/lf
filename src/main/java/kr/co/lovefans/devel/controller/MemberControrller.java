package kr.co.lovefans.devel.controller;

import kr.co.lovefans.devel.domain.Member;
import kr.co.lovefans.devel.form.LoginForm;
import kr.co.lovefans.devel.form.MemberForm;
import kr.co.lovefans.devel.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

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
    public String create(@Valid MemberForm form, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "views/subscr/join_page";
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
    public String login(@Valid LoginForm form, BindingResult bindingResult, Model model,HttpSession session){

        if(bindingResult.hasErrors()){
            return "login";
        }

        System.out.println("form = " + form.getMi_id());
        Member member = new Member();
        member.setMiId(form.getMi_id());
        member.setMiPwd(form.getMi_pwd());

        if(memberService.login(member)){

            Optional<Member> memberInfo = memberService.findById(form.getMi_id());
            System.out.println("회원닉 = " + memberInfo.get().getMiNick());

            model.addAttribute("model",memberInfo.get());

            session.setAttribute("session",memberInfo.get().getMiSeq());

            return "views/subscr/subscr_mypage";

        }else {

            } return "redirect:/";
        }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("session");
        return "redirect:/";

    }




}
