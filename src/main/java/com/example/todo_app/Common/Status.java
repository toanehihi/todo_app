package com.example.todo_app.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    In_Complete("In_Complete"),
    In_Progress("In_Progress"),
    Completed("Completed"),
    Overdue("Overdue"),
    Cancelled("Cancelled");
    private final String status;
}
