package com.choice.eduardo.rest.controller;

import com.choice.eduardo.rest.service.HotelService;
import com.choice.eduardo.spring.soap.gen.Amenity;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/v1/amenities")
public class AmenityController {
    private HotelService hotelService;

    @RequestMapping( method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody List<Amenity> readAmenities() {
        return hotelService.readAmenities();
    }
}
