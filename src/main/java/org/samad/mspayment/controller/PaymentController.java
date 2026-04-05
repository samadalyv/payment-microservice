package org.samad.mspayment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.samad.mspayment.dto.request.PaymentCriteria;
import org.samad.mspayment.dto.request.PaymentRequest;
import org.samad.mspayment.dto.response.PageablePaymentResponse;
import org.samad.mspayment.dto.response.PaymentResponse;
import org.samad.mspayment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(
        @Valid @RequestBody PaymentRequest paymentRequest) {
    paymentService.savePayment(paymentRequest);
    }

    @GetMapping
    public PageablePaymentResponse getAllPayments(
                    @RequestParam int page,
                    @RequestParam int count,
                    @ModelAttribute PaymentCriteria criteria
    ) {
        System.out.println(paymentService.getAllPayments(page, count, criteria));
        return paymentService.getAllPayments(page, count, criteria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PutMapping("/api/payments/update/{id}")
    public void updatePayment(@PathVariable Long id,
                                                         @RequestBody PaymentRequest paymentRequest) {
       paymentService.updatePayment(id, paymentRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePaymentById(id);
    }
}
