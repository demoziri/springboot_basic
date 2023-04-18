package com.pjs.exam.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjs.exam.demo.repository.MemberRepository;
import com.pjs.exam.demo.util.Ut;
import com.pjs.exam.demo.vo.MemberVO;
import com.pjs.exam.demo.vo.ResultData;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository; //Autowired보다 속도면에서 좋다(?)
	}

	public ResultData<Integer> join(String loginId, String loginPw, 
			        String name, String nickname, String cellphoneNo, String email) {
		//아이디 중복체크
		MemberVO oldmember = getMemberByLoginId(loginId);
		if(oldmember!=null) {
			return ResultData.from("F-7", Ut.f("해당 로그인아이디(%s)는 이미 사용중입니다.", loginId));
		}
		//이름 + 이메일 중복체크
		oldmember = getMemberByNameAndEmail(nickname, email);
		if(oldmember!=null) {
			return ResultData.from("F-8", Ut.f("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email));
		}
		
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		int id = memberRepository.getLastInsertId();
		
		return ResultData.from("S-1", "회원가입 완료!!!!!!.", "id", id);
	}
	public MemberVO getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}
	private MemberVO getMemberByNameAndEmail(String name,String email) {
		return memberRepository.getMemberByNameAndEmail(name,email);
	}

	public List<MemberVO> getMemberList(){
		return memberRepository.getMemberList();
	}
	
	public MemberVO getMemberById(int id) {
		return memberRepository.getMember(id);
	}

	
}
