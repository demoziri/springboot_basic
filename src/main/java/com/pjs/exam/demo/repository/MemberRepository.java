package com.pjs.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pjs.exam.demo.vo.MemberVO;

@Mapper
public interface MemberRepository {
	
	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNo = #{cellphoneNo},
			email = #{email}
			""")
	public void join(@Param("loginId")String loginId, @Param("loginPw")String loginPw, 
			@Param("name")String name, @Param("nickname")String nickname, 
			@Param("cellphoneNo")String cellphoneNo, @Param("email")String email);
	
	@Select("""
			SELECT * 
			FROM `member`
			ORDER BY
			id desc
			""")
	public List<MemberVO> getMemberList ();
	
	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.id=#{id}
			""")
	public MemberVO getMember(@Param("id")int id);
	
	@Select("""
			SELECT LAST_INSERT_ID()
			""")
	public int getListInsertId();
	
}
