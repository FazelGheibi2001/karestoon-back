package com.airbyte.charity.payment;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.PaymentHistoryDTO;
import com.airbyte.charity.model.PaymentHistory;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentHistoryService extends ParentService<PaymentHistory, PaymentHistoryRepository, PaymentHistoryDTO> {

    public PaymentHistoryService(PaymentHistoryRepository repository) {
        super(repository);
    }

    @Override
    public PaymentHistory updateModelFromDto(PaymentHistory paymentHistory, PaymentHistoryDTO dto) {
        paymentHistory.setAmount(dto.getAmount() != null ? dto.getAmount() : paymentHistory.getAmount());
        paymentHistory.setProjectId(dto.getProjectId() != null ? dto.getProjectId() : paymentHistory.getProjectId());
        paymentHistory.setProjectName(dto.getProjectName() != null ? dto.getProjectName() : paymentHistory.getProjectName());
        return paymentHistory;
    }

    @Override
    public PaymentHistory convertDTO(PaymentHistoryDTO dto) {
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setAmount(dto.getAmount());
        paymentHistory.setProjectId(dto.getProjectId());
        paymentHistory.setProjectName(dto.getProjectName());
        paymentHistory.setUsername(dto.getUsername());
        return paymentHistory;
    }

    @Override
    public List<PaymentHistory> getWithSearch(PaymentHistoryDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PaymentHistory> criteriaBuilderQuery = criteriaBuilder.createQuery(PaymentHistory.class);

        Root<PaymentHistory> root = criteriaBuilderQuery.from(PaymentHistory.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("date"), search.getDate()));
        }
        if (search.getProjectName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("projectName"), search.getProjectName()));
        }
        if (search.getUsername() != null) {
            predicates.add(criteriaBuilder.equal(root.get("username"), search.getUsername()));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }
}
