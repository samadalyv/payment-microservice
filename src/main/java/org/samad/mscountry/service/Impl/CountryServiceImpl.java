package org.samad.mscountry.service.Impl;

import org.samad.mscountry.dto.CountryResponse;
import org.samad.mscountry.service.CountryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Override
    public List<CountryResponse> getAvailableCountries(String currency) {
        if (currency.equals("USD")) {
           return List.of( new CountryResponse("USA", BigDecimal.valueOf(5000)),
                  new CountryResponse("GER", BigDecimal.TEN));
        }
        return new ArrayList<>();
    }
}
