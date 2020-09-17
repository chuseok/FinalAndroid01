package org.dragon.service;

import java.util.HashMap;
import java.util.List;

import org.dragon.domain.CollectionVO;
import org.dragon.domain.DragonVO;
import org.login.domain.MemberVO;

public interface DragonService {

	public DragonVO getDragonByUser(String userId);
	public List<DragonVO> getAllDragonByUser(String userId);
	public void createDragon(DragonVO dragonVO);
	public boolean updateDragon(DragonVO dragonVO);
	public int getCoin(String userId);
	public boolean updateCoin(MemberVO vo);
	public int getEgg(String userId);
	public List<Integer> getDragonId(int productId);
	public boolean updateEquip(DragonVO dragonVO);
	public HashMap<String, String> getImageByLevel(int dragonId);
	public List<CollectionVO> getListByDragonLists();
	public String getLevel1Name(String productImage);
	public String getLevel2Name(String productImage);
	public String getLevel3Name(String productImage);
	//public int getBackgroundId(String userId);
	public boolean updateBackground(DragonVO dragonVO);
	//public boolean updateFoodValue(DragonVO dragonVO);
	public boolean delete(DragonVO dragonVO);
	public int getProductId(int dragonId);
	public boolean reviveDragon(String userId);
	public DragonVO getDragonByDragonId(DragonVO dragonVO);
}
