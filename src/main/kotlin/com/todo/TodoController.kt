package com.todo

import com.todo.service.TodoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TodoController (val todoService: TodoService) {
	@GetMapping("/v1/todos")
	fun todos(): List<ToDo> = todoService.getTodos()

	@GetMapping("/v1/todo/{id}")
	fun todo(@PathVariable id: String): ToDo? = todoService.getTodo(id)

	@PostMapping("/v1/todo")
	fun createTodo(@RequestBody toDoRequest: ToDoRequest): ToDo =
		todoService.createTodo(toDoRequest)


	@PutMapping("/v1/todo")
	fun updateTodo(@RequestBody toDoRequest: ToDoRequest) {
		val todo = toDoRequest.id?.let { todoService.getTodo(it) }!!
		return todo.id.let { todoService.update(toDoRequest) }
	}

	@DeleteMapping("/v1/todo/{id}")
	fun delete(@PathVariable id: String): ResponseEntity<Unit> {
		val todo = todoService.getTodo(id)
		todo.let { todoService.delete(it) }
		return ResponseEntity.noContent().build()
	}
}
