package org.dragon.service;

import java.util.HashMap;
import java.util.List;

import org.dragon.domain.DragonVO;
import org.dragon.mapper.DragonMapper;
import org.dragon.mapper.InventoryMapper;
import org.dragon.mapper.ProductMapper;
import org.login.domain.MemberVO;
import org.login.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class DragonServiceImpl implements DragonService {

	@Setter(onMethod_ = @Autowired)
	private DragonMapper mapper;
	@Setter(onMethod_ = @Autowired)
	private InventoryMapper invenMapper;
	@Setter(onMethod_ = @Autowired)
	private ProductMapper proMapper;
	@Setter(onMethod_ = @Autowired)
	private MemberMapper userMapper;
	
	@Override
	public DragonVO getDragonByUser(String userId) {
		log.info("get........."+userId);
		return mapper.get(userId);
	}
	@Override
	public List<DragonVO> getAllDragonByUser(String userId) {
		return mapper.getAll(userId);
	}

	@Override
	public void createDragon(DragonVO dragonVO) {
		log.info("create..........");
		mapper.create(dragonVO);
	}

	@Override
	public boolean updateDragon(DragonVO dragonVO) {
		log.info("update..........");
		return mapper.update(dragonVO)==1;
	}

	@Override
	public int getCoin(String userId) {
		log.info("get coin........");
		return mapper.getCoin(userId);
	}

	@Override
	public boolean updateCoin(MemberVO vo) {
		log.info("update coin.........");
		return mapper.updateCoin(vo)==1;
	}

	@Override
	public int getEgg(String userId) {
		return mapper.getegg(userId);
	}

	@Override
	public List<Integer> getDragonId(int productId) {
		return mapper.getDragonId(productId);
	}

	@Override
	public boolean updateEquip(DragonVO dragonVO) {
		log.info("update equip.........");
		return mapper.updateEqiup(dragonVO)==1;
	}

	@Override
	public HashMap<String, String> getImageByLevel(int dragonId) {
		return mapper.getImageByLevel(dragonId);
	}
	@Override
	public boolean updateBackground(DragonVO dragonVO) {
		// TODO Auto-generated method stub
		return mapper.updateBackground(dragonVO)==1;
	}
	/*
	@Override
	public int getBackgroundId(String userId) {
		// TODO Auto-generated method stub
		return mapper.getBackground(userId);
	}
	@Override
	public boolean updateFoodValue(DragonVO dragonVO) {
		return mapper.updateFoodValue(dragonVO)==1;
	}*/
	@Transactional
	@Override
	public boolean delete(DragonVO dragonVO) {
		int proId = mapper.getProductId(dragonVO.getDragonId());
		invenMapper.deleteCostume(proId);
		return mapper.delete(dragonVO)==1;
	}
	@Override
	public int getProductId(int dragonId) {
		return mapper.getProductId(dragonId);
	}
	
	@Transactional
	@Override
	public boolean reviveDragon(String userId) {
		DragonVO dragonVO = mapper.get(userId);
		int coin = mapper.getCoin(userId)-20000;
		MemberVO user = userMapper.read(userId);
		user.setCoin(coin);
		mapper.updateCoin(user);
		dragonVO.setFoodValue(100);
		return mapper.update(dragonVO)==1;
		
	}
	@Override
	public DragonVO getDragonByDragonId(DragonVO dragonVO) {
		return mapper.getById(dragonVO);
	}
	


}
