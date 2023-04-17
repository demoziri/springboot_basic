package com.pjs.exam.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjs.exam.demo.repository.MemberRepository;
import com.pjs.exam.demo.vo.MemberVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository; //Autowired보다 속도면에서 좋다(?)
	}

	public int join(String loginId, String loginPw, 
			        String name, String nickname, String cellphoneNo, String email) {
		MemberVO oldmember = getMemberByLoginId(loginId);
		if(oldmember!=null) {
			return -1;
		}
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		
		return memberRepository.getLastInsertId();
	}
	private MemberVO getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public List<MemberVO> getMemberList(){
		return memberRepository.getMemberList();
	}
	
	public MemberVO getMemberById(int id) {
		return memberRepository.getMember(id);
	}

	
}
