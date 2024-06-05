package com.choice.eduardo.soap;

import com.choice.eduardo.soap.model.Amenity;
import com.choice.eduardo.soap.repository.HotelsRepository;
import com.choice.eduardo.soap.service.HotelService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

@WebServiceServerTest
@ComponentScan("com.choice.eduardo.soap")
public class HotelEndpointIT {

    @Autowired
    private MockWebServiceClient client;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private HotelsRepository hotelsRepository;

    @BeforeEach
    void beforeEach() {
        reset(hotelService);
        reset(hotelsRepository);
    }

    @Test
    public void testCreateHotelRequest() throws IOException {
        when(hotelsRepository.findByName("Test WS hotel name")).thenReturn(null);

        StringSource request = new StringSource("" +
                "<createHotelRequest xmlns=\"http://www.choice.com/eduardo/spring/soap/gen\">\n" +
                "    <Hotel>\n" +
                "        <name>Test WS hotel name</name>\n" +
                "        <address>Be avenue #33</address>\n" +
                "        <ratting>4</ratting>\n" +
                "        <amenities>1,2,3</amenities>\n" +
                "    </Hotel>\n" +
                "</createHotelRequest>");
        StringSource expectedResponse = new StringSource("" +
                "<ns2:commonResponse xmlns:ns2=\"http://www.choice.com/eduardo/spring/soap/gen\">\n" +
                "    <ns2:success>true</ns2:success>\n" +
                "</ns2:commonResponse>");

        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(validPayload(new ClassPathResource("hotels.xsd")))
                .andExpect(payload(expectedResponse))
                .andExpect(xpath("/ns2:commonResponse/ns2:success", createMapping())
                        .evaluatesTo("true"));
    }

    @Test
    public void testReadAmenitiesCatalog() throws IOException {

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(new Amenity(1L, "pool", Boolean.TRUE));
        amenities.add(new Amenity(2L, "wifi", Boolean.TRUE));
        when(hotelService.readAmenities()).thenReturn(amenities);

        StringSource request = new StringSource(
                "<getAmenitiesRequest xmlns=\"http://www.choice.com/eduardo/spring/soap/gen\">\n" +
                        "</getAmenitiesRequest>"
        );

        StringSource expectedResponse = new StringSource(
                "<ns2:getAmenitiesResponse xmlns:ns2=\"http://www.choice.com/eduardo/spring/soap/gen\">\n" +
                        "        <ns2:Amenity>\n" +
                        "            <ns2:id>1</ns2:id>\n" +
                        "            <ns2:name>pool</ns2:name>\n" +
                        "        </ns2:Amenity>\n" +
                        "        <ns2:Amenity>\n" +
                        "            <ns2:id>2</ns2:id>\n" +
                        "            <ns2:name>wifi</ns2:name>\n" +
                        "        </ns2:Amenity>\n" +
                        " </ns2:getAmenitiesResponse>"
        );

        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(validPayload(new ClassPathResource("hotels.xsd")))
                .andExpect(payload(expectedResponse))
                .andExpect(xpath("/ns2:getAmenitiesResponse/ns2:Amenity[1]/ns2:name", createMapping())
                        .evaluatesTo("pool"));
    }

    private static Map<String, String> createMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("ns2", HotelService.NAMESPACE);
        return mapping;
    }
}
