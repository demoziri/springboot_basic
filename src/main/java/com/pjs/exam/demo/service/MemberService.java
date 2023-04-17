package com.pjs.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pjs.exam.demo.repository.MemberRepository;
import com.pjs.exam.demo.vo.MemberVO;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository; //Autowired보다 속도면에서 좋다(?)
	}

//	public List<MemberVO> getMemberList() {
//		return memberRepository.getMemberList();
//	}
//	
//	public MemberVO getMember(String id) {
//		return memberRepository.getMember(id);
//	}

	public void join(String loginId, String loginPw, 
			String name, String nickname, String cellphoneNo, String email) {
		memberRepository.registMember(loginId, loginId, name, nickname, cellphoneNo, cellphoneNo);
	}
}
