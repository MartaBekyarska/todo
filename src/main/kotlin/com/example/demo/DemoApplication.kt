package com.example.demo

import com.example.demo.todos.ToDo
import com.example.demo.todos.service.ToDoRequest
import com.example.demo.todos.service.TodosService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*

@SpringBootApplication
@RestController
class DemoApplication (val todosService: TodosService) {
	@GetMapping("/todos")
	fun todos(): MutableList<ToDo> = todosService.getTodos()

	@GetMapping("/todo/{id}")
	fun todo(@PathVariable id: Int): ToDo = todosService.getTodo(id)

	@PostMapping("/todos")
	fun createTodo(@RequestBody toDoRequest: ToDoRequest): ToDo = todosService.createTodo(toDoRequest)
}
	fun main(args: Array<String>) {
		runApplication<DemoApplication>(*args)
	}
