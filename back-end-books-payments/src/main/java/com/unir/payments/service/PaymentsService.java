package com.unir.payments.service;

import com.unir.payments.data.model.Payment;
import com.unir.payments.controller.model.PaymentRequest;

import java.util.List;

public interface PaymentsService {

	// Crear un pago
	Payment createPayment(PaymentRequest request);

	// Obtener un pago por ID
	Payment getPayment(Long id);

	// Obtener todos los pagos
	List<Payment> getPayments();

	// Eliminar un pago por ID
	boolean deletePayment(Long id);
}


