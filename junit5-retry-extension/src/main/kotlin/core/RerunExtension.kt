package core

import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestTemplateInvocationContext
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider
import org.junit.platform.commons.util.AnnotationUtils.isAnnotated
import java.util.*
import java.util.Spliterators.spliteratorUnknownSize
import java.util.stream.Stream
import java.util.stream.StreamSupport.stream

const val DEFAULT_RERUN_COUNT = 3

class RerunExtension : TestTemplateInvocationContextProvider, AfterTestExecutionCallback {

    var isRunFailed = false

    // set displayName for all test reruns
    lateinit var displayName: String

    override fun provideTestTemplateInvocationContexts(context: ExtensionContext): Stream<TestTemplateInvocationContext> {
        displayName = context.displayName
        return stream(spliteratorUnknownSize(TestTemplateIterator(), Spliterator.NONNULL), false)
    }

    override fun supportsTestTemplate(context: ExtensionContext): Boolean {
        return isAnnotated(context.testMethod, TestTemplate::class.java)
    }

    override fun afterTestExecution(context: ExtensionContext) {
        if (context.executionException.isPresent) {
            isRunFailed = true
            println("Test run of ${context.displayName} failed")
        } else isRunFailed = false
    }

    inner class TestTemplateIterator : Iterator<TestTemplateInvocationContext> {
        var currentRun = 0

        override fun hasNext(): Boolean {
            return if (currentRun == 0) {
                return true
            } else {
                isRunFailed && currentRun < DEFAULT_RERUN_COUNT
            }
        }

        override fun next(): TestTemplateInvocationContext {
            if (hasNext()) {
                currentRun++
                return RepeatInvocationContext(displayName)
            } else throw NoSuchElementException("Rerun is not required already")
        }
    }
}


