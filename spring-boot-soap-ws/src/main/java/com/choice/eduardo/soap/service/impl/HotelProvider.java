package com.choice.eduardo.soap.service.impl;

import com.choice.eduardo.soap.model.Amenity;
import com.choice.eduardo.soap.model.Hotel;
import com.choice.eduardo.soap.model.PageFields;
import com.choice.eduardo.soap.repository.AmenitiesRepository;
import com.choice.eduardo.soap.model.HotelFilter;
import com.choice.eduardo.soap.repository.HotelsRepository;
import com.choice.eduardo.soap.service.HotelService;
import com.choice.eduardo.soap.transform.HotelDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class HotelProvider implements HotelService {

    private HotelsRepository hotelsRepository;
    private AmenitiesRepository amenitiesRepository;

    @Override
    public Pair<List<Hotel>, Map<String, Integer>> readBy(Map<String, Object> fields, Integer page) {
        //filter by name
        log.info("Reading hotels by page {} and filter fields:{}", page, fields);
        Page<Hotel> data;
        if (fields.containsKey(HotelFilter.NAME.getKey())) {
            data = hotelsRepository.findByNameContainsAndActive(
                    fields.get(HotelFilter.NAME.getKey()).toString(),
                    PageRequest.of(page, HotelService.PAGE_SIZE), Boolean.TRUE);
        } else {
            log.debug("no filter by `name` found");
            //if there is no filter by name or other field, we return the page 1 with the size of the page config
            page = 0;
            data = hotelsRepository.findAll(PageRequest.of(page, HotelService.PAGE_SIZE));
        }
        return Pair.of(data.stream().collect(Collectors.toList()), this.buildMapMetadata(data, page));
    }

    @Override
    public List<Amenity> readAmenities() {
        List<Amenity> amenities = new ArrayList<>();
        amenitiesRepository.findAllByActiveIsTrue().forEach(amenity -> amenities.add(amenity));
        return amenities;
    }

    @Override
    public void deleteHotel(Long id) {
        hotelsRepository.findById(id).ifPresent(hotel -> {
            if (hotel.getActive()) {
                hotel.setActive(Boolean.FALSE);
                hotelsRepository.save(hotel);
                log.info("Soft Deleted hotel id:{}", hotel.getId());
            } else {
                log.warn("Hotel with id {} is not active", id);
            }
        });
    }

    @Transactional
    @Override
    public void createHotel(com.choice.eduardo.spring.soap.gen.Hotel hotel) throws IllegalArgumentException {
        log.debug("Creating a hotel with name:{}", hotel.getName());
        Hotel exists = hotelsRepository.findByName(hotel.getName());
        if (exists != null) {
            log.warn("Hotel with name `{}` already exists, skipping creation", hotel.getName());
            throw new IllegalArgumentException("Hotel with name `" + hotel.getName() + "` already exists");
        } else {
            List<Long> amenitiesIds = this.parseAmenitiesIds(hotel.getAmenities());
            List<Amenity> amenities = amenitiesRepository.findAllById(amenitiesIds);
            Hotel newHotel = HotelDataMapper.hotelXmlToHotel(hotel);
            newHotel.setAmenities(amenities);
            newHotel.setActive(Boolean.TRUE);
            newHotel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
            newHotel.setLastUpdated(LocalDateTime.now(ZoneId.of("UTC")));
            hotelsRepository.save(newHotel);
        }
    }

    @Transactional
    @Override
    public void updateHotel(com.choice.eduardo.spring.soap.gen.Hotel hotel) throws IllegalArgumentException {
        log.debug("Updating hotel with id:{}", hotel.getId());
        Hotel fetched = hotelsRepository.findById(hotel.getId()).orElse(null);
        if (fetched == null) {
            log.warn("Hotel with name:{} and id:{} not found ", hotel.getName(), hotel.getId());
            throw new IllegalArgumentException("Hotel with name:" + hotel.getName() + " and id:" + hotel.getId() + " not found");
        } else {
            String currentAmenities = HotelDataMapper.buildAmenitiesJoiner(fetched.getAmenities());
            if(!currentAmenities.equals(hotel.getAmenities())) {
                log.debug("Amenities changed from {} to {} in id:{}", currentAmenities, hotel.getAmenities(), hotel.getId());
                List<Long> amenitiesIds = this.parseAmenitiesIds(hotel.getAmenities());
                List<Amenity> amenities = amenitiesRepository.findAllById(amenitiesIds);
                fetched.setAmenities(amenities);
            }
            fetched.setName(hotel.getName());
            fetched.setAddress(hotel.getAddress());
            fetched.setRatting(hotel.getRatting());
            fetched.setLastUpdated(LocalDateTime.now(ZoneId.of("UTC")));
            hotelsRepository.save(fetched);
        }
    }

    private List<Long> parseAmenitiesIds(String amenitiesIds) {
        return Arrays.stream(amenitiesIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    private Map<String, Integer> buildMapMetadata(Page<Hotel> data, Integer currentPage) {
        log.debug("records:{} pages:{} currentPage:{}", data.getTotalElements(), data.getTotalPages(), currentPage);
        Map<String, Integer> pagesMetadata = new HashMap<>();
        pagesMetadata.put(PageFields.PAGES.getKey(), data.getTotalPages());
        pagesMetadata.put(PageFields.ELEMENTS.getKey(), data.getNumberOfElements());
        pagesMetadata.put(PageFields.CURRENT_PAGE.getKey(), currentPage + 1);
        return pagesMetadata;
    }

}
