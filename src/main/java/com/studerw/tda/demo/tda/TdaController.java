package com.studerw.tda.demo.tda;

import java.security.Principal;
import java.util.List;

import com.studerw.tda.client.TdaClient;
import com.studerw.tda.model.account.SecuritiesAccount;
import com.studerw.tda.model.history.PriceHistory;
import com.studerw.tda.model.instrument.FullInstrument;
import com.studerw.tda.model.instrument.Instrument;
import com.studerw.tda.model.instrument.Query;
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
		LOGGER.info("[{}] - get getHello", principal.getName());
		return ResponseEntity.ok("Hello");
	}

	@GetMapping("/quotes")
	public ResponseEntity<List<Quote>> fetchQuotes(
		@RequestParam List<String> symbols,
		Principal principal) {
		LOGGER.info("[{}] - getQuotes, symbols={}", principal.getName(), symbols);
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
		LOGGER.info("[{}] - priceHistory, symbol={}", principal.getName(), symbol);
		if (StringUtils.isBlank(symbol)) {
			throw new IllegalArgumentException("Symbol parameter cannot be empty.");
		}
		final PriceHistory priceHistory = this.tdaClient.priceHistory(symbol);
		return ResponseEntity.ok().
			contentType(MediaType.APPLICATION_JSON).
			body(priceHistory);
	}

	@GetMapping("/accounts")
	public ResponseEntity<List<SecuritiesAccount>> getAccounts(
		@RequestParam(defaultValue = "false") boolean positions,
		@RequestParam(defaultValue = "false") boolean orders,
		Principal principal) {
		LOGGER.info("[{}] - accounts, positions={}, orders={}",
			principal.getName(), positions, orders);

		final List<SecuritiesAccount> accounts = this.tdaClient.getAccounts(positions, orders);
		return ResponseEntity.ok().
			contentType(MediaType.APPLICATION_JSON).
			body(accounts);
	}

	@GetMapping("/instruments")
	public ResponseEntity<List<Instrument>> searchInstruments(Query query, Principal principal) {
		LOGGER.info("[{}] - query instruments: {}", principal.getName(), query);

		final List<Instrument> instruments = this.tdaClient.queryInstruments(query);
		return ResponseEntity.ok().
			contentType(MediaType.APPLICATION_JSON).
			body(instruments);
	}

	@GetMapping("/fundamentals")
	public ResponseEntity<FullInstrument> fundamentals(
		@RequestParam String symbol,
		Principal principal) {
		LOGGER.info("[{}] - instrument fundamentals: {}", principal.getName(), symbol);

		final FullInstrument fundamentalData = this.tdaClient.getFundamentalData(symbol);
		return ResponseEntity.ok().
			contentType(MediaType.APPLICATION_JSON).
			body(fundamentalData);
	}

}
