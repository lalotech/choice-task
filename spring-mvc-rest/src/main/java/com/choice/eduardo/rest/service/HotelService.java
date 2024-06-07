package com.choice.eduardo.rest.service;

import com.choice.eduardo.rest.exception.BadRequestException;
import com.choice.eduardo.rest.exception.NotFoundException;
import com.choice.eduardo.rest.transport.HotelResponse;
import com.choice.eduardo.rest.transport.HotelDTO;
import com.choice.eduardo.rest.transport.Pagination;
import com.choice.eduardo.rest.util.HotelMapper;
import com.choice.eduardo.rest.ws.client.HotelsWSClient;
import com.choice.eduardo.spring.soap.gen.Amenity;
import com.choice.eduardo.spring.soap.gen.CommonResponse;
import com.choice.eduardo.spring.soap.gen.GetAmenitiesResponse;
import com.choice.eduardo.spring.soap.gen.GetHotelsResponse;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class HotelService {

    private static final Logger log = Logger.getLogger(HotelService.class);
    private HotelsWSClient hotelsWSClient;

    //this can be a system property
    public static final Integer MAX_RATING_VALUE = 10;
    public static final Integer MIN_RATING_VALUE = 1;

    public List<Amenity> readAmenities() {
        GetAmenitiesResponse response = hotelsWSClient.getAmenities();
        return response.getAmenity();
    }

    public HotelResponse readHotels(String name, Integer pageNumber, Integer pageSize) {
        log.info("filterByName: "+name+" pageNumber: "+pageNumber+" pageSize: "+pageSize);
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 1) {
            throw new BadRequestException("pageNumber and pageSize must be greater than 0");
        }
        GetAmenitiesResponse amenityResponse = hotelsWSClient.getAmenities();
        GetHotelsResponse response = hotelsWSClient.getHotels(name, pageNumber, pageSize);
        log.debug("readHotels pages:" + response.getPages() + " page:" + response.getCurrentPage() + " size:" + response.getHotel().size());
        return HotelResponse.builder()
                .data(HotelMapper.hotelToHotelDTO(response.getHotel(), amenityResponse.getAmenity()))
                .pagination(new Pagination(response.getCurrentPage(), response.getPages())).build();
    }

    public HotelDTO readHotel(Long hotelId) {
        GetHotelsResponse response = hotelsWSClient.getHotel(hotelId);
        if (response.getHotel() != null && response.getHotel().size() != 0) {
            return HotelMapper.hotelToHotelDTO(response.getHotel(), readAmenities()).get(0);
        }
        throw new NotFoundException("Hotel not found");
    }

    public HotelDTO createHotel(HotelDTO hotel) {
        validateHotel(hotel);
        CommonResponse response = hotelsWSClient.createHotel(HotelMapper.hotelDtoToHotel(hotel, readAmenities()));
        if (!response.isSuccess()) {
            throw new BadRequestException(response.getMessage());
        }
        return readHotel(response.getId());
    }

    public void deleteHotel(Long hotelId) {
        CommonResponse response = hotelsWSClient.deleteHotel(hotelId);
        if (!response.isSuccess()) {
            if (response.getMessage() != null && response.getMessage().contains("not found")) {
                throw new NotFoundException(response.getMessage());
            }
            throw new BadRequestException(response.getMessage());
        }
    }

    public HotelDTO updateHotel(HotelDTO hotel) {
        validateHotel(hotel);
        CommonResponse response = hotelsWSClient.updateHotel(HotelMapper.hotelDtoToHotel(hotel, readAmenities()));
        if (!response.isSuccess()) {
            throw new BadRequestException(response.getMessage());
        }
        return readHotel(response.getId());
    }

    private void validateHotel(HotelDTO hotel) {
        Arrays.asList(hotel.getName(), hotel.getAddress()).stream()
                .forEach(p -> {
                    if (p == null) {
                        throw new BadRequestException("parameter null");
                    }
                });
        //Rating limits
        if (hotel.getRating() != null) {
            Integer ratingLimits =
                    hotel.getRating() > HotelService.MAX_RATING_VALUE ?
                            HotelService.MAX_RATING_VALUE : hotel.getRating() <= 0 ?
                            HotelService.MIN_RATING_VALUE : hotel.getRating();
            hotel.setRating(ratingLimits);
        }
    }
}
