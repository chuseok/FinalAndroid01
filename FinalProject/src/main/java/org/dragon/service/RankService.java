package org.dragon.service;

import java.util.List;

import org.dragon.domain.DragonVO;
import org.dragon.domain.RankVO;

public interface RankService {

	public List<RankVO> getRankList();
	public RankVO getUserRank(String userId);
}
