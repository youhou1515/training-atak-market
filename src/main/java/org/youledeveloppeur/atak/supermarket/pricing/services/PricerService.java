package org.youledeveloppeur.atak.supermarket.pricing.services;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.youledeveloppeur.atak.supermarket.pricing.domain.Product;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PricerService {
	
	public static final BigDecimal PROMOTION_3_FOR_1_DOLLAR = new BigDecimal(3);
	public static final BigDecimal PROMOTION_BUY_2_GET_1_FREE = new BigDecimal(3);
	public static final BigDecimal BIG_DECIMAL_2 = new BigDecimal(2);
	
	private Set<String> setPromotionThreeForADollar = new HashSet<>();
	private Set<String> setPromotionBuyTwoGetOneFree = new HashSet<>();


	public void addPromotionBuyTwoGetOneFree(String aProductName) {
	setPromotionBuyTwoGetOneFree.add(aProductName);		
	}
	
	public void clearPromotionBuyTwoGetOneFree() {
		setPromotionBuyTwoGetOneFree.clear();
	}	
		
	public void addPromotionThreeForADollar(String aProductName) {
		setPromotionThreeForADollar.add(aProductName);		
	}
	
	public void clearPromotionThreeForADollar() {
		setPromotionThreeForADollar.clear();
	}
	
	public BigDecimal getPriceFromProduct(Product aProduct) {
		return aProduct.getProductPrice();
	}
	
	Predicate<Product> isPromotionThreeForADollar = aProduct -> setPromotionThreeForADollar.contains(aProduct.getProductName());
	Predicate<Product> isPromotionBuyTwoGetOneFree = aProduct -> setPromotionBuyTwoGetOneFree.contains(aProduct.getProductName());
	Predicate<Product> isNotPromotionAtAll = aProduct -> !setPromotionThreeForADollar.contains(aProduct.getProductName()) && !setPromotionBuyTwoGetOneFree.contains(aProduct.getProductName());
		
	public BigDecimal getPriceFromListProduct(List<Product> aListProduct) {	
		
		// Nomina case : sum the elements which are not in promotion
		BigDecimal withoutPromotionAtAllResult = aListProduct.stream()
				.filter(isNotPromotionAtAll)
				.peek(aProduct -> log.info("product with no promotion : " + aProduct))
				.map(aProduct -> aProduct.getProductPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
		// PROMOTION 1 : ThreeForADollar rule
		Stream<Product> streamProductPromotionThreeForADollar = aListProduct.stream().filter(isPromotionThreeForADollar);		
		Map<String, List<Product>> productNamePromotionThreeForADollar = streamProductPromotionThreeForADollar.collect(Collectors.groupingBy(Product::getProductName));
		
		BigDecimal promotionThreeForADollarResult = BigDecimal.ZERO;
		Map.Entry<String, List<Product>> entryP1 = null;
		int productNumberP1 = 0;
		BigDecimal priceProductP1 = null;
		BigDecimal[] quotienReminderPromotionP1 = null;
		BigDecimal quotienP1, reminderP1 = null;
		for (Iterator<Entry<String, List<Product>>> iterator = productNamePromotionThreeForADollar.entrySet().iterator(); iterator.hasNext();) {
			entryP1 = iterator.next();
			log.info("PROMOTION THREE_FOR_ONE_DOLLAR FOR " + entryP1.getKey().toUpperCase() + ", " + entryP1.getValue());
			priceProductP1 = entryP1.getValue().get(0).getProductPrice();
			productNumberP1 = entryP1.getValue().size();
			quotienReminderPromotionP1 = new BigDecimal(productNumberP1).divideAndRemainder(PROMOTION_3_FOR_1_DOLLAR);
			quotienP1 = quotienReminderPromotionP1[0];
			reminderP1 = quotienReminderPromotionP1[1];
			
			// result = (quotient * 1 (dollar)) + (reminder * priceProduct)
			promotionThreeForADollarResult = promotionThreeForADollarResult.add(quotienP1).add(reminderP1.multiply(priceProductP1));
		}
		
		// PROMOTION 2 : BuyTwoGetOneFree rule
		Stream<Product> streamProductPromotionBuyTwoGetOneFree = aListProduct.stream().filter(isPromotionBuyTwoGetOneFree);		
		Map<String, List<Product>> productNamePromotionBuyTwoGetOneFree = streamProductPromotionBuyTwoGetOneFree.collect(Collectors.groupingBy(Product::getProductName));
		
		BigDecimal promotionBuyTwoGetOneFreeResult = BigDecimal.ZERO;
		int productNumberP2 = 0;
		BigDecimal priceProductP2 = null;
		BigDecimal[] quotienReminderPromotionP2 = null;
		BigDecimal quotienP2, reminderP2 = null;
		Map.Entry<String, List<Product>> entryP2 = null;
		for (Iterator<Entry<String, List<Product>>> iterator = productNamePromotionBuyTwoGetOneFree.entrySet().iterator(); iterator.hasNext();) {
			entryP2 = iterator.next();
			log.info("PROMOTION BUY_TWO GET_ONE_FREE FOR " + entryP2.getKey().toUpperCase() + ", " + entryP2.getValue());
			priceProductP2 = entryP2.getValue().get(0).getProductPrice();
			productNumberP2 = entryP2.getValue().size();
			quotienReminderPromotionP2 = new BigDecimal(productNumberP2).divideAndRemainder(PROMOTION_BUY_2_GET_1_FREE);
			quotienP2 = quotienReminderPromotionP2[0];
			reminderP2 = quotienReminderPromotionP2[1];
			
			// example buy two get one free implementation : 
			// 1 product = price 1 time 
			// 2 products = price 2 times
			// 3 products = price 2 times, get one free
			// 4 products = price 3 times, get one free
			// 5 products = price 4 times, get one free
			// 6 products = price 4 times, get two free
			// 7 products = price 5 times, get two free
			// ...
			// formula : result = ((2 * quotient) + reminder) * priceProduct)
			BigDecimal calculateNumberProductToPay = ((BIG_DECIMAL_2.multiply(quotienP2)).add(reminderP2));
			promotionBuyTwoGetOneFreeResult = promotionBuyTwoGetOneFreeResult.add(calculateNumberProductToPay.multiply(priceProductP2));
		}
			
		// resultat final 
		return withoutPromotionAtAllResult.add(promotionThreeForADollarResult).add(promotionBuyTwoGetOneFreeResult);
	}	

}
