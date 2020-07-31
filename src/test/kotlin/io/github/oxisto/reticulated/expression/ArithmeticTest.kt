/*
 * Copyright (c) 2020, Christian Banse and Andreas Hager. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.operator.AdditiveExpr
import io.github.oxisto.reticulated.ast.expression.operator.MultiplicativeExpr
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.operator.UnaryExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.*
import io.github.oxisto.reticulated.ast.simple.AssignmentExpression
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
    // print(
    // beautifyResult(
    // input.toString()
    // )
    // )
    val firstAssignment = input.statementAsOrNull<AssignmentExpression>(0)
    val firstTarget = firstAssignment?.target as Identifier
    assertEquals(firstTarget.name, "a")

    val firstAdditiveExpr = firstAssignment.expression as AdditiveExpr
    val firstValue = firstAdditiveExpr.additiveExpr as Integer
    assertEquals(firstValue.value, 1)

    val firstBinaryOperator = firstAdditiveExpr.binaryOperator
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
    val valueOfFUEN4 = firstUnaryExprN4.baseOperator as Integer
    assertNotNull(valueOfFUEN4)
    assertEquals(valueOfFUEN4.value, 1)

    val secondAssignment = input.statements[1] as AssignmentExpression
    val secondTarget = secondAssignment.target as Identifier
    assertEquals(secondTarget.name, "b")
    val secondAdditiveExpr = secondAssignment.expression as AdditiveExpr
    val floatOfSSAE = secondAdditiveExpr.additiveExpr as FloatNumber
    assertNotNull(floatOfSSAE)
    assertEquals(floatOfSSAE.value, "0.5".toFloat())
    val binaryOperator = secondAdditiveExpr.binaryOperator
    assertNotNull(binaryOperator)
    assertEquals(binaryOperator.symbol, "+")
    val imagNumberOfMSSAE = secondAdditiveExpr.multiplicativeExpr as ImagNumber
    assertNotNull(imagNumberOfMSSAE)
    val valueOfImagNumberOfMSSAE = imagNumberOfMSSAE.integer as Integer
    assertNotNull(valueOfImagNumberOfMSSAE)
    assertEquals(valueOfImagNumberOfMSSAE.value, 1)

    val thirdAssignment = input.statements[2] as AssignmentExpression
    val thirdTarget = thirdAssignment.target as Identifier
    assertEquals(thirdTarget.name, "c")
    val thirdAdditiveExpr = thirdAssignment.expression as AdditiveExpr
    assertNotNull(thirdAdditiveExpr)
    val stringLiteralOfSAETAE = thirdAdditiveExpr.additiveExpr as StringLiteral
    assertNotNull(stringLiteralOfSAETAE)
    assertEquals(stringLiteralOfSAETAE.value, "Test")
    val binaryOperatorOfTAE = thirdAdditiveExpr.binaryOperator
    assertNotNull(binaryOperatorOfTAE)
    assertEquals(binaryOperatorOfTAE.symbol, "+")
    val stringLiteralOfMETAE = thirdAdditiveExpr.multiplicativeExpr as StringLiteral
    assertNotNull(stringLiteralOfMETAE)
    assertEquals(stringLiteralOfMETAE.value, "Test")


    val forthAssignment = input.statements[3] as AssignmentExpression
    val forthTarget = forthAssignment.target as Identifier
    assertEquals(forthTarget.name, "d")
    val forthAdditiveExpr = forthAssignment.expression as AdditiveExpr
    assertNotNull(forthAdditiveExpr)
    val valueOfSAEFAE = forthAdditiveExpr.additiveExpr as Integer
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
    val floatNumberOfMEFAE2 = multExprFAE2.baseOperator as FloatNumber
    assertNotNull(floatNumberOfMEFAE2)
    assertEquals(floatNumberOfMEFAE2.value, "0.5".toFloat())

    val fifthAssignment = input.statements[4] as AssignmentExpression
    val fifthTarget = fifthAssignment.target as Identifier
    assertEquals(fifthTarget.name, "e")
    val fifthAdditiveExpr = fifthAssignment.expression as AdditiveExpr
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
    val bytesLiteralOfSAEFiAE = unaryExprOfSAEFiAE2.baseOperator as BytesLiteral
    assertNotNull(bytesLiteralOfSAEFiAE)
    assertEquals(bytesLiteralOfSAEFiAE.value, "11".toByte())
    val binaryOperatorOfFiAE = fifthAdditiveExpr.binaryOperator
    assertNotNull(binaryOperatorOfFiAE)
    assertEquals(binaryOperatorOfFiAE.symbol, "-")
    val imagNumberOfFiAE = fifthAdditiveExpr.multiplicativeExpr as ImagNumber
    assertNotNull(imagNumberOfFiAE)
    val floatNumberOfINFiAE = imagNumberOfFiAE.floatNumber
    assertNotNull(floatNumberOfINFiAE)
    assertEquals(floatNumberOfINFiAE.value, "0.1".toFloat())
  }

  @Test
  fun multiplicativeTest() {
    val file = File(
      javaClass
        .classLoader
        .getResource("expressions/arithmetic/multiplicative.py")!!
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
    val power = input.statements[0] as PowerExpr
    assertNotNull(power)
    val primary = power.primary as BytesLiteral
    assertNotNull(primary)
    assertEquals(primary.value, "10".toByte())
    val primarySBO = power.baseOperator as ImagNumber
    assertNotNull(primarySBO)
    val floatNumberPSBO = primarySBO.floatNumber
    assertNotNull(floatNumberPSBO)
    assertEquals(floatNumberPSBO.value, "0.1".toFloat())

    val baseOperatorSE = input.statements[1] as MultiplicativeExpr
    assertNotNull(baseOperatorSE)
    val multiplicativeExprSE = baseOperatorSE.multiplicativeExpr as UnaryExpr
    assertNotNull(multiplicativeExprSE)
    val unaryOperatorMSE = multiplicativeExprSE.unaryOperator
    assertNotNull(unaryOperatorMSE)
    assertEquals(unaryOperatorMSE.symbol, "+")
    val floatNumberBOMSE = multiplicativeExprSE.baseOperator as FloatNumber
    assertNotNull(floatNumberBOMSE)
    assertEquals(floatNumberBOMSE.value, "0.5".toFloat())
    val binaryOperatorSE = baseOperatorSE.binaryOperator
    assertNotNull(binaryOperatorSE)
    assertEquals(binaryOperatorSE.symbol, "*")
    val unaryExprSE = baseOperatorSE.unaryExpr as UnaryExpr
    assertNotNull(unaryExprSE)
    val unaryOperatorSE = unaryExprSE.unaryOperator
    assertNotNull(unaryOperatorSE)
    assertEquals(unaryOperatorSE.symbol, "-")
    val bytesLiteralUOSE = unaryExprSE.baseOperator as BytesLiteral
    assertNotNull(bytesLiteralUOSE)
    assertEquals(bytesLiteralUOSE.value, "1".toByte())

    val baseOperatorTE = input.statements[2] as MultiplicativeExpr
    assertNotNull(baseOperatorTE)
    val bytesLiteralTE = baseOperatorTE.multiplicativeExpr as BytesLiteral
    assertNotNull(bytesLiteralTE)
    assertEquals(bytesLiteralTE.value, "0".toByte())
    val binaryOperatorTE = baseOperatorTE.binaryOperator
    assertNotNull(binaryOperatorTE)
    assertNotNull(binaryOperatorTE.symbol, "/")
    val imagNumberTE = baseOperatorTE.unaryExpr as ImagNumber
    assertNotNull(imagNumberTE)
    val floatNumberTE = imagNumberTE.floatNumber
    assertNotNull(floatNumberTE)
    assertEquals(floatNumberTE.value, "0.1".toFloat())

    val baseOperatorFoE = input.statements[3] as MultiplicativeExpr
    assertNotNull(baseOperatorFoE)
    val bytesLiteralFoE = baseOperatorFoE.multiplicativeExpr as BytesLiteral
    assertNotNull(bytesLiteralFoE)
    assertEquals(bytesLiteralFoE.value, "1".toByte())
    val binaryOperatorFoE = baseOperatorFoE.binaryOperator
    assertNotNull(binaryOperatorFoE)
    assertEquals(binaryOperatorFoE.symbol, "//")
    val floatNumberFoE = baseOperatorFoE.unaryExpr as FloatNumber
    assertNotNull(floatNumberFoE)
    assertEquals(floatNumberFoE.value, "0.5".toFloat())

    val baseOperatorFiE = input.statements[4] as MultiplicativeExpr
    assertNotNull(baseOperatorFiE)
    val integerFiE = baseOperatorFiE.multiplicativeExpr as Integer
    assertNotNull(integerFiE)
    assertEquals(integerFiE.value, 1)
    val binaryOperatorFiE = baseOperatorFiE.binaryOperator
    assertNotNull(binaryOperatorFiE)
    assertEquals(binaryOperatorFiE.symbol, "%")
    val floatNumberFiE = baseOperatorFiE.unaryExpr as FloatNumber
    assertNotNull(floatNumberFiE)
    assertEquals(floatNumberFiE.value, "0.5".toFloat())
  }
}