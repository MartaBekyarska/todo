package com.example.demo

import com.example.demo.todos.ToDo
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class DemoApplicationTests {

	@Test
	fun `should return todo list`() {
		DemoApplication().todos() shouldBeEqualTo listOf(ToDo(1, "First", "This is the first todo"))
	}

}
