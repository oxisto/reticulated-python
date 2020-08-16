package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.ConditionalExpression
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ConditionalTest {
  @Test
  fun testSlicingParseTree() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/conditional.py")!!
            .file
    )
    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    // print(
        // beautifyResult(
            // input.toString()
        // )
    // )
    val call = input.statements[0] as Call
    val callName = call.primary as Identifier
    assertEquals(callName.name, "print")
    val conditionalExpression = call.callTrailer as ConditionalExpression
    val orTest = conditionalExpression.orTest as Integer
    assertEquals(orTest.value, 1)
    val comparison = conditionalExpression.orTestOptional as Comparison
    val orExpr = comparison.orExpr as Integer
    assertEquals(orExpr.value, 1)
    val pair = comparison.comparisons[0]
    val compOperator = pair.getFirst()
    assertEquals(compOperator.symbol, "<")
    val second = pair.getSecond() as Integer
    assertEquals(second.value, 2)
    val expressionOptional = conditionalExpression.expressionOptional as Integer
    assertEquals(expressionOptional.value, 2)
  }
}