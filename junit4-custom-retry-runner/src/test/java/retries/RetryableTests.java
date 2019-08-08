package retries;
import core.Junit4Rerunner;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(Junit4Rerunner.class)
public class RetryableTests {

    private int tryCount = 0;

    @BeforeClass
    public static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @AfterClass
    public static void afterAll() {
        System.out.println("AfterAll");
    }

    @Before
    public void beforeEach() {
        System.out.println("BeforeEach");
    }

    @After
    public void afterEach() {
        System.out.println("AfterEach");
    }

    @Test
    public void failedTest() {
        System.out.println("testFailed");
        throw new RuntimeException("FAIL");
    }

    @Test
    public void successTest() {
        System.out.println("testSuccess");
    }

    @Test
    public void successAfterRetryTest() {
        if (tryCount < 2) {
            tryCount++;
            System.out.println("testFailed");
            throw new RuntimeException("testFailed");
        }
        System.out.println("testSuccess");
    }
}