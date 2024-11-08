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
	private val id = "1ab4c63b2d"
	private val todo = ToDo(id, "First", "This is the first todo")
	private val updatedTodo = ToDo(id, "First", "This is an updated task")
	val newTodo = ToDo(id, "Task", "This is a new task")

	@Test
	fun `should return todo list`() {
		every { todosService.getTodos() } returns mutableListOf(todo)

		demoApplication.todos() shouldBeEqualTo mutableListOf(todo)
	}

	@Test
	fun `should return a single todo`() {
		every { todosService.getTodo(id) } returns todo

		demoApplication.todo(id) shouldBeEqualTo todo
	}

	@Test
	fun `should create a single todo with generated id`() {
		every { todosService.createTodo(ToDoRequest("First", "This is a new task")) } returns newTodo

		demoApplication.createTodo(ToDoRequest("First", "This is a new task")) shouldBeEqualTo newTodo
	}

	@Test
	fun `should update existing todo`() {
		every { todosService.getTodo(id) } returns ToDo("1ab4c63b2d", "First", "This is a task")
		every { todosService.update(id, ToDoRequest("First", "This is an updated task")) } returns updatedTodo

		demoApplication.updateTodo(id, ToDoRequest("First", "This is an updated task")) shouldBeEqualTo updatedTodo
	}
}
