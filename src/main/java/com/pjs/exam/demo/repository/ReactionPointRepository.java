package com.pjs.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {
	
	@Select("""
			SELECT IFNULL(SUM(RP.point),0) AS S
			FROM reactionPoint AS RP
			WHERE RP.relTypeCode = #{relTypeCode}
			AND RP.id = #{id}
			AND RP.memberId = #{actorId}
			""")
	public int actorCanMakeReactionPoint(int actorId, String relTypeCode, int id);
}
