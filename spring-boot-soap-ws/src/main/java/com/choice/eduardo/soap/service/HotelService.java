package com.choice.eduardo.soap.service;


import com.choice.eduardo.soap.model.Amenity;
import com.choice.eduardo.soap.model.Hotel;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public interface HotelService {
    /**
     * the default 'page' size used in the pagination, this can come from the system properties
     */
    Integer MAX_PAGE_SIZE = 10;
    /**
     * the namespace value in the application
     */
    String NAMESPACE = "http://www.choice.com/eduardo/spring/soap/gen";

    /**
     * Read all the Hotels base on the filter map property and use the `page` number to enable pagination of the records
     *
     * @param fields {map} - The map of the filters
     * @param pageNumber {Integer} - the page number
     * @param pageSize {Integer} - the page size
     * @return
     */
    Pair<List<Hotel>, Map<String, Integer>> readBy(Map<String, Object> fields, Integer pageNumber, Integer pageSize);

    /**
     * Read all the active Amenities in the DB
     * @return {list} - A list of Amenities
     */
    List<Amenity> readAmenities();

    /**
     * Soft Delete Hotels in the DB using the id field
     * @param id {Long} - the ID of the Hotel
     */
    void deleteHotel(Long id);

    /**
     * Create Hotel if not exists already
     * @param hotel {Hotel} - the Hotel to create
     */
    void createHotel(com.choice.eduardo.spring.soap.gen.Hotel hotel);

    /**
     *
     * @param hotel {Hotel} - the Hotel to update
     */
    void updateHotel(com.choice.eduardo.spring.soap.gen.Hotel hotel);

}
