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
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.call.Call
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AsyncComprehensionTest {
    @Test
    fun testAsyncArgumentComprehension() {
        val file = File(
                javaClass
                        .classLoader
                        .getResource("expressions/comprehension/async_comprehension_argument.py")!!
            .file
    )

        val input = PythonParser()
                .parse(file.path)
                .root
        val isAsync = (
            (
                (
                    (
                        (
                            (
                                input.statements[0] as StatementList
                                )[0] as ExpressionStatement
                            ).expression as OrTest
                        ).andTest
                        .notTest
                        .comparison!!
                        .orExpr
                        .xorExpr
                        .andExpr
                        .shiftExpr
                        .baseOperator as PowerExpr
                    ).primary as Call)
                .callTrailer as Comprehension
            ).compFor
            .isAsync
        assertTrue(isAsync)
    }

  @Test fun asyncToStringTest(){
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
) expression=OrTest(
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
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='b'
) expression=OrTest(
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
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='c'
) expression=OrTest(
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
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='d'
) expression=OrTest(
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
)]
), StatementList(
	statements=[AssignmentExpression(
	target=Identifier(
	name='e'
) expression=OrTest(
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
)]
)])""".replace("\n", System.lineSeparator()))
  }
}
