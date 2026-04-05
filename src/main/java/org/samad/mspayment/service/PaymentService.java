package org.samad.mspayment.service;

import org.samad.mspayment.dto.request.PaymentCriteria;
import org.samad.mspayment.dto.request.PaymentRequest;
import org.samad.mspayment.dto.response.PageablePaymentResponse;
import org.samad.mspayment.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    void savePayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long id);
    PageablePaymentResponse getAllPayments(int page, int count, PaymentCriteria criteria);
    void deletePaymentById(Long id);
    void updatePayment(Long id,PaymentRequest request);

}
