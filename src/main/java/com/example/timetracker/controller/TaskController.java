package com.example.timetracker.controller;

import com.example.timetracker.model.Task;
import com.example.timetracker.model.TimeSummary;
import com.example.timetracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createOrUpdateTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        if (taskService.getTaskById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        task.setId(id);
        return ResponseEntity.ok(taskService.createOrUpdateTask(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.getTaskById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date/{date}")
    public List<Task> getTasksByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return taskService.getTasksByDate(date);
    }

    @GetMapping("/week/{date}")
    public List<Task> getTasksByWeek(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return taskService.getTasksByWeek(date);
    }

    @GetMapping("/summary/daily/{date}")
    public TimeSummary getDailySummary(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return taskService.getDailySummary(date);
    }

    @GetMapping("/summary/weekly/{date}")
    public TimeSummary getWeeklySummary(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return taskService.getWeeklySummary(date);
    }
}