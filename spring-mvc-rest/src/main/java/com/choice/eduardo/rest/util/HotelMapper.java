package com.choice.eduardo.rest.util;

import com.choice.eduardo.rest.service.HotelService;
import com.choice.eduardo.rest.transport.HotelDTO;
import com.choice.eduardo.spring.soap.gen.Amenity;
import com.choice.eduardo.spring.soap.gen.Hotel;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Utility class to mapping objects to others
 */
public class HotelMapper {
    static Logger log = Logger.getLogger(HotelMapper.class);

    /**
     * Build the representative object in the REST API for the hotels model.
     *
     * @param hotels           {Hotel} - the list of object to be mapped
     * @param amenitiesCatalog {Amenity} - the catalog of Amenities
     * @return {HotelDTO} - the dto model to expose in the API
     */
    public static List<HotelDTO> hotelToHotelDTO(List<Hotel> hotels, List<Amenity> amenitiesCatalog) {
        log.info("hotelToHotelWrapper() hotels:" + hotels.size() + " catalog:" + amenitiesCatalog.size());
        Map<Long, Amenity> amenityMap = amenitiesCatalog.stream()
                .collect(Collectors.toMap(Amenity::getId, amenity -> amenity));
        return hotels.stream().map(hotel -> {
            List<String> amenities;
            if (hotel.getAmenities() != null && !hotel.getAmenities().isEmpty()) {
                amenities = Arrays.asList(hotel.getAmenities().split(","))
                        .stream().map(id -> amenityMap.get(Long.parseLong(id)).getName()).collect(Collectors.toList());
            } else
                amenities = Collections.EMPTY_LIST;

            return new HotelDTO(
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getAddress(),
                    hotel.getRating(),
                    amenities
            );
        }).collect(Collectors.toList());
    }

    /**
     * @param hotelDTO
     * @param amenitiesCatalog
     * @return
     */
    public static Hotel hotelDtoToHotel(HotelDTO hotelDTO, List<Amenity> amenitiesCatalog) {
        Map<String, Amenity> amenityMap = amenitiesCatalog.stream()
                .collect(Collectors.toMap(Amenity::getName, amenity -> amenity));
        List<String> amenitiesIds = hotelDTO.getAmenities().stream()
                .map(amenityName -> {
                    Amenity amenity = amenityMap.get(amenityName.toLowerCase());
                    if (amenity != null) {
                        return String.valueOf(amenity.getId());
                    }
                    return null;
                })
                .collect(Collectors.toList());
        amenitiesIds.removeIf(Objects::isNull);
        Hotel hotel = new Hotel();
        if (hotelDTO.getId() != null) {
            hotel.setId(hotelDTO.getId()); // we need this on updates
        }
        hotel.setName(hotelDTO.getName());
        hotel.setAddress(hotelDTO.getAddress());
        if (hotelDTO.getRating() != null) {
            hotel.setRating(hotelDTO.getRating());
        }
        if (amenitiesIds.size() > 0) {
            StringJoiner joiner = new StringJoiner(",");
            amenitiesIds.stream().forEach(joiner::add);
            hotel.setAmenities(joiner.toString());
        }
        return hotel;
    }
}
