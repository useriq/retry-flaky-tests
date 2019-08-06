import core.retryableTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("Дожатие тестов с помощью обертки над тестом")
class RetryableTest {

    var tryCount = 0

    @Test
    fun failedTest() = retryableTest {
        println("testFailed")
        throw Exception("FAIL")
    }

    @Test
    fun successTest() = retryableTest {
        println("testSuccess")
    }

    @Test
    fun successAfterRetryTest() = retryableTest {
        if (tryCount < 2) {
            tryCount++
            println("testFailed")
            throw Exception("testFailed")
        }
        println("testSuccess")
    }
}