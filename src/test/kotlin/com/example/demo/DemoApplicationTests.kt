package com.example.demo

import com.example.demo.todos.ToDo
import com.example.demo.todos.service.ToDoRequest
import com.example.demo.todos.service.TodosService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@Import(TestcontainersConfiguration::class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	private val todosService = mockk<TodosService>()
	private val id = "1ab4c63b2d"
	private val todo = ToDo(id, "First", "This is the first todo")
	private val updatedTodo = ToDo(id, "First", "This is an updated task")
	val newTodo = ToDo(id, "Task", "This is a new task")

	@Test
	fun `should return todo list`() {
		every { todosService.getTodos() } returns mutableListOf(todo)

		mockMvc.perform(get("/todos"))
			.andExpect(status().isOk)
			.andExpect { jsonPath("$[0].id").value(id) }
			.andExpect { jsonPath("$[0].title").value("First") }
			.andExpect { jsonPath("$[0].description").value("This is the first todo") }
			.andReturn()
	}

	@Test
	fun `should return a single todo`() {
		every { todosService.getTodo(id) } returns todo

		mockMvc.perform(get("/todo/$id"))
			.andExpect(status().isOk)
			.andExpect { jsonPath("id").value(id) }
			.andExpect { jsonPath("title").value("First") }
			.andExpect { jsonPath("description").value("This is the first todo") }
			.andReturn()
	}

	@Test
	fun `should create a single todo with generated id`() {
		every { todosService.createTodo(ToDoRequest("First", "This is a new task")) } returns newTodo

		mockMvc.perform(
			post("/todos")
			.content("""{"title": "First", "description": "This is a new task"}""")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)
			.andExpect { jsonPath("id").value(id) }
			.andExpect { jsonPath("title").value("First") }
			.andExpect { jsonPath("description").value("This is a new task") }
			.andReturn()
	}

	@Test
	fun `should update existing todo`() {
		every { todosService.getTodo(id) } returns ToDo("1ab4c63b2d", "First", "This is a task")
		every { todosService.update(id, ToDoRequest("First", "This is an updated task")) } returns updatedTodo

		mockMvc.perform(
			put("/todo/$id")
			.content("""{"title": "First", "description": "This is an updated task"}""")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)
			.andExpect{ jsonPath("id").value(id) }
			.andExpect { jsonPath("title").value("First") }
			.andExpect{ jsonPath("description").value("This is an updated task") }
			.andReturn()
	}

	@Test
	fun `should delete existing todo`() {
		every { todosService.getTodo(id) } returns ToDo("1ab4c63b2d", "First", "This is a task")
		every { todosService.delete(id) } returns Unit

		mockMvc.perform(delete("/todo/$id")).andExpect(status().isNoContent)
	}
}
