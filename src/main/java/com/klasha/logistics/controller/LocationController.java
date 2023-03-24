package com.klasha.logistics.controller;

import com.klasha.logistics.dto.input.LocationInputDTO;
import com.klasha.logistics.dto.output.BasicResponseDTO;
import com.klasha.logistics.service.LocationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/location")
@Api(tags = "Location")
public class LocationController extends Controller {

    private final LocationService locationService;


    @PostMapping("/add")
    public BasicResponseDTO addLocation(@RequestBody LocationInputDTO dto) {
        return updateHttpStatus(locationService.addLocation(dto));
    }

    @PutMapping("/{id}/update")
    public BasicResponseDTO updateLocation(@PathVariable("id") String id, @RequestBody LocationInputDTO dto) {
        return updateHttpStatus(locationService.updateLocation(id,dto));
    }

    @DeleteMapping("/{id}/delete")
    public BasicResponseDTO deleteLocation(@PathVariable("id") String id) {
        return updateHttpStatus(locationService.deleteLocation(id));
    }

    @GetMapping("/{id}")
    public BasicResponseDTO fetchLocation(@PathVariable("id") String id) {
        return updateHttpStatus(locationService.fetchLocation(id));
    }

    @GetMapping("")
    public BasicResponseDTO fetchLocations() {
        return updateHttpStatus(locationService.fetchLocations());
    }

}
