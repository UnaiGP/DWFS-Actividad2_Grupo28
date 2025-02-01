package com.unir.payments.controller;

import com.unir.payments.data.model.Payment; // Modelo de Pago
import com.unir.payments.controller.model.PaymentRequest; // Solicitud de Pago
import com.unir.payments.service.PaymentsService; // Servicio de Pagos
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentsController {

    private final PaymentsService service; //Inyeccion por constructor mediante @RequiredArgsConstructor. Y, también es inyección por interfaz.

    // Endpoint para crear un pago (POST)
    @PostMapping("/payments")
    public ResponseEntity<Payment> createPayment(@RequestBody @Valid PaymentRequest request) { //Se valida con Jakarta Validation API

        log.info("Creating payment...");
        Payment created = service.createPayment(request);

        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    // Endpoint para obtener todos los pagos (GET)
    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getPayments() {
        List<Payment> payments = service.getPayments();

        // Si existen pagos, devuelve HTTP 200 con la lista
        if (payments != null) {
            return ResponseEntity.ok(payments);
        } else {
            // Si no hay pagos, devuelve una lista vacía
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    // Endpoint para obtener un pago por ID (GET)
    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        // Llama al servicio para obtener el pago por ID
        Payment payment = service.getPayment(id);

        // Si el pago se encuentra, devuelve HTTP 200
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            // Si no se encuentra el pago, devuelve HTTP 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }


    // Endpoint para eliminar un pago por ID (DELETE)
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        // Llama al servicio para eliminar el pago por ID
        boolean deleted = service.deletePayment(id);

        // Si se eliminó correctamente, devuelve HTTP 204 (que no hay contenido)
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            // Si no se pudo eliminar, devuelve HTTP 400 bad Request)
            return ResponseEntity.badRequest().build();
        }
    }
}
