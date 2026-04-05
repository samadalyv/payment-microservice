package org.samad.mspayment.service.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.samad.mspayment.dto.request.PaymentCriteria;
import org.samad.mspayment.entity.Payment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PaymentSpecification implements Specification<Payment> {

    private final PaymentCriteria criteria;
    private static final String AMOUNT = "amount";
    private static final String DESCRIPTION = "description";

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria != null){
            if (criteria.getAmountFrom() != null){
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(AMOUNT),
                                criteria.getAmountFrom()));
            }
            if (criteria.getAmountTo() != null){
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get(AMOUNT), criteria.getAmountTo()));
            }
            if(StringUtils.hasText(criteria.getDescription())){
                predicates.add(criteriaBuilder.like(root.get(DESCRIPTION),
                        "%"+criteria.getDescription()+"%"));
            }
        }
        if (predicates.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));


    }
}
