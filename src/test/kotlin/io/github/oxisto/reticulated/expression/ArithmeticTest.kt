package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.literal.*
import io.github.oxisto.reticulated.ast.expression.operator.*
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
    val subSecondAdditiveExpr = secondAdditiveExpr.additiveExpr as PowerExpr
    assertNotNull(subSecondAdditiveExpr)
    val floatOfSSAE = subSecondAdditiveExpr.primary as FloatNumber
    assertNotNull(floatOfSSAE)
    assertEquals(floatOfSSAE.value, "0.5".toFloat())
    val binaryOperator = secondAdditiveExpr.binaryOperator
    assertNotNull(binaryOperator)
    assertEquals(binaryOperator.symbol, "+")
    val multiplicativeExprOfSSAE = secondAdditiveExpr.multiplicativeExpr as PowerExpr
    assertNotNull(multiplicativeExprOfSSAE)
    val imagNumberOfMSSAE = multiplicativeExprOfSSAE.primary as ImagNumber
    assertNotNull(imagNumberOfMSSAE)
    val valueOfImagNumberOfMSSAE = imagNumberOfMSSAE.integer as Integer
    assertNotNull(valueOfImagNumberOfMSSAE)
    assertEquals(valueOfImagNumberOfMSSAE.value, 1)

    val thirdAdditiveExpr = (
        (
            (
                (
                    input.statements[2] as StatementList
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
    assertNotNull(thirdAdditiveExpr)
    val subAdditiveExprOfTAE = thirdAdditiveExpr.additiveExpr as PowerExpr
    assertNotNull(subAdditiveExprOfTAE)
    val stringLiteralOfSAETAE = subAdditiveExprOfTAE.primary as StringLiteral
    assertNotNull(stringLiteralOfSAETAE)
    assertEquals(stringLiteralOfSAETAE.value, "Test")
    val binaryOperatorOfTAE = thirdAdditiveExpr.binaryOperator as BinaryOperator
    assertNotNull(binaryOperatorOfTAE)
    assertEquals(binaryOperatorOfTAE.symbol, "+")
    val multiplicativeExprOfTAE = thirdAdditiveExpr.multiplicativeExpr as PowerExpr
    assertNotNull(multiplicativeExprOfTAE)
    val stringLiteralOfMETAE = multiplicativeExprOfTAE.primary as StringLiteral
    assertNotNull(stringLiteralOfMETAE)
    assertEquals(stringLiteralOfMETAE.value, "Test")

    val forthAdditiveExpr = (
        (
            (
                (
                    input.statements[3] as StatementList
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
    assertNotNull(forthAdditiveExpr)
    val subAdditiveExprOfFAE = forthAdditiveExpr.additiveExpr as PowerExpr
    assertNotNull(subAdditiveExprOfFAE)
    val valueOfSAEFAE = subAdditiveExprOfFAE.primary as Integer
    assertNotNull(valueOfSAEFAE)
    assertEquals(valueOfSAEFAE.value, 1)
    val binaryOperatorOfFAE = forthAdditiveExpr.binaryOperator
    assertNotNull(binaryOperatorOfFAE)
    assertEquals(binaryOperatorOfFAE.symbol, "-")
    val multExprFAE = forthAdditiveExpr.multiplicativeExpr as UnaryExpr
    assertNotNull(multExprFAE)
    val unaryOperatorMEFAE1 = multExprFAE.unaryOperator
    assertNotNull(unaryOperatorMEFAE1)
    assertEquals(unaryOperatorMEFAE1.symbol, "-")
    val multExprFAE2 = multExprFAE.baseOperator as UnaryExpr
    assertNotNull(multExprFAE2)
    val unaryOperatorMEFAE2 = multExprFAE2.unaryOperator
    assertNotNull(unaryOperatorMEFAE2)
    assertEquals(unaryOperatorMEFAE2.symbol, "+")
    val baseOperatorOfMEFAE2 = multExprFAE2.baseOperator as PowerExpr
    assertNotNull(baseOperatorOfMEFAE2)
    val floatNumberOfMEFAE2 = baseOperatorOfMEFAE2.primary as FloatNumber
    assertNotNull(floatNumberOfMEFAE2)
    assertEquals(floatNumberOfMEFAE2.value, "0.5".toFloat())

    val fifthAdditiveExpr = (
        (
            (
                (
                    input.statements[4] as StatementList
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
    assertNotNull(fifthAdditiveExpr)
    val subAdditiveExprOfFiAE = fifthAdditiveExpr.additiveExpr as UnaryExpr
    assertNotNull(subAdditiveExprOfFiAE)
    val unaryOpOfSAEFiAE = subAdditiveExprOfFiAE.unaryOperator
    assertNotNull(unaryOpOfSAEFiAE)
    assertEquals(unaryOpOfSAEFiAE.symbol, "~")
    val unaryExprOfSAEFiAE2 = subAdditiveExprOfFiAE.baseOperator as UnaryExpr
    assertNotNull(unaryExprOfSAEFiAE2)
    val unaryOpOfSAEFiAE2 = unaryExprOfSAEFiAE2.unaryOperator
    assertNotNull(unaryOpOfSAEFiAE2)
    assertEquals(unaryOpOfSAEFiAE2.symbol, "~")
    val baseOpOfSAEFiAE = unaryExprOfSAEFiAE2.baseOperator as PowerExpr
    assertNotNull(baseOpOfSAEFiAE)
    val bytesLiteralOfSAEFiAE = baseOpOfSAEFiAE.primary as BytesLiteral
    assertNotNull(bytesLiteralOfSAEFiAE)
    assertEquals(bytesLiteralOfSAEFiAE.value, "11".toByte())
    val binaryOperatorOfFiAE = fifthAdditiveExpr.binaryOperator
    assertNotNull(binaryOperatorOfFiAE)
    assertEquals(binaryOperatorOfFiAE.symbol, "-")
    val multExprFiAE = fifthAdditiveExpr.multiplicativeExpr as PowerExpr
    assertNotNull(multExprFiAE)
    val imagNumberOfFiAE = multExprFiAE.primary as ImagNumber
    assertNotNull(imagNumberOfFiAE)
    val floatNumberOfINFiAE = imagNumberOfFiAE.floatNumber
    assertNotNull(floatNumberOfINFiAE)
    assertEquals(floatNumberOfINFiAE.value, "0.1".toFloat())
  }
}