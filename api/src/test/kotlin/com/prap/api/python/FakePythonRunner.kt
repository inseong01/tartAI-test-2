package com.prap.api.python

class FakePythonRunner(
    private val result: String,
    private val exception: RuntimeException? = null
) : PythonRunner() {

    override fun run(): String {
        if (exception != null) {
            throw RuntimeException(exception.message.toString())
        }

        return result
    }
}
