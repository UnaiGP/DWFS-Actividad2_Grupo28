package com.unir.payments.service;

import com.unir.payments.data.PaymentJpaRepository;
import com.unir.payments.data.model.Payment;
import com.unir.payments.facade.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.unir.payments.facade.BooksFacade;
import com.unir.payments.controller.model.PaymentRequest;

@Service
@RequiredArgsConstructor  //mejor inyeccion por constructor mas por temas de testing
public class PaymentsServiceImpl implements PaymentsService {

  private final BooksFacade booksFacade;  // Facade para obtener productos desde el servicio de productos
  private final PaymentJpaRepository repository;  // Repositorio de pagos

  @Override
  public Payment createPayment(PaymentRequest request) {
    // Obtenemos los productos desde el microservicio de libros
    List<Book> books = request.getBooks().stream()
            .map(id ->  booksFacade.getBook(id))
            .filter(Objects::nonNull)
            .toList();

    if (books.size() != request.getBooks().size() ||
            books.stream().anyMatch(book -> !book.getVisible())) {
      return null;  // Si algún producto no es válido, no se crea el pago
    } else {
      // Crear el objeto Payment con los libross validados
      Payment payment = Payment
              .builder()
              .books(books.stream().map(Book::getId).collect(Collectors.toList()))
              .build();
      repository.save(payment);
      return payment;
    }
  }

  @Override
  public Payment getPayment(Long id) {
    return repository.findById(id).orElse(null);  // Buscar el pago por ID
  }

  @Override
  public List<Payment> getPayments() {
    List<Payment> payments = repository.findAll();  // Obtener todos los pagos
    return payments.isEmpty() ? null : payments;  // Si no hay pagos, retornar null
  }

  @Override
  public boolean deletePayment(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }
}
