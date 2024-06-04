package com.choice.eduardo.soap.transform;

import com.choice.eduardo.soap.model.Amenity;
import com.choice.eduardo.soap.model.Hotel;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Transform Objects to others
 */
public class HotelDataMapper {

    /**
     * Convert HotelXml to Hotel Entity
     * @param hotel {HotelXml} - The Hotel object for the SOAP communication
     * @return {Hotel} - The Hotel entity
     */
    public static Hotel hotelXmlToHotel(com.choice.eduardo.spring.soap.gen.Hotel hotel) {
        Hotel hotelXml = new Hotel();
        hotelXml.setId(hotel.getId());
        hotelXml.setName(hotel.getName());
        hotelXml.setAddress(hotel.getAddress());
        hotelXml.setRatting(hotel.getRatting());
        // omit amenities and active fields
        return hotelXml;
    }

    /**
     * Convert Hotel Entity to HotelXml
     * @param hotel {Hotel} - The Hotel entity
     * @return {HotelXml} - The Hotel object for the SOAP communication
     */
    public static com.choice.eduardo.spring.soap.gen.Hotel hotelToHotelXml(Hotel hotel) {
        com.choice.eduardo.spring.soap.gen.Hotel hotelxml = new com.choice.eduardo.spring.soap.gen.Hotel();
        hotelxml.setId(hotel.getId());
        hotelxml.setName(hotel.getName());
        hotelxml.setAddress(hotel.getAddress());
        hotelxml.setRatting(hotel.getRatting());
        hotelxml.setAmenities(HotelDataMapper.buildAmenitiesJoiner(hotel.getAmenities()));
        return hotelxml;
    }

    /**
     * Convert Amenities to AmenitiesXml
     * @param amenity {Amenity} - The Amenity entity
     * @return {AmenityXml} - The Amenity Object for the SOAP communication
     */
    public static com.choice.eduardo.spring.soap.gen.Amenity amenityToAmenityXml(Amenity amenity) {
        com.choice.eduardo.spring.soap.gen.Amenity xmlAmenity = new com.choice.eduardo.spring.soap.gen.Amenity();
        xmlAmenity.setId(amenity.getId());
        xmlAmenity.setName(amenity.getName());
        return xmlAmenity;
    }

    /**
     * Build a String list of Amenities Ids base on a Amenity list
     * @param amenities {list} - The Amenity list
     * @return {string} - the string representation of the ids eg: (1,2,3,4)
     */
    public static String buildAmenitiesJoiner(List<Amenity> amenities) {
        List<Long> amenityIds = amenities.stream().map(Amenity::getId).collect(Collectors.toList());
        StringJoiner stringJoiner = new StringJoiner(",");
        amenityIds.stream().forEach((amenityId) -> stringJoiner.add(Long.toString(amenityId)));
        return stringJoiner.toString();
    }
}
