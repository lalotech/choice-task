package com.choice.eduardo.soap.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Amenities")
@NoArgsConstructor
public class Amenity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, name = "name")
    private String name;
    @Column(name = "active")
    @NonNull
    private Boolean active;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
