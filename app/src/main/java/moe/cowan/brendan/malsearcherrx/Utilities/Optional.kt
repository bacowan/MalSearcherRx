package moe.cowan.brendan.malsearcherrx.Utilities

class Optional<out T> private constructor(private val value: T?) {

    companion object Factory {
        fun <T> empty(): Optional<T> = Optional(null)
        fun <T> of(value: T): Optional<T> = Optional(value)
    }

    fun ifPresent(consumer: (value: T) -> Unit) {
        if (value != null) {
            consumer(value)
        }
    }

    fun ifPresent(consumer: (value: T) -> Unit, ifNotPresent: () -> Unit) {
        if (value != null) {
            consumer(value)
        }
        else {
            ifNotPresent()
        }
    }

    fun <U> map(mapper: (input: T) -> U): Optional<U> {
        return if (value != null) { Optional.of(mapper(value)) }
        else { Optional.empty() }
    }

    fun <U> flatMap(mapper: (input: T) -> Optional<U>): Optional<U> {
        return if (value != null) { mapper(value) }
        else { Optional.empty() }
    }

}