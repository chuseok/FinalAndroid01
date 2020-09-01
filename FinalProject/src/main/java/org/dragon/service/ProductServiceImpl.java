package org.dragon.service;

import java.util.List;

import org.dragon.domain.ProductVO;
import org.dragon.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	@Setter(onMethod_ = @Autowired)
	private ProductMapper mapper;
	
	@Override
	public List<ProductVO> getList() {
		log.info("getList..........");
		return mapper.getList();
	}

	@Override
	public ProductVO getProductByName(String proname) {
		log.info("getList........"+proname);
		return mapper.get(proname);
	}

	@Override
	public List<ProductVO> getProductByCategory(String category) {
		log.info("getList........"+category);
		return mapper.getListByCategory(category);
	}

	@Override
	public ProductVO getProductById(int productId) {
		log.info("getList......"+productId);
		return mapper.getById(productId);
	}

}
