package com.pjs.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pjs.exam.demo.vo.MemberVO;

@Mapper
public interface MemberRepository {

	public MemberVO getMember(String id);
	
	public List<MemberVO> getMemberList();
	
	public void registMember(String loginId, String loginPw, 
			String name, String nickname, String cellphoneNo, String email ); 
	
	
	
}
