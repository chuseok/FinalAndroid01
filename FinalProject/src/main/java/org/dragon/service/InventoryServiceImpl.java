package org.dragon.service;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.dragon.domain.DragonVO;
import org.dragon.domain.InventoryVO;
import org.dragon.domain.ProductVO;
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
public class InventoryServiceImpl implements InventoryService{
	
	@Setter(onMethod_ = @Autowired)
	private InventoryMapper mapper;
	@Setter(onMethod_ = @Autowired)
	private DragonMapper dragonMapper;
	@Setter(onMethod_ = @Autowired)
	private ProductMapper proMapper;
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
	@Transactional
	@Override
	public void buy(InventoryVO vo, DragonVO dragonVO) {
		log.info("buy service : "+vo);
		List<InventoryVO> inventory = mapper.findById(dragonVO.getUserId());
		ProductVO product = proMapper.getById(vo.getProductId());
		boolean checkName = false; 
		for(InventoryVO i : inventory) {
			if(i.getProductId()==vo.getProductId()) {
				i.setAmount(i.getAmount()+vo.getAmount());
				mapper.update(i);
				checkName =true;
			}
		}
		int balance = dragonMapper.getCoin(dragonVO.getUserId()) - product.getPrice()*vo.getAmount();//ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
		System.out.println("ï¿½ï¿½ï¿½ï¿½ ï¿½Ý¾ï¿½ : "+balance);
		if(!checkName){
			mapper.insert(vo);
			if(product.getCategory().equals("egg")) {//Ä«ï¿½×°ï¿½ï¿½ï¿½ eggï¿½Ï¶ï¿½ï¿½ï¿½ ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½
				List<Integer> dragonIdList = dragonMapper.getDragonId(vo.getProductId());
				int length = dragonIdList.size();
				int dragonId = dragonIdList.get((int)(Math.random()*length));//ï¿½ï¿½ï¿½ï¿½Æ®ï¿½ï¿½ Å©ï¿½â¿¡ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
				dragonVO.setDragonId(dragonId);
				dragonMapper.create(dragonVO);	
			}
			if(dragonMapper.getAll(dragonVO.getUserId()).size()==1) {
				//System.out.println("ï¿½Æ¹ï¿½ï¿½Íµï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½..");
				dragonVO.setEquip(true);
				dragonMapper.updateEqiup(dragonVO);
			}
			System.out.println(dragonMapper.getAll(dragonVO.getUserId()));
		}
		MemberVO user = memberMapper.read(vo.getUserId());
		user.setCoin(balance);//ï¿½ï¿½ï¿½Î°ï¿½ ï¿½ï¿½ï¿½ï¿½
		dragonMapper.updateCoin(user);
		
	}

	@Override
	public ProductVO use(String userId, int selected) {
		int amount = -1;
		ProductVO usedProduct = new ProductVO();
		List<InventoryVO> inventory = mapper.findById(userId);
		for(InventoryVO i : inventory) {
			if(i.getProductId()==selected) {
				i.setAmount(i.getAmount()-1);
				amount = i.getAmount();
				if(amount==0) {
					mapper.delete(i.getInventoryId());
				}
				else {
					mapper.update(i);
				}
				
				usedProduct = proMapper.getById(i.getProductId());
				usedProduct.setCnt(amount);
			}
		}
		return usedProduct;
		
	}

	@Override
	public List<ProductVO> getInventory(String userId) {
		List<InventoryVO> invenData = mapper.findById(userId);
		List<ProductVO> inventory = new ArrayList<ProductVO>();
		invenData.forEach(action ->{
			ProductVO buyProduct = proMapper.getById(action.getProductId());
			buyProduct.setCnt(action.getAmount());
			inventory.add(buyProduct);
		});
		return inventory;
	}
	public List<InventoryVO> orderList(String userId) {
		log.info("get list");
		
		return mapper.findById(userId);
	}

	@Override
	public void refresh(InventoryVO vo) {
		mapper.update(vo);
	}

	@Override
	public InventoryVO get(int productId) {
		log.info("get product : "+productId);
		return mapper.get(productId);
	}

	

	@Override
	public boolean check(String userId, int productId) {
		List<InventoryVO> orderList = orderList(userId);
		for(InventoryVO i : orderList) {
			if(i.getProductId()==productId) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public int valueSettingByItem(int productId, String userId, int dragonId) throws ScriptException {// for android
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
		String[] strArray = proMapper.getById(productId).getDescription().split(" ");
		DragonVO dragonVO = new DragonVO(userId, dragonId, false);
		DragonVO targetDragon = dragonMapper.getById(dragonVO);
		Integer result = -1;
		switch (strArray[0]) {
		case "Æ÷¸¸°¨":
			int value = targetDragon.getFoodValue();
			String foo = value+strArray[1];
			result = (Integer)engine.eval(foo);
			if(result>100) {
				result=100;
			}
			targetDragon.setFoodValue(result);
			dragonMapper.update(targetDragon);
			break;
		case "°æÇèÄ¡":
			value = dragonMapper.get(userId).getLevelValue();
			foo = value+strArray[1];
			result = (Integer)engine.eval(foo);
			if(result>100) {
				result=0;
				targetDragon.setTotalLevel(targetDragon.getTotalLevel()+1);
			}
			targetDragon.setLevelValue(result);
			dragonMapper.update(targetDragon);
			break;
		default:
			result=-1;
			break;
		}
		
		
		return result;
	}

	
}
