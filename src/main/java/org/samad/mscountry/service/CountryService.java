package org.samad.mscountry.service;

import org.samad.mscountry.dto.CountryResponse;

import java.util.List;

public interface CountryService {

    List<CountryResponse> getAvailableCountries(String currency);
}
