package com.todo

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<TodoController>().with(TestcontainersConfiguration::class).run(*args)
}
