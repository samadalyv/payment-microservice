package org.samad.mscountry.controller;

import lombok.RequiredArgsConstructor;
import org.samad.mscountry.dto.CountryResponse;
import org.samad.mscountry.service.Impl.CountryServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class ConfigController {

        private final CountryServiceImpl countryService;

        @GetMapping
        public List<CountryResponse> getAllAvailableCountries(@RequestParam String currency) {
            return countryService.getAvailableCountries(currency);
        }

}
