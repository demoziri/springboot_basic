package com.pjs.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pjs.exam.demo.service.MemberService;
import com.pjs.exam.demo.util.Ut;
import com.pjs.exam.demo.vo.MemberVO;
import com.pjs.exam.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<MemberVO> doJoin(String loginId, String loginPw, String name, String nickname,
			String cellphoneNo, String email) {
		if(Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId(을)를 입력해주세요.");
		}
		if(Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw(을)를 입력해주세요.");
		}
		if(Ut.empty(name)) {
			return ResultData.from("F-3", "name(을)를 입력해주세요.");
		}
		if(Ut.empty(nickname)) {
			return ResultData.from("F-4", "nickname(을)를 입력해주세요.");
		}
		if(Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5", "cellphoneNo(을)를 입력해주세요.");
		}
		if(Ut.empty(email)) {
			return ResultData.from("F-6", "email(을)를 입력해주세요.");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		if(joinRd.isFail()) {
			return (ResultData)joinRd;
		}
		
		MemberVO member = memberService.getMemberById(joinRd.getData1());
		return ResultData.newData(joinRd,member);
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
			return Ut.f("회원 아이디(%s)가 존재하지 않습니다.",id);
		}
		return member;
	}
}






