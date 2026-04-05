package org.samad.mspayment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.samad.mspayment.dto.request.PaymentRequest;
import org.samad.mspayment.dto.response.PaymentResponse;
import org.samad.mspayment.entity.Payment;


@Mapper(componentModel = "spring")
public interface PaymentMapper {

   Payment mapRequestToEntity (PaymentRequest request);
   PaymentResponse mapEntityToResponse (Payment payment);
   void updateFromRequest(@MappingTarget Payment payment, PaymentRequest request);
}
