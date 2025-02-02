package com.unir.payments.facade;

import com.unir.payments.facade.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class BooksFacade {

  @Value("http://ms-books-catalogue/products/%s")
  private String getBookUrl;

  @Value("http://ms-books-catalogue/products/%s/stock")
  private String checkStockUrl;

  private final RestTemplate restTemplate;

  public Book getBook(Long id) {


      try {
          String url = String.format(getBookUrl, id);
          log.info("Getting product with ID {}. Request to {}", id, url);
          return restTemplate.getForObject(url, Book.class);
        } catch (HttpClientErrorException e) {
          log.error("Client Error: {}, Product with ID {}", e.getStatusCode(), id);
          return null;
        } catch (HttpServerErrorException e) {
          log.error("Server Error: {}, Product with ID {}", e.getStatusCode(), id);
          return null;
        } catch (Exception e) {
          log.error("Error: {}, Product with ID {}", e.getMessage(), id);
          return null;
        }
  }

    public boolean checkStock(Long id) {
        try {
            String url = String.format(checkStockUrl, id);
            log.info("Checking stock for book with ID {}. Request to {}", id, url);
            Integer stock = restTemplate.getForObject(url, Integer.class);
            return stock != null && stock > 0;  // verifcando si hay stock disponibl
        } catch (Exception e) {
            log.error("Error checking stock for book with ID {}: {}", id, e.getMessage());
            return false;
        }
    }

}
