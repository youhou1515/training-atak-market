package org.youledeveloppeur.atak.supermarket.pricing.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class ProductWeighed extends Product {
	
	public ProductWeighed(String productName, BigDecimal productPricePerKg, BigDecimal quantityPerKg) {
		super(productName, productPricePerKg);
		this.quantity = quantityPerKg;
	}
	
	// quantity in kg
	private BigDecimal quantity;
	
	@Override
	public BigDecimal getProductPrice() {
		return quantity.multiply(super.getProductPrice());
	}

}
