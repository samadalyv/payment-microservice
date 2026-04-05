package org.samad.mspayment.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.samad.mspayment.client.CountryClient;
import org.samad.mspayment.dto.request.PaymentCriteria;
import org.samad.mspayment.dto.request.PaymentRequest;
import org.samad.mspayment.dto.response.PageablePaymentResponse;
import org.samad.mspayment.dto.response.PaymentResponse;
import org.samad.mspayment.entity.Payment;
import org.samad.mspayment.exception.NotFoundException;
import org.samad.mspayment.mapper.PaymentMapper;
import org.samad.mspayment.repository.PaymentRepository;
import org.samad.mspayment.service.PaymentService;
import org.samad.mspayment.service.specification.PaymentSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.samad.mspayment.constant.ExceptionConstants.COUNTRY_NOT_FOUND_CODE;
import static org.samad.mspayment.constant.ExceptionConstants.COUNTRY_NOT_FOUND_MESSAGE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CountryClient client;
    private final PaymentMapper mapper;

    @Override
    public void savePayment(PaymentRequest request) {
        log.info("Saving payment...");
        client.getAllAvailableCountries(request.getCurrency())
                .stream()
                .filter(country -> country.getRemainingLimit().compareTo(request.getAmount()) >= 0)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, request.getAmount(),
                        request.getCurrency()),COUNTRY_NOT_FOUND_CODE));
        paymentRepository.save(mapper.mapRequestToEntity(request));
        log.info("Payment saved successfully.");
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        log.info("Getting payment by id: {}", id);
        Payment payment = fetchPaymentIfExists(id);
        log.info("Payment found with id: {}", id);
        return mapper.mapEntityToResponse(payment);
    }

    @Override
    public PageablePaymentResponse getAllPayments(int page, int count, PaymentCriteria criteria) {
       log.info("Get all payments started.");
       var pageable =  PageRequest.of(page,count, Sort.by(DESC, "id"));
       var pageablePayments = paymentRepository.findAll(new PaymentSpecification(criteria), pageable);
       var payments  = pageablePayments.getContent()
               .stream()
               .map(mapper::mapEntityToResponse).toList();
       log.info("All payments found successfully.");
       return PageablePaymentResponse.builder()
               .payments(payments)
               .hasNextPage(pageablePayments.hasNext())
               .totalElements(pageablePayments.getTotalElements())
               .totalPages(pageablePayments.getTotalPages())
               .build();
    }

    @Override
    public void deletePaymentById(Long id) {
        paymentRepository.deleteById(id);
        log.info("Deleted payment by id: {}", id);
    }

    @Transactional
    @Override
    public void updatePayment(Long id, PaymentRequest request) {
        Payment payment = fetchPaymentIfExists(id);
        mapper.updateFromRequest(payment, request);
        log.info("Updated payment with id: {}", id);
    }


    private Payment fetchPaymentIfExists(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}
