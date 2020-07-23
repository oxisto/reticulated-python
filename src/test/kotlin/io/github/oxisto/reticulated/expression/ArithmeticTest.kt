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
import io.github.oxisto.reticulated.ast.expression.ConditionalExpression
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.*
import io.github.oxisto.reticulated.ast.expression.operator.*
import io.github.oxisto.reticulated.ast.simple.AssignmentExpression
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
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
                    (
                        input.statements[0] as StatementList
                        )[0] as AssignmentExpression
                    ).expression as ConditionalExpression
                ).orTest as OrTest
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
                    (
                        input.statements[1] as StatementList
                        )[0] as AssignmentExpression
                    ).expression as ConditionalExpression
                ).orTest as OrTest
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
                    (
                        input.statements[2] as StatementList
                        )[0] as AssignmentExpression
                    ).expression as ConditionalExpression
                ).orTest as OrTest
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
                    (
                        input.statements[3] as StatementList
                        )[0] as AssignmentExpression
                    ).expression as ConditionalExpression
                ).orTest as OrTest
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
                    (
                        input.statements[4] as StatementList
                        )[0] as AssignmentExpression
                    ).expression as ConditionalExpression
                ).orTest as OrTest
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

  @Test fun multiplicativeTest(){
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
    val baseOperator = (
        (
            (
                (
                    input.statements[0] as StatementList
                    )[0] as ExpressionStatement
                ).starredExpression.expression as ConditionalExpression
            ).orTest as OrTest
        ).andTest
        .notTest
        .comparison!!
        .orExpr
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as PowerExpr
    assertNotNull(baseOperator)
    val primary = baseOperator.primary as BytesLiteral
    assertNotNull(primary)
    assertEquals(primary.value, "10".toByte())
    val subBaseOperator = baseOperator.baseOperator as PowerExpr
    assertNotNull(subBaseOperator)
    val primarySBO = subBaseOperator.primary as ImagNumber
    assertNotNull(primarySBO)
    val floatNumberPSBO = primarySBO.floatNumber
    assertNotNull(floatNumberPSBO)
    assertEquals(floatNumberPSBO.value, "0.1".toFloat())

    val baseOperatorSE = (
        (
            (
                (
                    input.statements[1] as StatementList
                    )[0] as ExpressionStatement
                ).starredExpression.expression as ConditionalExpression
            ).orTest as OrTest
        ).andTest
        .notTest
        .comparison!!
        .orExpr
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as MultiplicativeExpr
    assertNotNull(baseOperatorSE)
    val multiplicativeExprSE = baseOperatorSE.multiplicativeExpr as UnaryExpr
    assertNotNull(multiplicativeExprSE)
    val unaryOperatorMSE = multiplicativeExprSE.unaryOperator
    assertNotNull(unaryOperatorMSE)
    assertEquals(unaryOperatorMSE.symbol, "+")
    val subBaseOperatorMSE = multiplicativeExprSE.baseOperator as PowerExpr
    assertNotNull(subBaseOperatorMSE)
    val floatNumberBOMSE = subBaseOperatorMSE.primary as FloatNumber
    assertNotNull(floatNumberBOMSE)
    assertEquals(floatNumberBOMSE.value, "0.5".toFloat())
    val binaryOperatorSE = baseOperatorSE.binaryOperator as BinaryOperator
    assertNotNull(binaryOperatorSE)
    assertEquals(binaryOperatorSE.symbol, "*")
    val unaryExprSE = baseOperatorSE.unaryExpr as UnaryExpr
    assertNotNull(unaryExprSE)
    val unaryOperatorSE = unaryExprSE.unaryOperator
    assertNotNull(unaryOperatorSE)
    assertEquals(unaryOperatorSE.symbol, "-")
    val baseOperatorUOSE = unaryExprSE.baseOperator as PowerExpr
    assertNotNull(baseOperatorUOSE)
    val bytesLiteralUOSE = baseOperatorUOSE.primary as BytesLiteral
    assertNotNull(bytesLiteralUOSE)
    assertEquals(bytesLiteralUOSE.value, "1".toByte())

    val baseOperatorTE = (
        (
            (
                (
                    input.statements[2] as StatementList
                    )[0] as ExpressionStatement
                ).starredExpression.expression as ConditionalExpression
            ).orTest as OrTest
        ).andTest
        .notTest
        .comparison!!
        .orExpr
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as MultiplicativeExpr
    assertNotNull(baseOperatorTE)
    val multiplicativeExprTE = baseOperatorTE.multiplicativeExpr as PowerExpr
    assertNotNull(multiplicativeExprTE)
    val bytesLiteralTE = multiplicativeExprTE.primary as BytesLiteral
    assertNotNull(bytesLiteralTE)
    assertEquals(bytesLiteralTE.value, "0".toByte())
    val binaryOperatorTE = baseOperatorTE.binaryOperator
    assertNotNull(binaryOperatorTE)
    assertNotNull(binaryOperatorTE.symbol, "/")
    val unaryExprTE = baseOperatorTE.unaryExpr as PowerExpr
    assertNotNull(unaryExprTE)
    val imagNumberTE = unaryExprTE.primary as ImagNumber
    assertNotNull(imagNumberTE)
    val floatNumberTE = imagNumberTE.floatNumber
    assertNotNull(floatNumberTE)
    assertEquals(floatNumberTE.value, "0.1".toFloat())

    val baseOperatorFoE = (
        (
            (
                (
                    input.statements[3] as StatementList
                    )[0] as ExpressionStatement
                ).starredExpression.expression as ConditionalExpression
            ).orTest as OrTest
        ).andTest
        .notTest
        .comparison!!
        .orExpr
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as MultiplicativeExpr
    assertNotNull(baseOperatorFoE)
    val multiplicativeExprFoE = baseOperatorFoE.multiplicativeExpr as PowerExpr
    assertNotNull(multiplicativeExprFoE)
    val bytesLiteralFoE = multiplicativeExprFoE.primary as BytesLiteral
    assertNotNull(bytesLiteralFoE)
    assertEquals(bytesLiteralFoE.value, "1".toByte())
    val binaryOperatorFoE = baseOperatorFoE.binaryOperator
    assertNotNull(binaryOperatorFoE)
    assertEquals(binaryOperatorFoE.symbol, "//")
    val unaryExprFoE = baseOperatorFoE.unaryExpr as PowerExpr
    assertNotNull(unaryExprFoE)
    val floatNumberFoE = unaryExprFoE.primary as FloatNumber
    assertNotNull(floatNumberFoE)
    assertEquals(floatNumberFoE.value, "0.5".toFloat())

    val baseOperatorFiE = (
        (
            (
                (
                    input.statements[4] as StatementList
                    )[0] as ExpressionStatement
                ).starredExpression.expression as ConditionalExpression
            ).orTest as OrTest
        ).andTest
        .notTest
        .comparison!!
        .orExpr
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as MultiplicativeExpr
    assertNotNull(baseOperatorFiE)
    val multiplicativeExprFiE = baseOperatorFiE.multiplicativeExpr as PowerExpr
    assertNotNull(multiplicativeExprFiE)
    val integerFiE = multiplicativeExprFiE.primary as Integer
    assertNotNull(integerFiE)
    assertEquals(integerFiE.value, 1)
    val binaryOperatorFiE = baseOperatorFiE.binaryOperator
    assertNotNull(binaryOperatorFiE)
    assertEquals(binaryOperatorFiE.symbol, "%")
    val unaryExprFiE = baseOperatorFiE.unaryExpr as PowerExpr
    assertNotNull(unaryExprFiE)
    val floatNumberFiE = unaryExprFiE.primary as FloatNumber
    assertNotNull(floatNumberFiE)
    assertEquals(floatNumberFiE.value, "0.5".toFloat())
  }

  @Test fun multiplicativeToStringTest(){
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
    val inputString = input.toString()
    assertEquals(inputString, """FileInput(statements=[StatementList(
	statements=[ExpressionStatement(
	StarredExpression=StarredExpression(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=BytesLiteral(
	value=10
) power=** unaryExpr=PowerExpr(
	primary=ImagNumber(
	value=FloatNumber(
	value=0.1
) j
)
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[ExpressionStatement(
	StarredExpression=StarredExpression(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=MultiplicativeExpr(
	multiplicativeExpr=UnaryExpr(
	unaryOperator=POSITIVEUnaryExpr=PowerExpr(
	primary=FloatNumber(
	value=0.5
)
)
) binaryOperator=MULTIPLICATION unaryExpr=UnaryExpr(
	unaryOperator=NEGATIVEUnaryExpr=PowerExpr(
	primary=BytesLiteral(
	value=1
)
)
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[ExpressionStatement(
	StarredExpression=StarredExpression(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=MultiplicativeExpr(
	multiplicativeExpr=PowerExpr(
	primary=BytesLiteral(
	value=0
)
) binaryOperator=DIVISION unaryExpr=PowerExpr(
	primary=ImagNumber(
	value=FloatNumber(
	value=0.1
) j
)
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[ExpressionStatement(
	StarredExpression=StarredExpression(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=MultiplicativeExpr(
	multiplicativeExpr=PowerExpr(
	primary=BytesLiteral(
	value=1
)
) binaryOperator=FLOOR_DIVISION unaryExpr=PowerExpr(
	primary=FloatNumber(
	value=0.5
)
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[ExpressionStatement(
	StarredExpression=StarredExpression(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=MultiplicativeExpr(
	multiplicativeExpr=PowerExpr(
	primary=Integer(
	value=1
)
) binaryOperator=MODULO unaryExpr=PowerExpr(
	primary=FloatNumber(
	value=0.5
)
)
)
)
)
)
)
)
)
)
)
)
)
)]
)])""".replace("\n", System.lineSeparator()))
  }

    @Test fun arithmeticToStringTest(){
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
        val inputString = input.toString()
        assertEquals(inputString, """FileInput(statements=[StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='a'
) expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=AdditiveExpr(
	additiveExpr=PowerExpr(
	primary=Integer(
	value=1
)
) binaryOperator=ADDITION multiplicativeExpr=UnaryExpr(
	unaryOperator=NEGATIVEUnaryExpr=UnaryExpr(
	unaryOperator=NEGATIVEUnaryExpr=UnaryExpr(
	unaryOperator=POSITIVEUnaryExpr=UnaryExpr(
	unaryOperator=NEGATIVEUnaryExpr=PowerExpr(
	primary=Integer(
	value=1
)
)
)
)
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='b'
) expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=AdditiveExpr(
	additiveExpr=PowerExpr(
	primary=FloatNumber(
	value=0.5
)
) binaryOperator=ADDITION multiplicativeExpr=PowerExpr(
	primary=ImagNumber(
	value=Integer(
	value=1
) j
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='c'
) expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=AdditiveExpr(
	additiveExpr=PowerExpr(
	primary=StringLiteral(
	value=Test
)
) binaryOperator=ADDITION multiplicativeExpr=PowerExpr(
	primary=StringLiteral(
	value=Test
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='d'
) expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=AdditiveExpr(
	additiveExpr=PowerExpr(
	primary=Integer(
	value=1
)
) binaryOperator=SUBTRACTION multiplicativeExpr=UnaryExpr(
	unaryOperator=NEGATIVEUnaryExpr=UnaryExpr(
	unaryOperator=POSITIVEUnaryExpr=PowerExpr(
	primary=FloatNumber(
	value=0.5
)
)
)
)
)
)
)
)
)
)
)
)
)
)
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='e'
) expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=AdditiveExpr(
	additiveExpr=UnaryExpr(
	unaryOperator=BITWISE_NOTUnaryExpr=UnaryExpr(
	unaryOperator=BITWISE_NOTUnaryExpr=PowerExpr(
	primary=BytesLiteral(
	value=11
)
)
)
) binaryOperator=SUBTRACTION multiplicativeExpr=PowerExpr(
	primary=ImagNumber(
	value=FloatNumber(
	value=0.1
) j
)
)
)
)
)
)
)
)
)
)
)
)
)]
)])""".replace("\n", System.lineSeparator()))
    }
}