package com.studerw.tda.demo.tda;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/tda")
public class TdaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TdaController.class);

	private final Environment environment;
	private final TdaClient tdaClient;

	public TdaController(Environment environment, TdaClient tdaClient) {
		LOGGER.info("Instantiating TdaController - you should only see me once...");
		this.environment = environment;
		this.tdaClient = tdaClient;
	}

	@GetMapping(value = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> ping() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("[{}] - ping", authentication.getName());
		return ResponseEntity.ok("pong");
	}

	@GetMapping(value = "/quotes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Quote>> fetchQuotes(
			@RequestParam List<String> symbols) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("[{}] - getQuotes, symbols={}", authentication.getName(), symbols);
		if (symbols == null || symbols.isEmpty()) {
			throw new IllegalArgumentException("Must have at least one symbol parameter");
		}
		final List<Quote> quotes = this.tdaClient.fetchQuotes(symbols);
		return ResponseEntity.ok().
				contentType(MediaType.APPLICATION_JSON).
				body(quotes);
	}

	@GetMapping(value = "/priceHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PriceHistory> getPriceHistory(@RequestParam String symbol) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("[{}] - priceHistory, symbol={}", authentication.getName(), symbol);
		if (StringUtils.isBlank(symbol)) {
			throw new IllegalArgumentException("Symbol parameter cannot be empty.");
		}
		final PriceHistory priceHistory = this.tdaClient.priceHistory(symbol);
		return ResponseEntity.ok().
				contentType(MediaType.APPLICATION_JSON).
				body(priceHistory);
	}

	@GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SecuritiesAccount>> getAccounts(
			@RequestParam(defaultValue = "false") boolean positions,
			@RequestParam(defaultValue = "false") boolean orders) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("[{}] - accounts, positions={}, orders={}",
				authentication.getName(), positions, orders);

		final List<SecuritiesAccount> accounts = this.tdaClient.getAccounts(positions, orders);
		return ResponseEntity.ok().
				contentType(MediaType.APPLICATION_JSON).
				body(accounts);
	}

	@GetMapping(value = "/instruments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Instrument>> searchInstruments(Query query) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("[{}] - query instruments: {}", authentication.getName(), query);

		final List<Instrument> instruments = this.tdaClient.queryInstruments(query);
		return ResponseEntity.ok().
				contentType(MediaType.APPLICATION_JSON).
				body(instruments);
	}

	@GetMapping(value = "/fundamentals", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FullInstrument> fundamentals(@RequestParam String symbol) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("[{}] - instrument fundamentals: {}", authentication.getName(), symbol);

		final FullInstrument fundamentalData = this.tdaClient.getFundamentalData(symbol);
		return ResponseEntity.ok().
				contentType(MediaType.APPLICATION_JSON).
				body(fundamentalData);
	}
}
