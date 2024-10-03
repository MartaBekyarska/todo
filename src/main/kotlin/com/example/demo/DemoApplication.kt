package com.example.demo

import com.example.demo.todos.ToDo
import com.example.demo.todos.service.TodosService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class DemoApplication (val todosService: TodosService) {
	@GetMapping
	fun todos(): MutableList<ToDo> {
		return todosService.getTodos()
	}

	@GetMapping("/todo/{id}")
	fun todo(@PathVariable id: Int): ToDo {
		return todosService.getTodo(id)
	}
}
	fun main(args: Array<String>) {
		runApplication<DemoApplication>(*args)
	}
