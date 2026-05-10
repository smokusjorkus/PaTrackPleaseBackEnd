package com.example.PaTrackPleaseBackend.Tasks.Dto;

public class DashboardMetricsDto {
    private long completed;
    private long pending;
    private long overdue;
    private long total;

    public DashboardMetricsDto(long completed, long pending, long overdue, long total) {
        this.completed = completed;
        this.pending = pending;
        this.overdue = overdue;
        this.total = total;
    }

    // Getters (Required for Spring to turn this into JSON)
    public long getCompleted() {
        return completed;
    }

    public long getPending() {
        return pending;
    }

    public long getOverdue() {
        return overdue;
    }

    public long getTotal() {
        return total;
    }
}