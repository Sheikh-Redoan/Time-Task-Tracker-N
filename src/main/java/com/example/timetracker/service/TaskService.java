package com.example.timetracker.service;

import com.example.timetracker.model.Task;
import com.example.timetracker.model.TimeSummary;
import com.example.timetracker.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createOrUpdateTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByDate(LocalDate date) {
        return taskRepository.findByDate(date);
    }

    public List<Task> getTasksByWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
        return taskRepository.findByWeek(startOfWeek, endOfWeek);
    }

    public TimeSummary getDailySummary(LocalDate date) {
        List<Task> tasks = getTasksByDate(date);
        return createTimeSummary(tasks, "Daily Summary for " + date.toString());
    }

    public TimeSummary getWeeklySummary(LocalDate date) {
        List<Task> tasks = getTasksByWeek(date);
        return createTimeSummary(tasks, "Weekly Summary for week of " + date.toString());
    }

    private TimeSummary createTimeSummary(List<Task> tasks, String title) {
        TimeSummary summary = new TimeSummary();
        summary.setTitle(title);
        summary.setTotalTasks(tasks.size());

        long totalMinutes = tasks.stream()
                .mapToLong(Task::getDurationInMinutes)
                .sum();

        summary.setTotalHours(totalMinutes / 60);
        summary.setTotalMinutes(totalMinutes % 60);
        summary.setTasks(tasks);

        return summary;
    }
}