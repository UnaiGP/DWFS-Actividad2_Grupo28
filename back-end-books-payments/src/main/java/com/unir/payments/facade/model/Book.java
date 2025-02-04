package com.unir.payments.facade.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
	private Long id;
	private String title;
	private String country;
	private String description;
	private Boolean visible;
	private Double precio;
}

//esto es lo que espero recibir, hablando con el equipo para ver qeu sea así o modificarlo