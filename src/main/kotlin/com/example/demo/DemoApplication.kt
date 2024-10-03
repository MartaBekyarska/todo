package com.example.demo

import com.example.demo.todos.ToDo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class DemoApplication {
	@GetMapping
	fun todos(): List<ToDo> {
		return listOf(ToDo(1, "First", "This is the first todo"))
	}
}
	fun main(args: Array<String>) {
		runApplication<DemoApplication>(*args)
	}
