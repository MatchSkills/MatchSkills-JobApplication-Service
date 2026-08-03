package com.matchskills.jobapplication.service.services;

import com.matchskills.jobapplication.service.domains.ApplicationDomain;
import com.matchskills.jobapplication.service.dtos.MatchSkillsResponse;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MatchSkillsService {

    public List<MatchSkillsResponse> match(
            Map<String, Integer> targetSoftSkills,
            List<String> targetHardSkills,
            List<ApplicationDomain> applications) {

        List<MatchSkillsResponse> responses = new ArrayList<>();

        for (ApplicationDomain application : applications) {

            int softSkillsMatch = calculateSoftSkillsMatch(
                    targetSoftSkills,
                    application.getSoftSkills()
            );

            int hardSkillsMatch = calculateHardSkillsMatch(
                    targetHardSkills,
                    application.getHardSkills()
            );

            responses.add(new MatchSkillsResponse(
                    application.getCandidateName(),
                    softSkillsMatch,
                    hardSkillsMatch
            ));
        }

        return responses;
    }

    private int calculateSoftSkillsMatch(
            Map<String, Integer> targetSoftSkills,
            Map<String, Integer> candidateSoftSkills) {

        int totalSimilarity = 0;

        for (Map.Entry<String, Integer> targetEntry : targetSoftSkills.entrySet()) {

            Integer candidateLevel =
                    candidateSoftSkills.get(targetEntry.getKey());

            if (candidateLevel == null) {
                continue;
            }

            totalSimilarity += calculateSimilarity(
                    targetEntry.getValue(),
                    candidateLevel
            );
        }

        return targetSoftSkills.isEmpty()
                ? 0
                : totalSimilarity / targetSoftSkills.size();
    }

    private int calculateHardSkillsMatch(
            List<String> targetHardSkills,
            List<String> candidateHardSkills) {

        if (targetHardSkills.isEmpty()) {
            return 0;
        }

        Set<String> candidateSkills = new HashSet<>(candidateHardSkills);

        int matchedSkills = 0;

        for (String skill : targetHardSkills) {
            if (candidateSkills.contains(skill)) {
                matchedSkills++;
            }
        }

        return matchedSkills * 100 / targetHardSkills.size();
    }

    private int calculateSimilarity(
            int targetLevel,
            int candidateLevel) {

        if (targetLevel < 1 || targetLevel > 5 ||
                candidateLevel < 1 || candidateLevel > 5) {
            throw new IllegalArgumentException(
                    "Skill levels must be between 1 and 5."
            );
        }

        return 100 + ((candidateLevel - targetLevel) * 20);
    }
}