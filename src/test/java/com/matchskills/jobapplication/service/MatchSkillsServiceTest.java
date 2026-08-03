package com.matchskills.jobapplication.service;

import com.matchskills.jobapplication.service.domains.ApplicationDomain;
import com.matchskills.jobapplication.service.dtos.MatchSkillsResponse;
import com.matchskills.jobapplication.service.services.MatchSkillsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchSkillsServiceTest {

    private final MatchSkillsService service = new MatchSkillsService();

    @ParameterizedTest
    @CsvSource({
            "5,5,100",
            "5,4,80",
            "5,3,60",
            "5,2,40",
            "5,1,20",
            "4,5,120",
            "3,5,140",
            "2,5,160",
            "1,5,180"
    })
    void shouldCalculateSoftAndHardSkillsMatch(
            int targetLevel,
            int candidateLevel,
            int expectedSoftSkillsMatch) {

        // Arrange
        Map<String, Integer> targetSoftSkills = Map.of(
                "Communication", targetLevel
        );

        List<String> targetHardSkills = List.of(
                "Java",
                "Spring Boot",
                "Docker",
                "PostgreSQL"
        );

        ApplicationDomain application = new ApplicationDomain(
                "John Doe",
                Map.of("Communication", candidateLevel),
                List.of(
                        "Java",
                        "Spring Boot",
                        "Docker"
                )
        );

        // Act
        MatchSkillsResponse response = service
                .match(
                        targetSoftSkills,
                        targetHardSkills,
                        List.of(application)
                )
                .getFirst();

        // Assert
        assertEquals("John Doe", response.getCandidateName());
        assertEquals(expectedSoftSkillsMatch, response.getMatchSoftSkillsPercent());
        assertEquals(75, response.getMatchHardSkillsPercent());
    }

    @Test
    void shouldCalculateHardSkillsMatch() {

        Map<String, Integer> targetSoftSkills = Map.of();

        List<String> targetHardSkills = List.of(
                "Java",
                "Spring Boot",
                "Docker",
                "PostgreSQL"
        );

        ApplicationDomain application = new ApplicationDomain(
                "John Doe",
                Map.of(),
                List.of(
                        "Java",
                        "Spring Boot",
                        "Docker"
                )
        );

        MatchSkillsResponse response = service
                .match(
                        targetSoftSkills,
                        targetHardSkills,
                        List.of(application)
                )
                .getFirst();

        assertEquals(75, response.getMatchHardSkillsPercent());
    }
}