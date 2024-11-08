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
	@GetMapping("/todos")
	fun todos(): MutableList<ToDo> = todosService.getTodos()

	@GetMapping("/todo/{id}")
	fun todo(@PathVariable id: String): ToDo? = todosService.getTodo(id)

	@PostMapping("/todos")
	fun createTodo(@RequestBody toDoRequest: ToDoRequest): ToDo = todosService.createTodo(toDoRequest)

	@PutMapping("/todo/{id}")
	fun updateTodo(@PathVariable id: String, @RequestBody toDoRequest: ToDoRequest): ToDo? {
		val todo = todosService.getTodo(id)
		val updatedTodo = todo?.copy(
			title = toDoRequest.title,
			description = toDoRequest.description
		)
		todosService.update(id, toDoRequest)
		return updatedTodo
	}

	@DeleteMapping("/todo/{id}")
	fun delete(@PathVariable id: String): ResponseEntity<Unit> {
		todosService.delete(id)
		return ResponseEntity.noContent().build()
	}
}

	fun main(args: Array<String>) {
		runApplication<DemoApplication>(*args)
	}
