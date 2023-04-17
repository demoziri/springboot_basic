package com.pjs.exam.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjs.exam.demo.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository; //Autowired보다 속도면에서 좋다(?)
	}

	public void join(String loginId, String loginPw, 
			String name, String nickname, String cellphoneNo, String email) {
		memberRepository.join(loginId, loginId, name, nickname, cellphoneNo, cellphoneNo);
	}
}
