package com.choice.eduardo.soap.repository;

import com.choice.eduardo.soap.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelsRepository extends PagingAndSortingRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);

    Page<Hotel> findByNameContainsAndActive(String name, Pageable pageable, Boolean active);
}
