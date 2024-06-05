package com.choice.eduardo.soap.repository;

import com.choice.eduardo.soap.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsRepository extends PagingAndSortingRepository<Hotel, Long> {
    Hotel findByName(String name);

    Page<Hotel> findByNameContainsAndActive(String name, Pageable pageable, Boolean active);
}
