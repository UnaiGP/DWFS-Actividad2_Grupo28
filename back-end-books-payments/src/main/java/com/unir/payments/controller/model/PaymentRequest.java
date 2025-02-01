package com.unir.payments.controller.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

	@NotNull(message = "`paymentId` cannot be null")
	private Long paymentId;

	@NotNull(message = "`amount` cannot be null")
	private Double amount;

	@NotNull(message = "`paymentMethod` cannot be null")
	private String paymentMethod;

	@NotNull(message = "`status` cannot be null")
	private String status;

	@NotNull(message = "`books` cannot be null")
	private List<Long> books;
}
