package org.ignas.datagenerator.repositories;

import org.ignas.datagenerator.TestCase;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {

    List<TestCase> findByProcessedIsFalseOrderByIdAsc(Pageable pageable);
}
