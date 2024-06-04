package com.choice.eduardo.soap.repository;

import com.choice.eduardo.soap.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AmenitiesRepository extends JpaRepository<Amenity, Long> {
    List<Amenity> findAllByActiveIsTrue();
}
