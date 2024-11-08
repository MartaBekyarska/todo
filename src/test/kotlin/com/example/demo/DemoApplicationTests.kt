package com.example.demo

import com.example.demo.todos.ToDo
import com.example.demo.todos.service.ToDoRequest
import com.example.demo.todos.service.TodosService
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class DemoApplicationTests {
	private val todosService = mockk<TodosService>()
	private val demoApplication = DemoApplication(todosService)

	@Test
	fun `should return todo list`() {
		every { todosService.getTodos() } returns mutableListOf(ToDo(1, "First", "This is the first todo"))
		demoApplication.todos() shouldBeEqualTo mutableListOf(ToDo(1, "First", "This is the first todo"))
	}

	@Test
	fun `should return a single todo`() {
		every { todosService.getTodo(0) } returns ToDo(1, "First", "This is the first todo")
		demoApplication.todo(0) shouldBeEqualTo ToDo(1, "First", "This is the first todo")
	}

	@Test
	fun `should create a single todo with generated id`() {
		every { todosService.createTodo(ToDoRequest("First", "This is a new task")) } returns ToDo(1, "Task", "This is a new task")
		demoApplication.createTodo(ToDoRequest("First", "This is a new task")) shouldBeEqualTo ToDo(1, "Task", "This is a new task")
	}
}
