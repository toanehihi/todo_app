package com.example.todo_app.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Priority {
    IN_DAY("In a day"),
    IN_THREE_DAYS("In three days"),
    IN_WEEK("In week"),
    IN_TWO_WEEK("In two week"),
    IN_MONTH("In month");

    private String priority;
}