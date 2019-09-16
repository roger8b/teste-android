package br.com.easynvest.calc.ext

import org.mockito.*
import org.mockito.stubbing.OngoingStubbing

inline fun <reified T> mock() = Mockito.mock(T::class.java) as T

inline fun <reified T> mock(body: T.() -> Unit) = (Mockito.mock(T::class.java) as T).apply {
    body()
}

@CheckReturnValue
fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)

inline fun <reified T> anyOfType() = Mockito.any(T::class.java) ?: uninitialized()

@Suppress("UNCHECKED_CAST")
fun <T> uninitialized(): T = null as T
