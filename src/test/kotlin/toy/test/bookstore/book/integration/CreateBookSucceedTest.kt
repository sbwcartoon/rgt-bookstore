package toy.test.bookstore.book.integration

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import toy.test.bookstore.book.adapter.`in`.dto.CreateBookRequest
import toy.test.bookstore.book.adapter.`in`.dto.CreateBookResponse
import toy.test.bookstore.book.adapter.out.persistence.repository.BookJpaRepository
import toy.test.bookstore.book.integration.config.IntegrationTest

@IntegrationTest
@AutoConfigureMockMvc
class CreateBookSucceedTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val jpaRepository: BookJpaRepository,
) : BehaviorSpec({

    beforeSpec {
        jpaRepository.deleteAll()
    }

    Given("책 생성 요청을 할 때") {
        When("조건을 만족하면") {
            Then("저장되고 저장한 책 아이디가 반환된다") {
                val existing = CreateBookRequest(
                    title = "title",
                    author = "author",
                    price = 1,
                    quantity = 1,
                )

                val responseString = mockMvc.post("/api/books") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(existing)
                }.andExpect {
                    status { isOk() }
                }.andReturn().response.contentAsString

                val responseDto = objectMapper.readValue(responseString, CreateBookResponse::class.java)
                responseDto.id shouldNotBe ""
            }
        }
    }
})