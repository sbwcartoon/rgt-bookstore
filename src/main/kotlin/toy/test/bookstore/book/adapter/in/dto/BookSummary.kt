package toy.test.bookstore.book.adapter.`in`.dto

import toy.test.bookstore.book.unit.domain.model.Book

data class BookSummary(
    val id: String,
    val title: String,
    val author: String,
    val quantity: Int,
) {
    companion object {
        fun from(book: Book): BookSummary {
            requireNotNull(book.id) { "Saved book must have an id" }.toString()
            return BookSummary(
                id = book.id.toString(),
                title = book.title.toString(),
                author = book.author.toString(),
                quantity = book.quantity.value,
            )
        }
    }
}