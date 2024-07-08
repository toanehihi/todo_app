package com.example.todo_app.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Priority {
    In_day("In a day"),
    In_three_days("In three days"),
    In_week("In week"),
    In_two_week("In two week"),
    In_month("In month");
    private final String priority;
}
