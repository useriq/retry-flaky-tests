package core

import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.TestTemplateInvocationContext

class RepeatInvocationContext(private val displayName: String) : TestTemplateInvocationContext {
    override fun getDisplayName(invocationIndex: Int): String {
        return displayName
    }

    override fun getAdditionalExtensions(): MutableList<Extension> {
        return mutableListOf()
    }
}