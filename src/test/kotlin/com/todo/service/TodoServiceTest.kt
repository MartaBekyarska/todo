package com.todo.service

import com.todo.ToDo
import com.todo.ToDoRequest
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class TodoServiceTest {

    private val todo = ToDo("1ab4c63b2d", "task", "")
    private val todoService = TodoService()

    @Test
    fun getTodos() {
        val createdTodo = todoService.createTodo(ToDoRequest("1ab4c63b2d", "task", "New task"))

        todoService.getTodos() shouldBeEqualTo listOf(createdTodo)
    }

    @Test
    fun getTodo() {
        val id = "1ab4c63b2d"
        val createdTodo = todoService.createTodo(ToDoRequest(id, "task", "New task"))
        val todo = todoService.getTodos().first()
        todoService.getTodo(todo.id) shouldBeEqualTo createdTodo
    }

    @Test
    fun createTodo() {
        todoService.createTodo(ToDoRequest("sdsdg", "New Task", "This is a new task"))
        val newTodo = todoService.getTodos().first()

        newTodo.title shouldBeEqualTo "New Task"
        newTodo.description shouldBeEqualTo "This is a new task"
    }

    @Test
    fun updateTodo() {
        todoService.createTodo(ToDoRequest("1ab4c63b2d", "task", "New task"))
        val todo = todoService.getTodos().first()
        val updatedTodo = todoService
            .update(ToDoRequest(todo.id,"Updated Task", "This is an updated task"))

        updatedTodo shouldBeEqualTo ToDo(
            updatedTodo.id,
            "Updated Task",
            "This is an updated task"
        )
    }

    @Test
    fun deleteTodo() {
       todoService.createTodo(ToDoRequest("sdasd", todo.title, todo.description))
        val todo = todoService.getTodos().first()

        todoService.delete(todo)

        todoService.getTodos().shouldBeEmpty()
    }
}