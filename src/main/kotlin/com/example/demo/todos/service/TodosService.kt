package com.example.demo.todos.service

import com.example.demo.todos.ToDo
import org.springframework.stereotype.Service

@Service
class TodosService(val todoTasks: MutableList<ToDo>) {

    fun getTodos(): MutableList<ToDo> = todoTasks

    fun getTodo(id: Int): ToDo = todoTasks[id]

    fun createTodo(toDoRequest: ToDoRequest) = ToDo(1, "Task", "This is a new task")
}