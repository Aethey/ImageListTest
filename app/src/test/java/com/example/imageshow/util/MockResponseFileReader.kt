package com.example.imageshow.util

import java.io.InputStreamReader

/**
 * Created by Ryu on 18,五月,2021
 *
 * Test utility to read json files for a MockWebServer.
 * @property content The content of the read json file.
 * @param path The name of the json file to read in the `resources` directory.
 */
class MockResponseFileReader(path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}