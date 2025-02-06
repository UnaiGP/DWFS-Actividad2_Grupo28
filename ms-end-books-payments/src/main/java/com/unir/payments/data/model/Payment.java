package com.unir.payments.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "payments")  // Nombre de la tabla en la base de datos
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @Column(nullable = false)
    //    private Long paymentId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String status; // Estatus del pago, como x ejemplo, "COMPLETED", "PENDING"

    @ElementCollection
    @Column(name = "books")
    private List<Long> books;  // Almacena solo los IDs de los productos

}