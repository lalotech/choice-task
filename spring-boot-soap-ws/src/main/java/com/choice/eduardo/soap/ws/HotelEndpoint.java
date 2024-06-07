package com.choice.eduardo.soap.ws;

import com.choice.eduardo.soap.model.Hotel;
import com.choice.eduardo.soap.model.PageFields;
import com.choice.eduardo.soap.model.HotelFilter;
import com.choice.eduardo.soap.service.HotelService;
import com.choice.eduardo.soap.transform.HotelDataMapper;
import com.choice.eduardo.spring.soap.gen.CommonResponse;
import com.choice.eduardo.spring.soap.gen.CreateHotelRequest;
import com.choice.eduardo.spring.soap.gen.DeleteHotelRequest;
import com.choice.eduardo.spring.soap.gen.GetAmenitiesResponse;
import com.choice.eduardo.spring.soap.gen.GetHotelRequest;
import com.choice.eduardo.spring.soap.gen.GetHotelsRequest;
import com.choice.eduardo.spring.soap.gen.GetHotelsResponse;
import com.choice.eduardo.spring.soap.gen.UpdateHotelRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Endpoint
@Slf4j
public class HotelEndpoint {

    private HotelService hotelService;

    @PayloadRoot(namespace = HotelService.NAMESPACE, localPart = "createHotelRequest")
    @ResponsePayload
    public CommonResponse createHotel(@RequestPayload CreateHotelRequest request) {
        CommonResponse response = new CommonResponse();
        try {
            Hotel hotel = hotelService.createHotel(request.getHotel());
            response.setId(hotel.getId());
            response.setSuccess(true);
        } catch (IllegalArgumentException iae) {
            log.error("Error creating hotel", iae);
            response.setSuccess(false);
            response.setMessage(iae.getMessage());
        } catch (Exception e) {
            log.error("Error creating hotel", e);
            response.setSuccess(false);
        }
        return response;
    }

    @PayloadRoot(namespace = HotelService.NAMESPACE, localPart = "updateHotelRequest")
    @ResponsePayload
    public CommonResponse updateHotel(@RequestPayload UpdateHotelRequest request) {
        CommonResponse response = new CommonResponse();
        try {
            hotelService.updateHotel(request.getHotel());
            response.setSuccess(true);
        } catch (Exception e) {
            log.error("Error updating hotel", e);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PayloadRoot(namespace = HotelService.NAMESPACE, localPart = "deleteHotelRequest")
    @ResponsePayload
    public void deleteHotel(@RequestPayload DeleteHotelRequest request) {
        log.info("Request to delete hotel id:{}", request.getId());
        hotelService.deleteHotel(request.getId());
    }

    @PayloadRoot(namespace = HotelService.NAMESPACE, localPart = "getHotelsRequest")
    @ResponsePayload
    public GetHotelsResponse getHotels(@RequestPayload GetHotelsRequest request) {
        log.debug("getHotels name: {} page:{}", request.getName(), request.getPageNumber());
        GetHotelsResponse response = new GetHotelsResponse();

        Map<String, Object> filters = new HashMap<>();
        filters.put(HotelFilter.NAME.getKey(), (request.getName() != null ? request.getName() : ""));
        Integer pageNumber = (request.getPageNumber() != null ? request.getPageNumber() : 1);

        Pair<List<com.choice.eduardo.soap.model.Hotel>, Map<String, Integer>> data = hotelService.readBy(filters, pageNumber - 1, request.getPageSize());
        data.getFirst().stream().
                forEach(hotel ->
                        response.getHotel().add(HotelDataMapper.hotelToHotelXml(hotel))
                );
        // fill the metadata
        response.setPages(data.getSecond().get(PageFields.PAGES.getKey()));
        response.setElements(data.getSecond().get(PageFields.ELEMENTS.getKey()));
        response.setCurrentPage(data.getSecond().get(PageFields.CURRENT_PAGE.getKey()));
        return response;
    }

    @PayloadRoot(namespace = HotelService.NAMESPACE, localPart = "getHotelRequest")
    @ResponsePayload
    public GetHotelsResponse getHotel(@RequestPayload GetHotelRequest request) {
        log.debug("getHotel by id:{}", request.getId());
        com.choice.eduardo.soap.model.Hotel hotel = hotelService.readById(request.getId());
        GetHotelsResponse response = new GetHotelsResponse();
        if (hotel != null) {
            response.getHotel().add(HotelDataMapper.hotelToHotelXml(hotel));
        }
        return response;
    }

    @PayloadRoot(namespace = HotelService.NAMESPACE, localPart = "getAmenitiesRequest")
    @ResponsePayload
    public GetAmenitiesResponse getAmenities() {
        log.debug("getAmenities invoked");
        GetAmenitiesResponse response = new GetAmenitiesResponse();
        hotelService.readAmenities().stream()
                .forEach(amenity ->
                        response.getAmenity().add(HotelDataMapper.amenityToAmenityXml(amenity))
                );
        return response;
    }
}
