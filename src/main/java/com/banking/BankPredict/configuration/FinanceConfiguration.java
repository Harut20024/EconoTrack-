package com.banking.BankPredict.configuration;

import com.banking.BankPredict.exception.ResponseCodeException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Configuration
public class FinanceConfiguration {

    @Bean("exchangeClient")
    @Profile("localExchange")
    public RestClient localKbExchangeRestClient() {
        return RestClient.builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/ad3c442e3e742f955cb30f08/latest")
                .defaultStatusHandler(new KbStorageServiceResponseErrorHandler())
                .build();
    }


    @Bean("worldBankClient")
    @Profile("worldBankClient")
    public RestClient localWorldBankRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.worldbank.org/v2/")
                .defaultStatusHandler(new KbStorageServiceResponseErrorHandler())
                .build();
    }


    static class KbStorageServiceResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response)
                throws IOException {
            return !response.getStatusCode().is2xxSuccessful();
        }

        @Override
        public void handleError(ClientHttpResponse response)
                throws IOException {
            throw new ResponseCodeException(
                    response.getStatusCode(),
                    response.getStatusText() + " " + new String(response.getBody().readAllBytes())
            );
        }
    }
}
