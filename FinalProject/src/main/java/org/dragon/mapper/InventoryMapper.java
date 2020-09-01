package org.dragon.mapper;

import java.util.List;

import org.dragon.domain.InventoryVO;

public interface InventoryMapper {

	public void insert(InventoryVO vo);
	public void delete(String inventoryId);
	public List<InventoryVO> findById(String userId);
	public void update(InventoryVO vo);
	public InventoryVO get(int productId);
	public void deleteCostume(int productId);
}
