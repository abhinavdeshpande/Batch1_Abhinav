package com.cg;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConvertorController {

	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		// String url = "http://localhost:8001/currency-exchange/from/{from}/to/{to}";
		/*
		 * String url = "http://localhost:8002/currency-exchange/from/{from}/to/{to}";
		 * Map<String, String> uriVariables = new HashMap<>(); uriVariables.put("from",
		 * from); uriVariables.put("to", to);
		 * 
		 * ResponseEntity<CurrencyConversionBean> respEntity = new
		 * RestTemplate().getForEntity(url, CurrencyConversionBean.class, uriVariables);
		 * 
		 * CurrencyConversionBean bean = respEntity.getBody();
		 */
		CurrencyConversionBean bean = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversionBean(bean.getId(), from, to, bean.getConversionMultiple(), quantity,
				quantity.multiply(bean.getConversionMultiple()), bean.getPort());
	}
}
