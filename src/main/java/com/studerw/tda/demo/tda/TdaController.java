package com.studerw.tda.demo.tda;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.studerw.tda.client.TdaClient;
import com.studerw.tda.model.history.PriceHistory;
import com.studerw.tda.model.quote.Quote;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tda")
public class TdaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TdaController.class);

	private final Environment environment;

	private final TdaClient tdaClient;

	public TdaController(Environment environment, TdaClient tdaClient) {
		LOGGER.info("Instantiating TdaController - you should only see me once...");
		this.environment = environment;
		this.tdaClient = tdaClient;
	}


	@GetMapping("/hello")
	public ResponseEntity<String> getHello(Principal principal) {
		LOGGER.info("[{}] - get getHello", "hello");//principal.getName());
		return ResponseEntity.of(Optional.of("Hello"));
	}

	@GetMapping("/quotes")
	public ResponseEntity<List<Quote>> fetchQuotes(
		@RequestParam List<String> symbols,
		Principal principal) {
		LOGGER.info("[{}] - getQuotes, symbols={}", "user", symbols);
		if (symbols == null || symbols.isEmpty()) {
			throw new IllegalArgumentException("Must have at least one symbol parameter");
		}
		final List<Quote> quotes = this.tdaClient.fetchQuotes(symbols);
		return ResponseEntity.ok().
			contentType(MediaType.APPLICATION_JSON).
			body(quotes);
	}

	@GetMapping("/priceHistory")
	public ResponseEntity<PriceHistory> getPriceHistory(
		@RequestParam String symbol,
		Principal principal) {
		LOGGER.info("[{}] - priceHistory, symbol={}", "user", symbol);
		if (StringUtils.isBlank(symbol)) {
			throw new IllegalArgumentException("Symbol parameter cannot be empty.");
		}
		final PriceHistory priceHistory = this.tdaClient.priceHistory(symbol);
		return ResponseEntity.ok().
			contentType(MediaType.APPLICATION_JSON).
			body(priceHistory);
	}
}
