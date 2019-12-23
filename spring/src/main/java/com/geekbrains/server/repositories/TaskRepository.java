package com.geekbrains.server.repositories;

import com.geekbrains.gwt.common.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task>, PagingAndSortingRepository<Task, Long> {
}