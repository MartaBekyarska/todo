package com.example.demo.todos.service

import com.example.demo.todos.ToDo
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodosService(val todoTasks: MutableList<ToDo>) {

    fun getTodos(): MutableList<ToDo> = todoTasks

    fun getTodo(id: String): ToDo? {
        val todo = todoTasks.find { it.id == id }
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

    fun update(id: String, toDoRequest: ToDoRequest): ToDo? {
        val todo = todoTasks.find { it.id == id }
            todo.let {
            it?.title = toDoRequest.title
            it?.description = toDoRequest.description
        }
        return todo
    }
}