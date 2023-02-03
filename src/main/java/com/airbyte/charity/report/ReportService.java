package com.airbyte.charity.report;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.ReportDTO;
import com.airbyte.charity.model.Report;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ReportService extends ParentService<Report, ReportRepository, ReportDTO> {

    public ReportService(ReportRepository repository) {
        super(repository);
    }

    @Override
    public Report updateModelFromDto(Report report, ReportDTO dto) {
        report.setDate(dto.getDate() != null ? dto.getDate() : report.getDate());
        report.setDescription(dto.getDescription() != null ? dto.getDescription() : report.getDescription());
        report.setTitle(dto.getTitle() != null ? dto.getTitle() : report.getTitle());
        return report;
    }

    @Override
    public Report convertDTO(ReportDTO dto) {
        Report report = new Report();
        report.setDate(dto.getDate());
        report.setDescription(dto.getDescription());
        report.setTitle(dto.getTitle());

        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            Map<String, String> fileMap = new TreeMap<>();
            dto.getFiles().forEach(fileDTO -> fileMap.put(fileDTO.getName(), fileDTO.getFileId()));
            report.setFiles(fileMap);
        }
        return report;
    }

    @Override
    public List<Report> getWithSearch(ReportDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Report> criteriaBuilderQuery = criteriaBuilder.createQuery(Report.class);

        Root<Report> root = criteriaBuilderQuery.from(Report.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("date"), search.getDate()));
        }
        if (search.getTitle() != null && !search.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + search.getTitle() + "%"));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }
}
