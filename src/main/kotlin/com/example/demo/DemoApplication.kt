package com.example.demo

import com.example.demo.todos.ToDo
import com.example.demo.todos.service.ToDoRequest
import com.example.demo.todos.service.TodosService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@SpringBootApplication
@RestController
class DemoApplication (val todosService: TodosService) {
	@GetMapping("/v1/todos")
	fun todos(): MutableList<ToDo> = todosService.getTodos()

	@GetMapping("/v1/todo/{id}")
	fun todo(@PathVariable id: String): ToDo? = todosService.getTodo(id)

	@PostMapping("/v1/todo")
	fun createTodo(@RequestBody toDoRequest: ToDoRequest): ToDo = todosService.createTodo(toDoRequest)

	@PutMapping("/v1/todo/{id}")
	fun updateTodo(@PathVariable id: String, @RequestBody toDoRequest: ToDoRequest): ToDo? {
		val todo = todosService.getTodo(id)
		return todo?.id?.let { todosService.update(todo, toDoRequest) }
	}

	@DeleteMapping("/v1/todo/{id}")
	fun delete(@PathVariable id: String): ResponseEntity<Unit> {
		val todo = todosService.getTodo(id)
		todo?.let { todosService.delete(it) }
		return ResponseEntity.noContent().build()
	}
}

	fun main(args: Array<String>) {
		runApplication<DemoApplication>(*args)
	}
