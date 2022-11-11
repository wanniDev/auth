package me.spring.auth.account.domain

import java.util.*

class Id<R, V>(private val reference: Class<R>?, private val value: V) {

    companion object {
        fun <R : Any, V> of(reference: Class<R>?, value: V): Id<R, V>? {
            return Id(reference, value)
        }
    }

    fun value(): V {
        return value
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val id: Id<*, *> = o as Id<*, *>
        return reference == id.reference && value == id.value
    }

    override fun hashCode(): Int {
        return Objects.hash(reference, value)
    }

}