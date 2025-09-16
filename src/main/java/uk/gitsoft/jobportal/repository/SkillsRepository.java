package uk.gitsoft.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gitsoft.jobportal.entity.Skills;

import java.util.Collection;

public interface SkillsRepository extends JpaRepository<Skills, Integer> {
    void deleteAllByIdInBatch(Iterable<Integer> ids);
    void deleteByIdIn(Collection<Integer> ids);
}
