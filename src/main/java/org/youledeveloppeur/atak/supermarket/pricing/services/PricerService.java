package org.youledeveloppeur.atak.supermarket.pricing.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.youledeveloppeur.atak.supermarket.pricing.domain.Product;

@Service
public class PricerService {
	
	
	public BigDecimal getPriceFromProduct(Product aProduct) {
		return aProduct.getProductPrice();
	}
	
	public BigDecimal getPriceFromListProduct(List<Product> aListProduct) {	
		return aListProduct.stream().map(aProduct -> aProduct.getProductPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	

}
