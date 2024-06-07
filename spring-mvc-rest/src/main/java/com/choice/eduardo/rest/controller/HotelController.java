package com.choice.eduardo.rest.controller;

import com.choice.eduardo.rest.exception.BadRequestException;
import com.choice.eduardo.rest.exception.NotFoundException;
import com.choice.eduardo.rest.service.HotelService;
import com.choice.eduardo.rest.transport.HotelResponse;
import com.choice.eduardo.rest.transport.HotelDTO;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@Controller
@RequestMapping(value = "/v1/hotels")
public class HotelController {

    private HotelService hotelService;
    private final Logger log = Logger.getLogger(HotelController.class);

    /**
     * Expose the Hotels collection with pagination over the records and filter by name
     * @param name {String} - the `searchName` - filter to apply over the records
     * @param pageNumber {Integer} - the page number to display
     * @param pageSize {Integer} - the pageSize
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HotelResponse> getHotels(
            @RequestParam(name = "searchName", required = false) String name,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize",required = false, defaultValue = "1") Integer pageSize) {

        try {
            return ResponseEntity.ok(hotelService.readHotels(name, pageNumber, pageSize));
        } catch (BadRequestException bre) {
            log.warn(bre.getMessage(), bre);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Retrieve a Hotel by `id`
     * @param id {Long} - the id of the Hotel
     * @return {HotelDTO} - the Hotel data
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HotelDTO> getHotel(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(hotelService.readHotel(id));
        } catch (NotFoundException nfe) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Create a Hotel using the HotelDTO model
     * @param hotelDTO {HotelDTO} - the data to create a Hotel
     * @return {String/HotelDTO} - the error message or the new HotelDTO created.
     */
    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createHotel(@RequestBody HotelDTO hotelDTO) {
        try {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(hotelService.createHotel(hotelDTO));
        } catch (BadRequestException bre) {
            log.warn(bre.getMessage(), bre);
            return ResponseEntity.badRequest().body(bre.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Soft Delete a Hotel
     * @param id {Long} - the id of the Hotel
     * @return
     */
    @RequestMapping(value = "{id}", method = {RequestMethod.DELETE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HotelDTO> deleteHotel(@PathVariable Long id) {
        try {
            hotelService.deleteHotel(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException nfe) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Update a Hotel by `id`
     * @param id {Long} - the id of the Hotel
     * @param hotelDTO {HotelDTO} - the new HotelDTO data.
     * @return {String/HotelDTO} - the error message or the updated HotelDTO.
     */
    @RequestMapping(value = "{id}",method = {RequestMethod.PATCH},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> updateHotel(@PathVariable Long id,
                                              @RequestBody HotelDTO hotelDTO) {
        try {
            hotelDTO.setId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(hotelService.updateHotel(hotelDTO));
        } catch (BadRequestException bre) {
            log.warn(bre.getMessage(), bre);
            return ResponseEntity.badRequest().body(bre.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
