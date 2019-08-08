def call(Map<String, String> params) {
    assert params["runId"]

    def threadsCount = params["threadsCount"] ?: "8"
    def testList = params["testList"] ?: "*Test"
    def runId = params["runId"]

    int FAILED_TESTS_TRESHOLD = 40
    def countFailedTests = 0
    def failedTests
    int run = 1

    boolean isFinished = false
    int threads = threadsCount as int

    while (run <= Integer.valueOf(runCount) && !isFinished) {

        if (run == 1) {
            startTests()
        } else {
            if (countFailedTests > 0) {
                threads = reduceThreads(threads)
                testList = failedTests.toString().minus('[').minus(']').minus(' ')
                startTests()
            }
        }

        stage("check ${run}_run result ") {
            failedTests = getFailedTests(runId)

            def previousCountFailedTests = countFailedTests

            countFailedTests = failedTests.size()

            if (countFailedTests == 0) {
                echo "FINISHED"
                isFinished = true
            }
            if (countFailedTests > FAILED_TESTS_TRESHOLD) {
                echo "TERMINATED - too much failed tests > ${FAILED_TESTS_TRESHOLD}"
                isFinished = true
            }
            if (countFailedTests == previousCountFailedTests) {
                echo "TERMINATED - no one new passed test after retry"
                isFinished = true
            }
        }

        run += 1
    }
}