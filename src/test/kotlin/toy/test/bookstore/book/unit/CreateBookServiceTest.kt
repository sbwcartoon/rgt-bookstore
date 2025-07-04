package toy.test.bookstore.book.unit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import toy.test.bookstore.book.application.exception.DuplicateBookException
import toy.test.bookstore.book.application.port.`in`.command.CreateBookCommand
import toy.test.bookstore.book.application.port.out.BookRepository
import toy.test.bookstore.book.application.service.CreateBookService
import toy.test.bookstore.book.unit.testfixture.BookTestFixture

class CreateBookServiceTest : BehaviorSpec({
    val repository = mockk<BookRepository>()
    val service = CreateBookService(repository)

    Given("동일한 제목 및 저자 이름으로 저장된 책이 존재할 때") {
        every { repository.existsByTitleAndAuthor(any(), any()) } returns true

        When("저장하면") {
            then("오류가 발생한다") {
                val book = BookTestFixture.generateBook()
                val command = CreateBookCommand(
                    title = book.title,
                    author = book.author,
                    price = book.price,
                    quantity = book.quantity,
                )

                shouldThrow<DuplicateBookException> {
                    service.execute(command)
                }
            }
        }
    }
})