package com.choice.eduardo.soap.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "hotels")
@NoArgsConstructor
@RequiredArgsConstructor
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(nullable = false, unique = true, name = "name")
    private String name;
    @Column(nullable = false, name = "address")
    @NonNull
    private String address;
    @Column(nullable = false, name = "rating", columnDefinition = "integer default 0")
    @NonNull
    private Integer rating;
    @Column(name = "active")
    @NonNull
    private Boolean active;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="hotel_amenities",
            joinColumns=
            @JoinColumn(name="hotel_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="amenity_id", referencedColumnName="id")
    )
    private List<Amenity> amenities;
}
