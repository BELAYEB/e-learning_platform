package com.elearning.platform.modules.courses.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "grades",
        uniqueConstraints = @UniqueConstraint(columnNames = "submission_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_id", nullable = false)
    private Long submissionId;

    @Column(nullable = false)
    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(nullable = false)
    private Long gradedByTutorId;

    @Column(nullable = false)
    private LocalDateTime gradedAt;
}
