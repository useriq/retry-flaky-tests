import core.RerunExtension
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(PER_CLASS)
@DisplayName("Дожатие тестов с помощью RerunExtension")
class RetryableTest {

    private var tryCount = 0

    @BeforeAll
    fun beforeAll() {
        println("BeforeAll")
    }

    @AfterAll
    fun afterAll() {
        println("AfterAll")
    }

    @BeforeEach
    fun beforeEach() {
        println("BeforeEach")
    }

    @AfterEach
    fun afterEach() {
        println("AfterEach")
    }

    @TestTemplate
    @ExtendWith(RerunExtension::class)
    fun failedTest() {
        println("testFailed")
        throw Exception("FAIL")
    }

    @TestTemplate
    @ExtendWith(RerunExtension::class)
    fun successTest() {
        println("testSuccess")
    }

    @TestTemplate
    @ExtendWith(RerunExtension::class)
    fun successAfterRetryTest() {
        if (tryCount < 2) {
            tryCount++
            println("testFailed")
            throw Exception("testFailed")
        }
        println("testSuccess")
    }

}