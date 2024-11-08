package com.example.demo.todos.service

import com.example.demo.todos.ToDo
import org.amshove.kluent.`should not be`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.util.*

class TodosServiceTest {

    @Test
    fun getTodos() {
        TodosService(
            todoTasks = mutableListOf(ToDo("1ab4c63b2d", "task", ""))
        )
            .todoTasks shouldBeEqualTo mutableListOf(
            ToDo(
                "1ab4c63b2d",
                "task",
                ""
            )
        )
    }

    @Test
    fun getTodo() {
        TodosService(
            todoTasks = mutableListOf(ToDo("1ab4c63b2d", "task", ""))
        )
            .getTodo(0) shouldBeEqualTo ToDo(
            "1ab4c63b2d",
            "task",
            ""
        )
    }

    @Test
    fun createTodo() {
        val newTodo = TodosService(
            todoTasks = mutableListOf()
        )
            .createTodo(ToDoRequest("New Task", "This is a new task"))

        UUID.fromString(newTodo.id) `should not be` null
        newTodo.title shouldBeEqualTo "New Task"
        newTodo.description shouldBeEqualTo "This is a new task"
    }
}