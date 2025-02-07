package com.todo.service

import com.todo.ToDo
import com.todo.ToDoRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class TodosService(val todoTasks: MutableList<ToDo>) {

    fun getTodos(): MutableList<ToDo> = todoTasks

    fun getTodo(id: String): ToDo? {
        val todo = todoTasks.find { it.id == id }
        return todo ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")
    }

    fun createTodo(toDoRequest: ToDoRequest): ToDo {
        val newTodo = ToDo(
            id = UUID.randomUUID().toString(),
            title = toDoRequest.title,
            description = toDoRequest.description
        )
        todoTasks.add(newTodo)
        return newTodo
    }

    fun update(todo: ToDo, toDoRequest: ToDoRequest): ToDo {
        val updatedTodo = todo.copy(
            title = toDoRequest.title,
            description = toDoRequest.description
        )
        val index = todoTasks.indexOf(todo)
        if (index != -1) {
            todoTasks[index] = updatedTodo
        }
        return updatedTodo
    }

    fun delete(todo: ToDo) {
        todoTasks.remove(todo)
    }
}