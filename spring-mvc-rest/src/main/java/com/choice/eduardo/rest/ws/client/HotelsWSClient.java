package com.choice.eduardo.rest.ws.client;

import com.choice.eduardo.spring.soap.gen.CommonResponse;
import com.choice.eduardo.spring.soap.gen.CreateHotelRequest;
import com.choice.eduardo.spring.soap.gen.DeleteHotelRequest;
import com.choice.eduardo.spring.soap.gen.GetAmenitiesRequest;
import com.choice.eduardo.spring.soap.gen.GetAmenitiesResponse;
import com.choice.eduardo.spring.soap.gen.GetHotelRequest;
import com.choice.eduardo.spring.soap.gen.GetHotelsRequest;
import com.choice.eduardo.spring.soap.gen.GetHotelsResponse;
import com.choice.eduardo.spring.soap.gen.Hotel;
import com.choice.eduardo.spring.soap.gen.UpdateHotelRequest;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


public class HotelsWSClient extends WebServiceGatewaySupport {

    /**
     * Invoke the '<getAmenitiesRequest/>' request
     * @return the 'GetAmenitiesResponse' with the list of amenities
     */
    public GetAmenitiesResponse getAmenities() {
        GetAmenitiesRequest request = new GetAmenitiesRequest();
        GetAmenitiesResponse response = (GetAmenitiesResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    /**
     * Invoke the '<getHotelsRequest/>' request
     * @param name {String} - the name to used as filter
     * @param pageNumber {Integer} - the current pageNumber
     * @param pageSize {Integer} - the pageSize to limit the data.
     * @return {GetHotelsResponse} with the Hotel list
     */
    public GetHotelsResponse getHotels(String name, Integer pageNumber, Integer pageSize) {
        GetHotelsRequest request = new GetHotelsRequest();
        request.setName(name);
        request.setPageNumber(pageNumber);
        request.setPageSize(pageSize);
        GetHotelsResponse response = (GetHotelsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    /**
     * Invoke the '<getHotelRequest/>' request
     * @param id {Integer} - the 'id' to lookup in the Hotels collection
     * @return {GetHotelsResponse} with only one Hotel record
     */
    public GetHotelsResponse getHotel(Long id) {
        GetHotelRequest request = new GetHotelRequest();
        request.setId(id);
        GetHotelsResponse response = (GetHotelsResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    /**
     * Invoke the '<createHotelRequest/>'
     * @param hotel {Hotel} - the hotel to be created
     * @return {CommonResponse} - the response with success and message
     */
    public CommonResponse createHotel(Hotel hotel) {
        CreateHotelRequest request = new CreateHotelRequest();
        request.setHotel(hotel);
        CommonResponse response = (CommonResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    /**
     * Invoke the '<deleteHotelRequest/>'
     * @param hotelId {Long} - the hotel to be created
     * @return {CommonResponse} - the response with success and message
     */
    public CommonResponse deleteHotel(Long hotelId) {
        DeleteHotelRequest request = new DeleteHotelRequest();
        request.setId(hotelId);
        CommonResponse response = (CommonResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }

    /**
     * Invoke the '<updateHotelRequest/>'
     * @param hotel {Hotel} - the hotel to be updated
     * @return {CommonResponse} - the response with success and message
     */
    public CommonResponse updateHotel(Hotel hotel) {
        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setHotel(hotel);
        CommonResponse response = (CommonResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        return response;
    }
}
