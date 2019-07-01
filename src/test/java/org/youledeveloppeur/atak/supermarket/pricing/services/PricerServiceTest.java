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
	
	/**
	 * apply a promotion "promotion 3 for a dollar" for the black pen product
	 * get the price for 3 black pens which is 1 dollar
	 */
	@Test
	public void getThreeForADollarTest3a() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE;
		assertEquals(resultExpected, resultActual);		
	}
	
	/**
	 * apply a promotion "promotion 3 for a dollar" for the black pen product
	 * get the price for 4 black pens which is 1 dollar + price of 1 black pen 
	 */
	@Test
	public void getThreeForADollarTest3b() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		Product productBlackPen4 = new Product(blackPen, priceBlackPen);
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3, productBlackPen4);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(priceBlackPen);
		assertEquals(resultExpected, resultActual);		
	}
	
	/**
	 * apply a promotion "promotion 3 for a dollar" for the black pen product
	 * get the price for 5 black pens which is 1 dollar + price of 2 black pen 
	 */
	@Test
	public void getThreeForADollarTest3c() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		Product productBlackPen4 = new Product(blackPen, priceBlackPen);
		Product productBlackPen5 = new Product(blackPen, priceBlackPen);
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3, productBlackPen4, productBlackPen5);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(priceBlackPen).add(priceBlackPen);
		assertEquals(resultExpected, resultActual);		
	}
	
	/**
	 * apply a promotion "promotion 3 for a dollar" for the black pen product
	 * get the price for 6 black pens which is 2 dollar
	 */
	@Test
	public void getThreeForADollarTest3d() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		Product productBlackPen4 = new Product(blackPen, priceBlackPen);
		Product productBlackPen5 = new Product(blackPen, priceBlackPen);
		Product productBlackPen6 = new Product(blackPen, priceBlackPen);
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3, productBlackPen4, productBlackPen5, productBlackPen6);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(BigDecimal.ONE);
		assertEquals(resultExpected, resultActual);		
	}
	
	/**
	 * apply a promotion "promotion 3 for a dollar" for the black pen product
	 * get the price for 7 black pens which is 2 dollar + price of 1 black pen
	 */
	@Test
	public void getThreeForADollarTest3e() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		Product productBlackPen4 = new Product(blackPen, priceBlackPen);
		Product productBlackPen5 = new Product(blackPen, priceBlackPen);
		Product productBlackPen6 = new Product(blackPen, priceBlackPen);
		Product productBlackPen7 = new Product(blackPen, priceBlackPen);
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3, productBlackPen4, productBlackPen5, productBlackPen6, productBlackPen7);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(BigDecimal.ONE).add(priceBlackPen);
		assertEquals(resultExpected, resultActual);		
	}

}
