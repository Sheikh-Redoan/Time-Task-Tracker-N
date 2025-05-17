package com.example.timetracker.repository;

import com.example.timetracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStartTimeBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT t FROM Task t WHERE DATE(t.startTime) = :date")
    List<Task> findByDate(LocalDate date);

    @Query("SELECT t FROM Task t WHERE t.startTime >= :startOfWeek AND t.startTime <= :endOfWeek")
    List<Task> findByWeek(LocalDate startOfWeek, LocalDate endOfWeek);
}