package com.matchskills.jobapplication.service.entitys;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "jobapplication")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JobapplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobpostingId;
    private Long candidateId;
    private List<String> hardskills;

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Integer> softskills;

    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    private LocalDate createAt;


}
