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
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class KwargTest {

  @Test
  fun testKwargCallRef() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/argument/kwarg.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    val functionDefinition = input.statements[1] as FunctionDefinition
    assertNotNull(functionDefinition)
    val parameterList = functionDefinition.parameterList
    assertNotNull(parameterList)
    val param1 = parameterList.parameters[0]
    assertNotNull(param1)
    // TODO: parameter expression is null
    // val param1Expression = param1.expression as Expression
    // assertNotNull(param1Expression)
    val param2 = parameterList.parameters[1]
    assertNotNull(param2)
    // val param2Expression = param2.expression as Expression
    // assertNotNull(param2Expression)

    val statementList = input.statements[2] as StatementList
    assertNotNull(statementList)
    val expr = statementList.statements[0] as ExpressionStatement
    assertNotNull(expr)
    val orTestCall = expr.expression as OrTest
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
    assertNotNull(call)
    val primary = call.primary
    assertNotNull(primary)
    val argList = call.callTrailer as ArgumentList
    assertNotNull(argList)
    val kwarg1 = argList[2]
    assertNotNull(kwarg1)
    val kwarg2 = argList[3]
    assertNotNull(kwarg2)

    assertNotNull(primary)
  }

    @Test fun kwargToStringTest(){
        val file = File(
            javaClass
                .classLoader
                .getResource("expressions/argument/kwarg.py")!!
                .file
        )

        val input = PythonParser()
            .parse(file.path)
            .root
        assertNotNull(input)

        val inputString = input.toString()
        assertEquals(inputString, """FileInput(statements=[FunctionDefinition(id=Identifier(
	name='func'
), parameters=ParameterList(parameters=[Parameter(
	id=Identifier(
	name='param'
)
), Parameter(
	id=Identifier(
	name='args'
)
), Parameter(
	id=Identifier(
	name='kwargs'
)
)])), FunctionDefinition(id=Identifier(
	name='fun_func'
), parameters=ParameterList(parameters=[Parameter(
	id=Identifier(
	name='args_f'
)
), Parameter(
	id=Identifier(
	name='kwargs_f'
)
)])), StatementList(
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
	name='fun_func'
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
	primary=StringLiteral(
	value=${'$'}
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
), Argument(
	expression=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=StringLiteral(
	value=%
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
), KeywordItem(
	keywordItem=(Identifier(
	name='kwargs1'
)=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=StringLiteral(
	value=test1
)
)
)
)
)
)
)
)
)
))
), KeywordItem(
	keywordItem=(Identifier(
	name='kwargs2'
)=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=StringLiteral(
	value=test2
)
)
)
)
)
)
)
)
)
))
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
    }

}