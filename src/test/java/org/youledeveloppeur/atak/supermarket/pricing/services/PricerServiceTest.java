package org.youledeveloppeur.atak.supermarket.pricing.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.youledeveloppeur.atak.supermarket.pricing.domain.ProductWeighed;

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
		pricerService.clearPromotionBuyTwoGetOneFree();
	}
	
	/**
	 * get the price from the product
	 */
	@Test
	public void getPriceFromProductTest1A() {
		
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
	public void getPriceFromListProductTest2A() {
		
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
	public void getPriceFromListProductTest2B() {
		
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
	public void getThreeForADollarTest3A() {
		
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
	public void getThreeForADollarTest3B() {
		
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
	public void getThreeForADollarTest3C() {
		
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
	public void getThreeForADollarTest3D() {
		
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
	public void getThreeForADollarTest3E() {
		
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
	public void getThreeForADollarTest3F() {
		
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
	public void getThreeForADollarTest3G() {
		
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
	public void getPriceFromProductWeighedTest4A() {
		
		// GIVEN : 2.90 eur/kg the apples
		String apple = "apple";
		BigDecimal priceApplePerKg = new BigDecimal(2.90); // 2.99 euros/kg
		BigDecimal quantity = BigDecimal.ONE; // per kg
		Product productApple = new ProductWeighed(apple, priceApplePerKg, quantity);
				
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromProduct(productApple);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = priceApplePerKg.multiply(quantity);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}
	
	/**
	 * 2.90 euro/kg, get the price for 1.5kg which is 4.35 euros.	
	 */
	@Test
	public void getPriceFromListProductWeighedTest4B() {
		
		// GIVEN : 2.90 eur/kg the apples
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
	
	/**
	 * add Promotion(4 rubber) + regular product(1 chair) + productWeighed(1.5 apple) in disorder 
	 * total 1.50 + 79.99 + 4.35 = 85.84 euros
	 */
	@Test
	public void getPriceFromListProductMixedTest4C() {
		
		// GIVEN : 2.90 eur/kg the apples
		String apple = "apple";
		BigDecimal priceApplePerKg = new BigDecimal(2.90); // 2.99 euros/kg
		BigDecimal quantity = new BigDecimal(1.5); // per kg
		Product productApple = new ProductWeighed(apple, priceApplePerKg, quantity);
		
		String rubber = "rubber";
		BigDecimal priceRubber = new BigDecimal(0.50);
		Product productRubber1 = new Product(rubber, priceRubber);
		Product productRubber2 = new Product(rubber, priceRubber);
		Product productRubber3 = new Product(rubber, priceRubber);
		Product productRubber4 = new Product(rubber, priceRubber);
		
		pricerService.addPromotionThreeForADollar(rubber);
		
		String chair = "chair";
		BigDecimal priceChair = new BigDecimal(79.99);
		Product productChair = new Product(chair, priceChair);
		
		List<Product> listProduct = Arrays.asList(productRubber3, productApple, productRubber1, productRubber2, productChair, productRubber4);
				
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = new BigDecimal(85.84);
		assertEquals(0, resultExpected.setScale(2, RoundingMode.HALF_UP).compareTo(resultActual.setScale(2, RoundingMode.HALF_UP)));	
	}
	
	/**
	 * promotion : buy 2 bottle of water of Evian, get 1 free
	 * buy 2 bottles of water of Evian (.65 euros the bottle), the amount is 1.30 euros.
	 */
	@Test
	public void buyTwoGetOneFreeTest5A() {
		
		// GIVEN : buy 2 bootles of water, get 1 free
		String bottleOfWaterEvian = "waterEvian";
		BigDecimal priceBottleOfWaterEvian = new BigDecimal(.65); // 
		Product productBottleOfWaterEvian1 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian2 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		
		List<Product> listProduct = Arrays.asList(productBottleOfWaterEvian1, productBottleOfWaterEvian2);
		
		pricerService.addPromotionBuyTwoGetOneFree(bottleOfWaterEvian);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = new BigDecimal(1.30);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}
	
	/**
	 * promotion : buy 2 bottle of water of Evian, get 1 free
	 * buy 3 bottles of water of Evian (.65 euros the bottle), the amount is still 1.30 euros because of the promotion
	 */
	@Test
	public void buyTwoGetOneFreeTest5B() {
		
		// GIVEN : buy 2 bootles of water, get 1 free
		String bottleOfWaterEvian = "waterEvian";
		BigDecimal priceBottleOfWaterEvian = new BigDecimal(.65); // 
		Product productBottleOfWaterEvian1 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian2 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian3 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		
		List<Product> listProduct = Arrays.asList(productBottleOfWaterEvian1, productBottleOfWaterEvian2, productBottleOfWaterEvian3);
		
		pricerService.addPromotionBuyTwoGetOneFree(bottleOfWaterEvian);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = new BigDecimal(1.30);
		assertEquals(0, resultExpected.compareTo(resultActual));	
	}
	
	/**
	 * promotion : buy 2 bottle of water of Evian, get 1 free
	 * buy 4 bottles of water of Evian (.65 euros the bottle), the amount is 1.95 euros, you pay 3 bottles of water in total (you get 1 free)
	 */
	@Test
	public void buyTwoGetOneFreeTest5C() {
		
		// GIVEN : buy 2 bootles of water, get 1 free
		String bottleOfWaterEvian = "waterEvian";
		BigDecimal priceBottleOfWaterEvian = new BigDecimal(.65); // 
		Product productBottleOfWaterEvian1 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian2 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian3 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian4 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		
		List<Product> listProduct = Arrays.asList(productBottleOfWaterEvian1, productBottleOfWaterEvian2, productBottleOfWaterEvian3, productBottleOfWaterEvian4);
		
		pricerService.addPromotionBuyTwoGetOneFree(bottleOfWaterEvian);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = new BigDecimal(1.95);
		assertEquals(0, resultExpected.setScale(2, RoundingMode.HALF_UP).compareTo(resultActual.setScale(2, RoundingMode.HALF_UP)));	
	}
	
	/**
	 * promotion : buy 2 bottle of water of Evian, get 1 free
	 * buy 5 bottles of water of Evian (.65 euros the bottle), the amount is 2.60 euros, you pay 4 bottles of water in total (you get 1 free)
	 */
	@Test
	public void buyTwoGetOneFreeTest5D() {
		
		// GIVEN : buy 2 bootles of water, get 1 free
		String bottleOfWaterEvian = "waterEvian";
		BigDecimal priceBottleOfWaterEvian = new BigDecimal(.65); // 
		Product productBottleOfWaterEvian1 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian2 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian3 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian4 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian5 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		
		List<Product> listProduct = Arrays.asList(productBottleOfWaterEvian1, productBottleOfWaterEvian2, productBottleOfWaterEvian3, productBottleOfWaterEvian4, productBottleOfWaterEvian5);
		
		pricerService.addPromotionBuyTwoGetOneFree(bottleOfWaterEvian);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = new BigDecimal(2.60);
		assertEquals(0, resultExpected.setScale(2, RoundingMode.HALF_UP).compareTo(resultActual.setScale(2, RoundingMode.HALF_UP)));	
	}
	
	/**
	 * promotion : buy 2 bottle of water of Evian, get 1 free
	 * buy 6 bottles of water of Evian (.65 euros the bottle), the amount is 2.60 euros, you pay 4 bottles of water in total (you get 2 free)
	 */
	@Test
	public void buyTwoGetOneFreeTest5E() {
		
		// GIVEN : buy 2 bootles of water, get 1 free
		String bottleOfWaterEvian = "waterEvian";
		BigDecimal priceBottleOfWaterEvian = new BigDecimal(.65); // 
		Product productBottleOfWaterEvian1 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian2 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian3 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian4 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian5 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian6 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		
		List<Product> listProduct = Arrays.asList(productBottleOfWaterEvian1, productBottleOfWaterEvian2, productBottleOfWaterEvian3, 
				productBottleOfWaterEvian4, productBottleOfWaterEvian5, productBottleOfWaterEvian6);
		
		pricerService.addPromotionBuyTwoGetOneFree(bottleOfWaterEvian);
		
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = new BigDecimal(2.60);
		assertEquals(0, resultExpected.setScale(2, RoundingMode.HALF_UP).compareTo(resultActual.setScale(2, RoundingMode.HALF_UP)));	
	}
	
	/**
	 * promotion : 3 for 1 doller for black pens
	 * promotion : buy 2 bottle of water of Evian, get 1 free
	 * list of products to buy : 4 bottles of water Evian (.65 a bottle) + 4 black pens (.45 a pen) + 1 kg of apple (2.90/kg) + 1 chair (79.99)
	 * result = 1.95 + 1.45 + 2.90 + 79.99 = 86.29
	 */
	@Test
	public void mixedProductsTestA() {
		
		// GIVEN : 
		// promotion 2 : buy 2 bootles of water, get 1 free
		String bottleOfWaterEvian = "waterEvian";
		BigDecimal priceBottleOfWaterEvian = new BigDecimal(.65); // 
		Product productBottleOfWaterEvian1 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian2 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian3 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		Product productBottleOfWaterEvian4 = new Product(bottleOfWaterEvian, priceBottleOfWaterEvian);
		
		// promotion 1 : black pen
		String blackPen = "black pen";
		BigDecimal priceBlackPen = new BigDecimal(0.45);
		Product productBlackPen1 = new Product(blackPen, priceBlackPen);
		Product productBlackPen2 = new Product(blackPen, priceBlackPen);
		Product productBlackPen3 = new Product(blackPen, priceBlackPen);
		Product productBlackPen4 = new Product(blackPen, priceBlackPen);
		
		String chair = "chair";
		BigDecimal priceChair = new BigDecimal(79.99);
		Product productChair = new Product(chair, priceChair);
		
		String apple = "apple";
		BigDecimal priceApplePerKg = new BigDecimal(2.90); // 2.90 euros/kg
		BigDecimal quantity = BigDecimal.ONE; // per kg
		Product productApple = new ProductWeighed(apple, priceApplePerKg, quantity);
		
		List<Product> listProduct = Arrays.asList(productBottleOfWaterEvian1, productBottleOfWaterEvian2, productBottleOfWaterEvian3, productBottleOfWaterEvian4
				, productBlackPen1, productBlackPen2, productBlackPen3, productBlackPen4, productChair, productApple);	
		pricerService.addPromotionBuyTwoGetOneFree(bottleOfWaterEvian);
		pricerService.addPromotionThreeForADollar(blackPen);
			
		// WHEN
		BigDecimal resultActual = pricerService.getPriceFromListProduct(listProduct);
		
		// TEST
		// get the promotion price 
		BigDecimal resultExpected = new BigDecimal(86.29);
		assertEquals(0, resultExpected.setScale(2, RoundingMode.HALF_UP).compareTo(resultActual.setScale(2, RoundingMode.HALF_UP)));	
	}

}
