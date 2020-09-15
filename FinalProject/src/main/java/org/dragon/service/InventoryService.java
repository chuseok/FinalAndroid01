package org.dragon.service;

import java.util.List;

import javax.script.ScriptException;

import org.dragon.domain.DragonVO;
import org.dragon.domain.InventoryVO;
import org.dragon.domain.ProductVO;

public interface InventoryService {

	public void buy(InventoryVO vo, DragonVO dragonVO);
	public ProductVO use(String userId, int selected);
	public List<InventoryVO> orderList(String userId);
	public List<ProductVO> getInventory(String userId);
	public void refresh(InventoryVO vo);
	public InventoryVO get(int productId);
	public boolean check(String userId, int productId);
	public int valueSettingByItem(int productId, String userId, int dragonId) throws ScriptException;
}
