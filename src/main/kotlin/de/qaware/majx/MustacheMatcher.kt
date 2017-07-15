package de.qaware.majx

import com.github.mustachejava.DefaultMustacheFactory
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import java.io.StringReader
import java.io.StringWriter

/**
 * Provides common functionality dealing with mustache expressions.
 */
object MustacheMatcher {

    /**
     * Evaluate the mustache expression.
     *
     * @param expression    Expression to evaluate. If this is not a mustache expression (doesn't contain braces) we
     *                       just return it.
     * @param mustacheScope Scope for evaluation
     * @return Evaluated string
     */
    fun evaluateMustache(expression: String, mustacheScope: Any): String {
        // This is potentially a mustache expression. We don't start the mustache parser unless we know that there might
        // be a mustache expression to improve performance.
        if (potentiallyMustache(expression)) {
            // We could set this as a static variable (it is thread safe) but it just does a bit of caching that we
            // don't need
            val mf = DefaultMustacheFactory()
            val compiledPattern = mf.compile(StringReader(expression), "temporaryExpression")
            // Create output stream and evaluate mustache expression
            val writer = StringWriter()
            compiledPattern.execute(writer, mustacheScope)
            return writer.toString()
        } else {
            return expression
        }
    }

    /**
     * Assert that the actual value is equal to the pattern after the pattern is evaluated as a mustache expression.

     * @param error         Error for assertion (if the actual values does not match the pattern)
     * @param pattern       Pattern that may be a mustache expression
     * @param actual        Actual value
     * @param mustacheScope Scope for evaluation. May be null to deactivate mustache evaluation.
     */
    fun assertEqual(error: String, pattern: String, actual: String, mustacheScope: Any?) {
        if (mustacheScope != null && potentiallyMustache(pattern)) {
            val computedPattern = evaluateMustache(pattern, mustacheScope)
            val errorComplete = error + ". Pattern was evaluated as mustache expression. " +
                    "Original pattern: " + pattern
            assertThat(errorComplete, actual, `is`(computedPattern))
        } else {
            assertThat(error, actual, `is`(pattern))
        }
    }

    /**
     * @param expression Expression to look at
     * @return true if this is potentially a mustache expression. false if this is definitely not a mustache expression
     */
    private fun potentiallyMustache(expression: String): Boolean {
        return expression.contains("{{") && expression.contains("}}")
    }
}
