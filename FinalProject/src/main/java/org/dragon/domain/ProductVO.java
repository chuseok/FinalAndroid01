package org.dragon.domain;

import lombok.Data;

@Data
public class ProductVO {
	private int productId;
	private String productName;
	private String productImage;
	private int price;
	private int cnt;
	private String category;
	private String description;
}
