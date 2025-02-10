package com.todo.service

import com.todo.ToDo
import com.todo.ToDoRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodoService {
    private val todoTasks: MutableList<ToDo> = mutableListOf()

    fun getTodos(): List<ToDo> = todoTasks

    fun getTodo(id: String): ToDo {
        val todo = todoTasks.find { it.id == id }!!
        return todo
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

    fun update(toDoRequest: ToDoRequest): ToDo {
        val todo = todoTasks.find { it.id == toDoRequest.id }!!
        val updatedTodo = toDoRequest.id?.let {
            todo.copy(
                id = it,
                title = toDoRequest.title,
                description = toDoRequest.description
            )
        }!!
        todoTasks[todoTasks.indexOf(todo)] = updatedTodo
        return updatedTodo
    }

    fun delete(todo: ToDo) {
        todoTasks.remove(todo)
    }
}