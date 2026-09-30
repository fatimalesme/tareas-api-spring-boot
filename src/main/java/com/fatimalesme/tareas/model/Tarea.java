package com.fatimalesme.tareas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA: cada objeto Tarea es una fila de la tabla "tareas".
 */
@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING) // guarda "PENDIENTE" como texto, no como número
    @Column(nullable = false)
    private Estado estado = Estado.PENDIENTE;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist // se ejecuta justo antes de insertar en la base de datos
    void alCrear() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
}
