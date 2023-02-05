package ir.reversedev.mycalendar.util

import java.util.concurrent.atomic.AtomicBoolean

data class Event<T>(private val value: T) {
    private val isHandled = AtomicBoolean(false)

    fun ifNotHandled(function: (T) -> Unit) {
        if (isHandled.compareAndSet(false, true)) {
            function.invoke(value)
        }
    }
}