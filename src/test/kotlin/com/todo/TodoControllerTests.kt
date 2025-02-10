package com.todo

import com.todo.service.TodoService
import io.mockk.every
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.server.ResponseStatusException

@Import(TodoControllerTests.ControllerTestConfig::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
class TodoControllerTests {

	@TestConfiguration
	class ControllerTestConfig {
		@Bean
		fun todoService() = mockk<TodoService>()
	}

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Autowired
	private lateinit var todoService: TodoService

	@AfterEach
	fun tearDown() {
		clearAllMocks()
	}

	private val id = "1ab4c63b2d"
	private val todo = ToDo(id, "First", "This is the first todo")
	val newTodo = ToDo(id, "Task", "This is a new task")

	@Test
	fun `should return todo list`() {
		every { todoService.getTodos() } returns mutableListOf(todo)

		mockMvc.perform(get("/v1/todos"))
			.andExpect(status().isOk)
			.andExpect { jsonPath("$[0].id").value(id) }
			.andExpect { jsonPath("$[0].title").value("First") }
			.andExpect { jsonPath("$[0].description").value("This is the first todo") }
			.andReturn()
	}

	@Test
	fun `should return a single todo`() {
		every { todoService.getTodo(id) } returns todo

		mockMvc.perform(get("/v1/todo/${todo.id}"))
			.andExpect(status().isOk)
			.andExpect { jsonPath("id").value(todo.id) }
			.andExpect { jsonPath("title").value("First") }
			.andExpect { jsonPath("description").value("This is the first todo") }
			.andReturn()
	}

	@Test
	fun `should create a single todo with generated id`() {
		every { todoService.createTodo(ToDoRequest("dfd","First", "This is a new task")) } returns newTodo
		every { todoService.getTodos() } returns listOf(newTodo)

		mockMvc.perform(
			post("/v1/todo")
			.content("""{"id": "dfd", "title": "First", "description": "This is a new task"}""")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)
			.andExpect { jsonPath("id").value(id) }
			.andExpect { jsonPath("title").value("First") }
			.andExpect { jsonPath("description").value("This is a new task") }
			.andReturn()
	}

	@Test
	fun `should update existing todo`() {
		val todo = ToDo("sf", "First", "This is a task")
		val updatedTodo = todo.copy(description = "This is an updated task")
		every { todoService.getTodo(todo.id) } returns todo
		every { todoService.update(ToDoRequest("sf","First", "This is an updated task")) } returns updatedTodo

		mockMvc.perform(
			put("/v1/todo")
			.content("""{"id": "sf", "title": "First", "description": "This is an updated task"}""")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)
			.andExpect{ jsonPath("id").value(id) }
			.andExpect { jsonPath("title").value("First") }
			.andExpect{ jsonPath("description").value("This is an updated task") }
			.andReturn()
	}

	@Test
	fun `should delete existing todo`() {
		every { todoService.getTodo(id) } returns todo
		every { todoService.delete(todo) } returns Unit

		mockMvc.perform(delete("/v1/todo/$id")).andExpect(status().isNoContent)
	}

	@Test
	fun `get todo should throw an exception if id is not found`() {
		every { todoService.getTodo(id) } throws ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")

		mockMvc.perform(get("/v1/todo/$id"))
			.andExpect(status().isNotFound)
			.andReturn()
	}

	@Test
	fun `delete todo should throw an exception if id is not found`() {
		every { todoService.getTodo(id) } throws ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")

		mockMvc.perform(delete("/v1/todo/$id"))
			.andExpect(status().isNotFound)
			.andReturn()

		verify(exactly = 0) { todoService.delete(todo) }
	}
}
