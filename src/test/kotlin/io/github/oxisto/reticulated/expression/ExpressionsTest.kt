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
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.call.Call
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ExpressionsTest {
  @Test
  fun testAttributeRef() {
    val file = File(
            javaClass
            .classLoader
            .getResource("import.py")!!
            .file
    )

    val input = PythonParser()
            .parse(file.path)
            .root
    assertNotNull(input)

    val inputString = input.toString()
    assertEquals(inputString, """FileInput(statements=[StatementList(
	statements=[ImportStatement(
	module=Identifier(
	name='os'
)
)]
), StatementList(
	statements=[ExpressionStatement(
	expression=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Call(
	primary=Identifier(
	name='print'
) callTrailer=ArgumentList(
	argument=[Argument(
	expression=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=AttributeRef(
	primary=Identifier(
	name='os'
)"."identifier=Identifier(
	name='name'
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

    val s1 = input.statements[1] as StatementList
    val expressionStatement = s1[0] as ExpressionStatement
    assertNotNull(expressionStatement)
    val orTestCall = expressionStatement.expression as OrTest
    assertNotNull(orTestCall)
    val subOrTestCall = orTestCall.orTest
    assertNull(subOrTestCall)
    val andTestCall = orTestCall.andTest
    assertNotNull(andTestCall)
    val subAndTestCall = andTestCall.andTest
    assertNull(subAndTestCall)
    val notTestCall = andTestCall.notTest
    assertNotNull(notTestCall)
    val subNotTestCall = notTestCall.notTest
    assertNull(subNotTestCall)
    val comparisonCall = notTestCall.comparison
    assertNotNull(comparisonCall)
    val comparisonsCall = comparisonCall.comparisons
    assertNotNull(comparisonsCall)
    assertEquals(comparisonsCall.size, 0)
    val orExprCall = comparisonCall.orExpr
    assertNotNull(orExprCall)
    val subOrExprCall = orExprCall.orExpr
    assertNull(subOrExprCall)
    val xorExprCall = orExprCall.xorExpr
    assertNotNull(xorExprCall)
    val subXorExprCall = xorExprCall.xorExpr
    assertNull(subXorExprCall)
    val andExprCall = xorExprCall.andExpr
    assertNotNull(andExprCall)
    val subAndExprCall = andExprCall.andExpr
    assertNull(subAndExprCall)
    val shiftExprCall = andExprCall.shiftExpr
    assertNotNull(shiftExprCall)
    val subShiftExprCall = shiftExprCall.shiftExpr
    assertNull(subShiftExprCall)
    val binaryOperatorCall = shiftExprCall.binaryOperator
    assertNull(binaryOperatorCall)
    val baseOperatorCall = shiftExprCall.baseOperator as PowerExpr
    assertNotNull(baseOperatorCall)
    val awaitExprCall = baseOperatorCall.awaitExpr
    assertNull(awaitExprCall)
    val subBaseOperatorCall = baseOperatorCall.baseOperator
    assertNull(subBaseOperatorCall)
    val call = baseOperatorCall.primary as Call
    val arg = call.callTrailer as ArgumentList
    val arg0 = arg[0]
    assertNotNull(arg0)
  }




}