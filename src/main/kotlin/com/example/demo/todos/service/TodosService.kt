package com.example.demo.todos.service

import com.example.demo.todos.ToDo
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TodosService(val todoTasks: MutableList<ToDo>) {

    fun getTodos(): MutableList<ToDo> = todoTasks

    fun getTodo(id: Int): ToDo = todoTasks[id]

    fun createTodo(toDoRequest: ToDoRequest): ToDo {
        val newTodo = ToDo(
            id = UUID.randomUUID().toString(),
            title = toDoRequest.title,
            description = toDoRequest.description
        )
        todoTasks.add(newTodo)
        return newTodo
    }
}