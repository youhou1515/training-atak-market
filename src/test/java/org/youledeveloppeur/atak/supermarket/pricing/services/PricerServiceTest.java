package org.youledeveloppeur.atak.supermarket.pricing.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.youledeveloppeur.atak.supermarket.pricing.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PricerServiceTest {
	
	@Autowired
	private PricerService pricerService;
	
	/**
	 * get the price from the product
	 */
	@Test
	public void getPriceFromProductTest1a() {
		
		// GIVEN
		String chair = "chair";
		BigDecimal priceChair = new BigDecimal(79.99);
		Product productChair = new Product(chair, priceChair);
		
		// WHEN
		BigDecimal priceChairActual = pricerService.getPriceFromProduct(productChair);
		
		// TEST
		assertEquals("not the same price of chair...", priceChair, priceChairActual);
		
	}
	
	/**
	 * add 1 simple product on the list and sum it, get the result the amount
	 */
	@Test
	public void getPriceFromListProductTest2a() {
		
		// GIVEN		
		String chair = "chair";
		BigDecimal priceChair = new BigDecimal(79.99);		
		Product productChair = new Product(chair, priceChair);
		
		List<Product> listProduct = Arrays.asList(productChair);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		
		// TEST
		BigDecimal resultExpected = priceChair;
		assertEquals("not the same price!", resultExpected, resultActual);
		
	}
	
	/**
	 * add 2 simple products on the list and sum it, get the result the amount
	 */
	@Test
	public void getPriceFromListProductTest2b() {
		
		// GIVEN
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		
		String chair = "chair";
		BigDecimal priceChair = new BigDecimal(79.99);		
		Product productChair = new Product(chair, priceChair);
		
		List<Product> listProduct = Arrays.asList(productChair, productBlackPen1);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		
		// TEST
		BigDecimal resultExpected = priceBlackPen.add(priceChair);
		assertEquals("not the same price!", resultExpected, resultActual);
		
	}

}
