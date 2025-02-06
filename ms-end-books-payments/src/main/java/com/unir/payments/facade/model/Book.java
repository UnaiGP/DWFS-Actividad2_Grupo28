package com.unir.payments.facade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // No incluirlo en las respuestas
	private Long id;
	private String titulo;
	private String isbn;
	private String autor;
	private Boolean visible;
	private String categoria;
	private Integer stock;
	private Double valoracion;
	private Double precio;
}

//esto es lo que espero recibir, hablando con el equipo para ver qeu sea así o modificarlo