package org.dragon.mapper;

import java.util.List;

import org.dragon.domain.RankVO;

public interface RankMapper {

	public List<RankVO> getRankList();
	public RankVO getUserRank(String userId);
	public String getProfile(String userId);
}
