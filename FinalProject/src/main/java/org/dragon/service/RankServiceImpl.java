package org.dragon.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dragon.domain.DragonVO;
import org.dragon.domain.RankVO;
import org.dragon.mapper.DragonMapper;
import org.dragon.mapper.RankMapper;
import org.login.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class RankServiceImpl implements RankService {

	@Setter(onMethod_ = @Autowired)
	private RankMapper mapper;
	@Setter(onMethod_ = @Autowired)
	private DragonMapper dragonMapper;
	@Setter(onMethod_ = @Autowired)
	private MemberMapper userMapper;

	@Override
	public List<RankVO> getRankList() {
		List<RankVO> rankList = mapper.getRankList();
		// int level = vo.getTotalLevel();

		rankList.forEach(rank -> {
			DragonVO vo = dragonMapper.get(rank.getUserId());
			int level = vo.getTotalLevel();
			HashMap<String, String> dragonList = dragonMapper.getImageByLevel(vo.getDragonId());
			if (dragonList != null) {
				if (level < 10) {
					rank.setImg(dragonList.get("level0"));
				} else if (level >= 10 && level < 20) {
					rank.setImg(dragonList.get("level1"));
				} else if (level >= 20 && level < 30) {
					rank.setImg(dragonList.get("level2"));
				} else if (level >= 30) {
					rank.setImg(dragonList.get("level3"));
				}
			}
			Date lastDate = userMapper.read(rank.getUserId()).getLastConn();
			DateFormat format = new SimpleDateFormat("MM/dd HH:mm");
			String conn = format.format(lastDate);
			rank.setTotalConnection(conn);
		});

		return rankList;
	}

	@Override
	public RankVO getUserRank(String userId) {
		List<RankVO> rankList = getRankList();
		for (RankVO rank : rankList) {
			if (rank.getUserId().equals(userId)) {
				return rank;
			}
		}
		return null;
	}

}
