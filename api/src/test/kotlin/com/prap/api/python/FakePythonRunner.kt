package com.prap.api.python

class FakePythonRunner(
    private val result: String,
    private val exception: Exception? = null
) : PythonRunner() {

    override fun run(): String {
        if (exception != null) {
            throw exception
        }

        return result
    }
}
