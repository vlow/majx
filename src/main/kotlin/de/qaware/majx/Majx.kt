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

import com.fasterxml.jackson.databind.JsonNode
import java.io.IOException

fun assertJsonMatches(actual: String, pattern: String) =
        assertJsonMatches(null, actual, pattern)

fun assertJsonMatches(actual: String, pattern: String, mustacheScope: Any? = null) =
        assertJsonMatches(null, actual, pattern, mustacheScope)

fun assertJsonMatches(reason: String?, actual: String, pattern: String, mustacheScope: Any? = null) {
    assertJsonMatches(reason,
            parseAndValidate(actual, "actual"),
            parseAndValidate(pattern, "pattern"),
            mustacheScope)
}

private fun parseAndValidate(paramValue: String, paramName: String): JsonNode {
    try {
        return convertToJsonNode(paramValue)
    } catch (ioe: IOException) {
        throw IllegalArgumentException("Failed to parse $paramName as JSON:\n$paramValue", ioe)
    }
}

private fun assertJsonMatches(reason: String?, actual: JsonNode, pattern: JsonNode, mustacheScope: Any?) =
        JsonMatcher(mustacheScope).assertMatches(reason, actual, pattern)
