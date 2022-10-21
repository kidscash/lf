package kr.co.lovefans.devel.service;

import kr.co.lovefans.devel.domain.Member;
import kr.co.lovefans.devel.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.transaction.Transactional;
import java.util.Optional;


@Transactional
public class MemberService {


    @Autowired private final MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public Long join(Member member){

        CheckDuplicateMemberNick(member);
        CheckDuplicateMember(member);
        EncodingPwd(member);


        /**
         * 시간을 여기서?
         * */
        java.util.Date dt = new java.util.Date();
        java.sql.Date date = new java.sql.Date(dt.getTime());
        member.setMiRegdt(date);
        memberRepository.save(member);
        return member.getMiSeq();
    }

    private void CheckDuplicateMember(Member member) {
        memberRepository.findByMiId(member.getMiId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 가입된 메일입니다.");
                });
    }

    private void CheckDuplicateMemberNick(Member member) {
        memberRepository.findByMiNick(member.getMiNick())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 사용된 닉네임 입니다.");
                });
    }

    private void EncodingPwd(Member member){
        String pwd = passwordEncoder.encode(member.getMiPwd());
        member.setMiPwd(pwd);
    }

    private Boolean ValidatePwd(Member member){

        Optional<Member> id = memberRepository.findByMiId(member.getMiId());
        id.orElseThrow(()->new IllegalStateException("아이디 존재하지 않음"));

        if(passwordEncoder.matches(member.getMiPwd(),id.get().getMiPwd())){

            return true;
        }else {

            return false;
        }


    }

    public Optional<Member> findOne(Long memberSeq){
        return memberRepository.findByMiSeq(memberSeq);
    }
    public Optional<Member> findById(String id){
        return memberRepository.findByMiId(id);
    }

    public Boolean login(Member member){



        return ValidatePwd(member);
    }



}
