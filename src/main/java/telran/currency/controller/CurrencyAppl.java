package telran.currency.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import telran.currency.dto.CurrencyDto;

public class CurrencyAppl {
	static final String URL = "http://data.fixer.io/api/latest";
	static final String API_KEY = "f304f67f677ccfc35df4c8c31d961dec";
//	static String str = URL.append(API_KEY).append("&symbols=EUR,ILS,RUB,BTC").toString();

	public static void main(String[] args) throws URISyntaxException, IOException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
				.queryParam("access_key", API_KEY)
				.queryParam("symbols", "EUR,ILS,RUB,BTC");
		RestTemplate restTemplate = new RestTemplate();
		RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET, builder.build().toUri());
		ResponseEntity<CurrencyDto> responseEntity = restTemplate.exchange(requestEntity, CurrencyDto.class);
		System.out.println(responseEntity.getStatusCodeValue());
		CurrencyDto currencies = responseEntity.getBody();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Input currency from:");
		String from = br.readLine();
		System.out.println("Input currency to:");
		String to = br.readLine();
		System.out.println("Input amount:");
		double amount = Double.parseDouble(br.readLine());
		double res = currencies.convertValues(from, to, amount);
		System.out.println(amount + " " + from + " = " + res + " " + to);
	}

}
