package com.unir.payments.facade;

import com.unir.payments.facade.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class BooksFacade {

    @Value("http://ms-books-catalogue/libros/%s")
    private String getBookUrl;

    private final RestTemplate restTemplate;

    public Book getBook(Long id) {

        try {
            String url = String.format(getBookUrl, id);
            log.info("Getting product with ID {}. Request to {}", id, url);
            log.info(restTemplate.getForObject(url, Book.class).toString());
            return restTemplate.getForObject(url, Book.class);
        } catch (HttpClientErrorException e) {
            log.error("Client Error: {}, Product with ID {}", e.getStatusCode(), id);
            log.error("Error response body: {}", e.getResponseBodyAsString()); // Log the error response
            return null;
        } catch (HttpServerErrorException e) {
            log.error("Server Error: {}, Product with ID {}", e.getStatusCode(), id);
            return null;
        } catch (Exception e) {
            log.error("Error: {}, Product with ID {}", e.getMessage(), id);
            return null;
        }
    }

    public boolean decrementStock(Long id) {
        try {
            String url = String.format(getBookUrl, id);
            log.info("Getting product with ID {}. Request to {}", id, url);
            Book libro = restTemplate.getForObject(url, Book.class);
            if (libro == null) {
                log.error("No se encontró el libro con ID {}", id);
                return false;
            }
            libro.setStock(libro.getStock() - 1);
            log.info("Actualizando libro: {}", libro);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Book> entity = new HttpEntity<>(libro, headers);
            ResponseEntity<Book> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Book.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Stock actualizado correctamente para el libro con ID {}", id);
                return true;
            } else {
                log.error("Error al actualizar el stock, respuesta: {}", response.getStatusCode());
                log.error("Error response body: {}", response.getBody()); // Log the error body
                return false;
            }
        } catch (HttpClientErrorException e) {
            log.error("Client Error: {}, Product with ID {}", e.getStatusCode(), id);
            log.error("Error response body: {}", e.getResponseBodyAsString()); // Log the error response
            return false;
        } catch (HttpServerErrorException e) {
            log.error("Server Error: {}, Product with ID {}", e.getStatusCode(), id);
            return false;
        } catch (Exception e) {
            log.error("Error: {}, Product with ID {}", e.getMessage(), id);
            return false;
        }
    }
}
