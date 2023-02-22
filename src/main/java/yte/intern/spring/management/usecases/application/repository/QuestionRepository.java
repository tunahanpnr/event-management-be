package yte.intern.spring.management.usecases.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.spring.management.usecases.application.entity.Event;
import yte.intern.spring.management.usecases.application.entity.Question;
import yte.intern.spring.management.usecases.application.entity.Users;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
