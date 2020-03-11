package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.literal.Integer
import io.github.oxisto.reticulated.ast.expression.operator.AdditiveExpr
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.operator.UnaryExpr
import io.github.oxisto.reticulated.ast.simple.AssignmentExpression
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ArithmeticTest {
  @Test
  fun testAdditiveExpressions() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/arithmetic/additive.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    val firstAdditiveExpr = (
        (
            (
                (
                    input.statements[0] as StatementList
                    )[0] as AssignmentExpression
                ).expression as OrTest
            ).andTest
            .notTest
            .comparison as Comparison
        ).orExpr
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as AdditiveExpr
    assertNotNull(firstAdditiveExpr)
    val firstSubAdditiveExpr  = firstAdditiveExpr.additiveExpr as PowerExpr
    assertNotNull(firstSubAdditiveExpr)
    val firstValueOfSAE = firstSubAdditiveExpr.primary as Integer
    assertNotNull(firstValueOfSAE)
    assertEquals(firstValueOfSAE.value, 1)
    val firstBinaryOperator = firstAdditiveExpr.binaryOperator
    assertNotNull(firstBinaryOperator)
    assertEquals(firstBinaryOperator.symbol, "+")
    val firstUnaryExprN1 = firstAdditiveExpr.multiplicativeExpr as UnaryExpr
    assertNotNull(firstUnaryExprN1)
    assertEquals(firstUnaryExprN1.unaryOperator.symbol, "-")
    val firstUnaryExprN2 = firstUnaryExprN1.baseOperator as UnaryExpr
    assertNotNull(firstUnaryExprN2)
    assertEquals(firstUnaryExprN2.unaryOperator.symbol, "-")
    val firstUnaryExprP3 = firstUnaryExprN2.baseOperator as UnaryExpr
    assertNotNull(firstUnaryExprP3)
    assertEquals(firstUnaryExprP3.unaryOperator.symbol, "+")
    val firstUnaryExprN4 = firstUnaryExprP3.baseOperator as UnaryExpr
    assertNotNull(firstUnaryExprN4)
    assertEquals(firstUnaryExprN4.unaryOperator.symbol, "-")
    val valueOfFUEN4 = (firstUnaryExprN4.baseOperator as PowerExpr).primary as Integer
    assertNotNull(valueOfFUEN4)
    assertEquals(valueOfFUEN4.value, 1)

    val secondAdditiveExpr = (
        (
            (
                (
                    input.statements[1] as StatementList
                    )[0] as AssignmentExpression
                ).expression as OrTest
            ).andTest
            .notTest
            .comparison as Comparison
        ).orExpr
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as AdditiveExpr
    assertNotNull(secondAdditiveExpr)
    // TODO: 2., 3., 4. and 5. additiveExpr
  }
}