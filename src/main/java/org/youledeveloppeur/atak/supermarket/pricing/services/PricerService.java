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
	
	private Set<String> setPromotionThreeForADollar = new HashSet<>();
	
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
	Predicate<Product> isNotPromotionThreeForADollar = aProduct -> !setPromotionThreeForADollar.contains(aProduct.getProductName());
	
	public BigDecimal getPriceFromListProduct(List<Product> aListProduct) {	
		
		// Nomina case : sum the elements which are not in promotion
		BigDecimal resultWithoutPromotionThreeForADollar = aListProduct.stream()
				.filter(isNotPromotionThreeForADollar)
				.peek(aProduct -> log.info("product with no promotion : " + aProduct))
				.map(aProduct -> aProduct.getProductPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
		// PromotionThreeForADollar rule
		Stream<Product> streamProductPromotionThreeForADollar = aListProduct.stream().filter(isPromotionThreeForADollar);		
		Map<String, List<Product>> productNamePromotions = streamProductPromotionThreeForADollar.collect(Collectors.groupingBy(Product::getProductName));
		
		BigDecimal promotionThreeForADollarResult = BigDecimal.ZERO;
		for (Iterator<Entry<String, List<Product>>> iterator = productNamePromotions.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, List<Product>> entry = iterator.next();
			log.info(entry.getKey() + ", " + entry.getValue());
			BigDecimal priceProduct = entry.getValue().get(0).getProductPrice();
			BigDecimal[] quotienReminderPromotion = new BigDecimal(entry.getValue().size()).divideAndRemainder(PROMOTION_3_FOR_1_DOLLAR);
			
			// result = (quotient * 1) + (reminder * priceProduct)
			promotionThreeForADollarResult = promotionThreeForADollarResult.add(quotienReminderPromotion[0]).add(quotienReminderPromotion[1].multiply(priceProduct));
		}
			
		return resultWithoutPromotionThreeForADollar.add(promotionThreeForADollarResult);
	}	

}
