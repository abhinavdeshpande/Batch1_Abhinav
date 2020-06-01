package com.cg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private ExchangeValueRepository repository;
	@Autowired
	private Environment env;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		// ExchangeValue exval = new
		// ExchangeValue(1000L,from,to,BigDecimal.valueOf(70));
		ExchangeValue exval = repository.findByFromAndTo(from, to);
		exval.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return exval;
	}
}
