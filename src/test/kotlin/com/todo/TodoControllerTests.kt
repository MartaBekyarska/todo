package com.todo

import com.todo.service.TodoService
import io.mockk.clearAllMocks
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(TodoController::class)
class TodoControllerTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@MockBean
	private lateinit var todoService: TodoService

	@AfterEach
	fun tearDown() {
		clearAllMocks()
	}

	private val id = "1ab4c63b2d"
	private val todo = ToDo(id, "First", "This is the first todo")
	private val newTodo = ToDo(id, "Task", "This is a new task")

	@Test
	fun `should return todo list`() {
		`when`(todoService.getTodos()).thenReturn(listOf(todo))

		mockMvc.perform(get("/v1/todos"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$[0].id").value(id))
			.andExpect(jsonPath("$[0].title").value("First"))
			.andExpect(jsonPath("$[0].description").value("This is the first todo"))
			.andReturn()

		todoService.getTodos() shouldBeEqualTo listOf(todo)
	}

	@Test
	fun `should return a single todo`() {
		`when`(todoService.getTodo(id)).thenReturn(todo)

		mockMvc.perform(get("/v1/todo/${todo.id}"))
			.andExpect(status().isOk)
			.andExpect { jsonPath("id").value(todo.id) }
			.andExpect { jsonPath("title").value("First") }
			.andExpect { jsonPath("description").value("This is the first todo") }
			.andReturn()
	}

	@Test
	fun `should create a single todo with generated id`() {
		`when`(todoService.createTodo(ToDoRequest("dfd","First", "This is a new task"))).thenReturn(newTodo)
		`when`(todoService.getTodos()).thenReturn(listOf(newTodo))

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

		`when`(todoService.getTodo(todo.id)).thenReturn(todo)
		`when`(todoService.update(ToDoRequest("sf","First", "This is an updated task")))
			.thenReturn(updatedTodo)

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
        `when`(todoService.getTodo(id)).thenReturn(todo)
		doNothing().`when`(todoService).delete(todo)

		mockMvc.perform(delete("/v1/todo/$id")).andExpect(status().isNoContent)
	}
}
