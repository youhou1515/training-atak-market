package org.youledeveloppeur.atak.supermarket.pricing.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.youledeveloppeur.atak.supermarket.pricing.domain.Product;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PricerServiceTest {
	
	@Rule 
	public TestRule watchman = new TestWatcher() {
	   protected void starting(Description description) {
		   log.info("Starting test: " + description.getMethodName());
	   }
	
	};
	
	@Autowired
	private PricerService pricerService;
	
	@Before
	public void before() {
		
	}
	
	@After
	public void after() {
		pricerService.clearPromotionThreeForADollar();
	}
	
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
		
		pricerService.addPromotionThreeForADollar(blackPen);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE;
		assertEquals(0, resultExpected.compareTo(resultActual));		
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
		
		pricerService.addPromotionThreeForADollar(blackPen);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(priceBlackPen);
		assertEquals(0, resultExpected.compareTo(resultActual));			
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
		
		pricerService.addPromotionThreeForADollar(blackPen);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(priceBlackPen).add(priceBlackPen);
		assertEquals(0, resultExpected.compareTo(resultActual));		
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
		
		pricerService.addPromotionThreeForADollar(blackPen);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(BigDecimal.ONE);
		assertEquals(0, resultExpected.compareTo(resultActual));		
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
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3, 
				productBlackPen4, productBlackPen5, productBlackPen6, productBlackPen7);
		
		pricerService.addPromotionThreeForADollar(blackPen);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price
		BigDecimal resultExpected = BigDecimal.ONE.add(BigDecimal.ONE).add(priceBlackPen);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}
	
	/**
	 * promotion 3 for a dollar : black pen + rubber
	 * sum 4 black pens + 3 rubber => 1.45 + 1 = 2.45 
	 */
	@Test
	public void getThreeForADollarTest3f() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		Product productBlackPen4 = new Product(blackPen, priceBlackPen);
		
		String rubber = "rubber";
		BigDecimal priceRubber = new BigDecimal(0.50);
		Product productRubber1 = new Product(rubber, priceRubber);
		Product productRubber2 = new Product(rubber, priceRubber);
		Product productRubber3 = new Product(rubber, priceRubber);
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3, productBlackPen4, 
				productRubber1, productRubber2, productRubber3);
		
		pricerService.addPromotionThreeForADollar(blackPen);
		pricerService.addPromotionThreeForADollar(rubber);
				
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = BigDecimal.ONE.add(priceBlackPen).add(BigDecimal.ONE);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}
	
	/**
	 * promotion 3 for a dollar : black pen + rubber
	 * sum 4 black pens + 3 rubber => 1.45 + 1 = 2.45 
	 */
	@Test
	public void getThreeForADollarTest3g() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		Product productBlackPen4 = new Product(blackPen, priceBlackPen);
		
		String chair = "chair";
		BigDecimal priceChair = new BigDecimal(79.99);
		Product productChair1 = new Product(chair, priceChair);
		
		List<Product> listProduct = Arrays.asList(productBlackPen1, productBlackPen2, productBlackPen3, productBlackPen4, 
				productChair1);
		
		pricerService.addPromotionThreeForADollar(blackPen);
				
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = BigDecimal.ONE.add(priceBlackPen).add(priceChair);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}
	
	/**
	 * 2.90 euro/kg, get the price for 1kg	
	 */
	@Test
	public void getPriceFromProductWeighedTest4a() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String apple = "apple";
		BigDecimal priceApplePerKg = new BigDecimal(2.90); // 2.99 euros/kg
		BigDecimal quantity = new BigDecimal(1.00); // per kg
		Product productApple = new ProductWeighed(apple, priceApplePerKg, quantity);
		
		List<Product> listProduct = Arrays.asList(productApple);
				
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromProduct(productApple);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = priceApplePerKg.multiply(quantity);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}
	
	/**
	 * 2.90 euro/kg, get the price for 1.5kg	
	 */
	@Test
	public void getPriceFromProductWeighedTest4b() {
		
		// GIVEN : we have a promotion for the black pen : 3 for 1 dollar
		String apple = "apple";
		BigDecimal priceApplePerKg = new BigDecimal(2.90); // 2.99 euros/kg
		BigDecimal quantity = new BigDecimal(1.5); // per kg
		Product productApple = new ProductWeighed(apple, priceApplePerKg, quantity);
		
		List<Product> listProduct = Arrays.asList(productApple);
				
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = priceApplePerKg.multiply(quantity);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}

}
