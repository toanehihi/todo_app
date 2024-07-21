package com.example.todo_app.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    OVERDUE("Overdue");
    private final String status;
}
