package toy.test.bookstore.book.unit.domain.vo

import java.util.*

@JvmInline
value class BookId(val value: UUID) {
    override fun toString(): String = value.toString()

    companion object {
        fun generate(): BookId {
            return BookId(UUID.randomUUID())
        }
    }
}