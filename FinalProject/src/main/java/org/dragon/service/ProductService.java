package org.dragon.service;

import java.util.List;

import org.dragon.domain.ProductVO;

public interface ProductService {

	public List<ProductVO> getList();
	public ProductVO getProductByName(String proname);
	public List<ProductVO> getProductByCategory(String category);
	public ProductVO getProductById(int productId);
	
}
