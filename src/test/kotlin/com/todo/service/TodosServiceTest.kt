package com.todo.service

import com.todo.ToDo
import com.todo.ToDoRequest
import org.amshove.kluent.`should not be`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.server.ResponseStatusException
import java.util.*

class TodosServiceTest {

    private val todo = ToDo("1ab4c63b2d", "task", "")

    @Test
    fun getTodos() {
        TodosService(
            todoTasks = mutableListOf(todo)
        )
            .todoTasks shouldBeEqualTo mutableListOf(
            todo
        )
    }

    @Test
    fun getTodo() {
        TodosService(todoTasks = mutableListOf(todo))
            .getTodo("1ab4c63b2d") shouldBeEqualTo todo
    }

    @Test
    fun getTodoNotFound() {
        val todosService = TodosService(mutableListOf())

        val exception = assertThrows<ResponseStatusException> {
            todosService.getTodo("1ab4c63b2d")
        }

        exception.reason shouldBeEqualTo "Todo not found"
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

    @Test
    fun updateTodo() {
        val updatedTodo = TodosService(
            todoTasks = mutableListOf(todo)
        )
            .update(todo, ToDoRequest("Updated Task", "This is an updated task"))

        updatedTodo shouldBeEqualTo ToDo(
            "1ab4c63b2d",
            "Updated Task",
            "This is an updated task"
        )
    }

    @Test
    fun deleteTodo() {
        val todosService = TodosService(
            todoTasks = mutableListOf(todo)
        )
        todosService.delete(todo)
        todosService.todoTasks.size shouldBeEqualTo 0
    }
}