package com.pjs.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pjs.exam.demo.service.MemberService;
import com.pjs.exam.demo.vo.MemberVO;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public MemberVO doJoin(String loginId, String loginPw, String name, String nickname,
			String cellphoneNo, String email) {
		int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		MemberVO member = memberService.getMemberById(id);
		return member;
	}
	
	
	@RequestMapping("/usr/member/getMembers")
	@ResponseBody
	public List<MemberVO> getMembers() {
		
		return memberService.getMemberList();
	}
	
	
	@RequestMapping("usr/member/getMember")
	@ResponseBody
	public Object getMember(int id) {
		MemberVO member = memberService.getMemberById(id);
		
		if(member==null) {
			return "회원 아이디" + id + "가 존재하지 않습니다.";
		}
		return member;
	}
}






