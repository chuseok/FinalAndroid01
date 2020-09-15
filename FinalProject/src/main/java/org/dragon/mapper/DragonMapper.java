package org.dragon.mapper;


import java.util.HashMap;
import java.util.List;

import org.dragon.domain.DragonVO;
import org.login.domain.MemberVO;

public interface DragonMapper {

	public DragonVO get(String userId);
	public List<DragonVO> getAll(String userId);
	public void create(DragonVO dragonVO);
	public int update(DragonVO dragonVO);
	public int getCoin(String userId);
	public int updateCoin(MemberVO vo);
	public int getegg(String userId);
	public List<Integer> getDragonId(int productId);
	public int updateEqiup(DragonVO dragonVO);
	public HashMap<String, String> getImageByLevel(int dragonId);
	public int getBackground(String userId);
	public int updateBackground(DragonVO dragonVO);
	public int updateFoodValue(DragonVO dragonVO);
	public int delete(DragonVO dragonVO);
	public int getProductId(int dragonId);
	public DragonVO getById(DragonVO dragonVO);
}
