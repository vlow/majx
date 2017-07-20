/**
 * MIT License
 *
 * Copyright (c) 2017 QAware GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.qaware.majx

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import kotlin.test.fail

class UnexpectedTests {

    @Test
    fun testSingleUnexpectedInObject() {
        val actual = readFile("unexpected/singlePropertyUnexpected/object/actual.json")
        val pattern = readFile("unexpected/singlePropertyUnexpected/object/pattern.json")
        val expectedMessage = readFile("unexpected/singlePropertyUnexpected/object/expectedMessage.txt")

        try {
            assertJsonMatches(actual, pattern)
            fail()
        } catch (ex: AssertionError) {
            assertThat(ex.message, `is`(expectedMessage))
        }
    }

    @Test
    fun testSingleUnexpectedInArray() {
        val actual = readFile("unexpected/singlePropertyUnexpected/array/actual.json")
        val pattern = readFile("unexpected/singlePropertyUnexpected/array/pattern.json")
        val expectedMessage = readFile("unexpected/singlePropertyUnexpected/array/expectedMessage.txt")

        try {
            assertJsonMatches(actual, pattern)
            fail()
        } catch (ex: AssertionError) {
            assertThat(ex.message, `is`(expectedMessage))
        }
    }
}