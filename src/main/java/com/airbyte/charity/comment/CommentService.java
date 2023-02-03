package com.airbyte.charity.comment;

import com.airbyte.charity.common.ParentService;
import com.airbyte.charity.dto.CommentDTO;
import com.airbyte.charity.model.Comment;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentService extends ParentService<Comment, CommentRepository, CommentDTO> {

    public CommentService(CommentRepository repository) {
        super(repository);
    }

    @Override
    public Comment updateModelFromDto(Comment comment, CommentDTO dto) {
        comment.setDisLikeCount(dto.getDisLikeCount() != null ? BigDecimal.valueOf(dto.getDisLikeCount()) : comment.getDisLikeCount());
        comment.setLikeCount(dto.getLikeCount() != null ? BigDecimal.valueOf(dto.getLikeCount()) : comment.getLikeCount());
        comment.setText(dto.getText() != null ? dto.getText() : comment.getText());
        comment.setSenderName(dto.getSenderName() != null ? dto.getSenderName() : comment.getSenderName());
        return comment;
    }

    @Override
    public Comment convertDTO(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setDisLikeCount(BigDecimal.valueOf(dto.getDisLikeCount() != null ? dto.getDisLikeCount() : 0));
        comment.setLikeCount(BigDecimal.valueOf(dto.getLikeCount() != null ? dto.getLikeCount() : 0));
        comment.setText(dto.getText());
        comment.setSenderName(dto.getSenderName());
        return comment;
    }

    @Override
    public List<Comment> getWithSearch(CommentDTO search) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaBuilderQuery = criteriaBuilder.createQuery(Comment.class);

        Root<Comment> root = criteriaBuilderQuery.from(Comment.class);
        List<Predicate> predicates = new ArrayList<>();

        if (search.getDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("date"), search.getDate()));
        }
        if (search.getLikeCount() != null) {
            predicates.add(criteriaBuilder.gt(root.get("likeCount"), search.getLikeCount()));
        }
        if (search.getDisLikeCount() != null) {
            predicates.add(criteriaBuilder.gt(root.get("disLikeCount"), search.getLikeCount()));
        }
        if (search.getSenderName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("senderName"), search.getSenderName()));
        }

        criteriaBuilderQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaBuilderQuery).getResultList();
    }

    public List<Comment> getGoodComment(String projectId) { // TODO : FILL ME
        List<Comment> comments = repository.findAll();
        List<Comment> responseList = new ArrayList<>();

        for (Comment comment : comments) {
            if (comment.getDisLikeCount().longValue() == 0) {
                double performance = (double) comment.getLikeCount().longValue();
                comment.setPerformance(performance * 100);
            } else if (comment.getLikeCount().longValue() == 0) {
                double performance = (double) comment.getDisLikeCount().longValue() * -100;
                if (performance == 0) {
                    comment.setPerformance(0.0);
                } else {
                    comment.setPerformance(performance);
                }
            } else {
                double performance = (double) comment.getLikeCount().longValue() / comment.getDisLikeCount().longValue();
                comment.setPerformance(performance * 100);
            }
            responseList.add(comment);
        }

        responseList.sort(Comparator.comparing(Comment::getPerformance).reversed());
        return responseList;
    }
}
