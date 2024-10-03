package com.example.demo.todos.service

import com.example.demo.todos.ToDo
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class TodosServiceTest {

    @Test
    fun getTodos() {
        TodosService(
            todoTasks = mutableListOf(ToDo(0, "task", ""))
        )
            .todoTasks shouldBeEqualTo mutableListOf(
            ToDo(
                0,
                "task",
                ""
            )
        )
    }

    @Test
    fun getTodo() {
        TodosService(
            todoTasks = mutableListOf(ToDo(0, "task", ""))
        )
            .getTodo(0) shouldBeEqualTo ToDo(
            0,
            "task",
            ""
        )
    }
}