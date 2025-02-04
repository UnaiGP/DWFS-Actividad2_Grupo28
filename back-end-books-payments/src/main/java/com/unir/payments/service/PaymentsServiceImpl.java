package com.unir.payments.service;

import com.unir.payments.data.PaymentJpaRepository;
import com.unir.payments.data.model.Payment;
import com.unir.payments.facade.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // Lombok Logger
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.unir.payments.facade.BooksFacade;
import com.unir.payments.controller.model.PaymentRequest;

@Slf4j
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

    // Verifico si el stock está disponible para todos los libros
    for (Book book : books) {
          if (book.getStock() <= 0) {
            log.warn("Libro con ID {} no tiene stock disponible", book.getId());
            return null;  // Si no hay stock de algún libro, no se crea el pago
          }
        }

    // Verifico si todos los libros son válidos (tienen stock y visibles)
    if (books.size() != request.getBooks().size() ||
            books.stream().anyMatch(book -> !book.getVisible())) {
      return null;  // Si algún producto no es válido, no se crea el pago
    }

      // Calcula el total amount de los libros
    double amount = 0.0;

    // Sumando el precio de los libros, asignando 0 si el precio es null
    for (Book book : books) {
      amount += (book.getPrecio() != null) ? book.getPrecio() : 0.0;  // Si el precio es null, sumamos 0
    }

      // Crear el objeto Payment con los libross validados
      Payment payment = Payment.builder()
              .amount(amount)
              .paymentMethod(request.getPaymentMethod())
              .status(request.getStatus())
              .books(books.stream().map(Book::getId).collect(Collectors.toList()))
              .build();

      log.info(payment.toString());
      repository.save(payment);

    for (Book book : books) {
      booksFacade.decrementStock(book.getId());
    }

      return payment;
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
