package org.youledeveloppeur.atak.supermarket.pricing.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	
	private String productName;
	private BigDecimal productPrice;

}
