package com.unir.libros.data;

import com.unir.libros.data.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface LibroJpaRepository extends JpaRepository<Libro, Long>, JpaSpecificationExecutor<Libro> {
}
