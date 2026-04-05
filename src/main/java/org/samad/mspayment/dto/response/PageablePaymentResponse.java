package org.samad.mspayment.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.samad.mspayment.entity.Payment;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageablePaymentResponse {

    List<PaymentResponse> payments;
    Long totalElements;
    int totalPages;
    boolean hasNextPage;
}
