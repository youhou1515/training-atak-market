package org.youledeveloppeur.atak.supermarket.pricing.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	
	public Product() {}
	
	private String productName;
	
	// phase 3, since we can have productWeighed, productPrice can mean pricePerKg
	private BigDecimal productPrice;

}
