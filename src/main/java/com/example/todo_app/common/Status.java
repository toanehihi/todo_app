package com.example.todo_app.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    In_Progress("In_Progress"),
    Completed("Completed"),
    Overdue("Overdue"),
    Cancelled("Cancelled");
    private final String status;
}
