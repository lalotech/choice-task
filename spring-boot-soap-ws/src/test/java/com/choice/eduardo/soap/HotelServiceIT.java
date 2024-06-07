package com.choice.eduardo.soap;

import com.choice.eduardo.soap.model.Amenity;
import com.choice.eduardo.soap.model.HotelFilter;
import com.choice.eduardo.soap.service.HotelService;
import com.choice.eduardo.soap.transform.HotelDataMapper;
import com.choice.eduardo.spring.soap.gen.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location = classpath:application-local.yml" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HotelServiceIT {

    String testHotelName = "Test Hotel Resort";
    Map<String,Object> filters = new HashMap<>();
    Integer pageSize = 5;

    @Autowired
    HotelService hotelService;

    @BeforeEach
    void beforeEach() {
        filters.clear();
    }

    @Test
    @Order(1)
    void createHotelTest() {
        //give
        Hotel hotel = new Hotel();
        hotel.setName(testHotelName);
        hotel.setAddress("Test Address");
        hotel.setAmenities("1,2");
        hotel.setRating(3);
        filters.put(HotelFilter.NAME.getKey(), testHotelName);

        //when
        hotelService.createHotel(hotel);

        //then
        Pair<List<com.choice.eduardo.soap.model.Hotel>,Map<String,Integer>> result = hotelService.readBy(filters, 0, pageSize);
        assertEquals(1, result.getFirst().size());

    }

    @Test
    @Order(2)
    void updateHotelTest() {
        //give
        filters.put(HotelFilter.NAME.getKey(), testHotelName);
        Pair<List<com.choice.eduardo.soap.model.Hotel>,Map<String,Integer>> before = hotelService.readBy(filters, 0, pageSize);
        System.out.println(before.getFirst());
        com.choice.eduardo.soap.model.Hotel current = before.getFirst().get(0);
        Hotel update = HotelDataMapper.hotelToHotelXml(current);
        update.setAddress("New Address");
        update.setRating(5);
        update.setAmenities("1,2,3,4");

        //when
        hotelService.updateHotel(update);

        //then
        Pair<List<com.choice.eduardo.soap.model.Hotel>,Map<String,Integer>> after = hotelService.readBy(filters, 0, pageSize);
        com.choice.eduardo.soap.model.Hotel newUpdate = after.getFirst().get(0);
        assertEquals(newUpdate.getAddress(), update.getAddress());
        assertEquals(newUpdate.getRating(), update.getRating());
        assertEquals(newUpdate.getName(), update.getName());
        String amenitiesIds = HotelDataMapper.buildAmenitiesJoiner(newUpdate.getAmenities());
        assertEquals(update.getAmenities(), amenitiesIds);
    }

    @Test
    @Order(3)
    void deleteHotelTest() {
        //give
        filters.put(HotelFilter.NAME.getKey(), testHotelName);
        Pair<List<com.choice.eduardo.soap.model.Hotel>,Map<String,Integer>> before = hotelService.readBy(filters, 0, pageSize);

        //when
        hotelService.deleteHotel(before.getFirst().get(0).getId());

        //then
        Pair<List<com.choice.eduardo.soap.model.Hotel>,Map<String,Integer>> after = hotelService.readBy(filters, 0, pageSize);
        assertEquals(0, after.getFirst().size());
    }

    @Test
    void readAmenitiesTest() {
        //when
        List<Amenity> amenities = hotelService.readAmenities();

        //then
        assertEquals(4, amenities.size());
        String amenitiesIds = HotelDataMapper.buildAmenitiesJoiner(amenities);
        assertEquals("1,2,3,4", amenitiesIds);
    }
}
