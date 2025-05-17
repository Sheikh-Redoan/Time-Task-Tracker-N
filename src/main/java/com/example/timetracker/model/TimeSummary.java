package com.example.timetracker.model;

import lombok.Data;
import java.util.List;

@Data
public class TimeSummary {
    private String title;
    private int totalTasks;
    private long totalHours;
    private long totalMinutes;
    private List<Task> tasks;
}