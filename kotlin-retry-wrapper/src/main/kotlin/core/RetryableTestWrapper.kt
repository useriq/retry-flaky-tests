package core

const val MAX_TRY_COUNT = 3

fun retryableTest(testCode: () -> Unit) {
    var finalError: Throwable? = null
    for (i in 0 until MAX_TRY_COUNT) {
        try {
            testCode()
            finalError = null
            break
        } catch (e: Throwable) {
            finalError = e
        }
    }
    finalError?.let { throw it }
}
