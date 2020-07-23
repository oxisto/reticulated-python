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
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.GeneratorExpression
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class GeneratorTest {


  @Test
  fun testGeneratorExpression () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/generator_expression.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    print(
        // beautifyResult(
            input.toString()
        // )
    )
    val generatorExpression = (
        (
            (
                (
                    (
                        (
                            input.statements[0] as StatementList
                            ).statements[0] as ExpressionStatement
                        ).starredExpression
                        .expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as GeneratorExpression
    val condIdentifier = (
        (
            (
                (
                    generatorExpression.expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Identifier
    assertEquals(condIdentifier.name, "x")
    val compFor = generatorExpression.compFor
    assertFalse(compFor.isAsync)
    val targetIdentifier = compFor.targetList[0] as Identifier
    assertEquals(targetIdentifier.name, "x")
    val call = (
        (
            compFor.orTest
                .andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Call
    assertNotNull(call)
    val name = call.primary as Identifier
    assertEquals(name.name, "range")
    val arg = (
        (
            (
                (
                    (
                        call.callTrailer as ArgumentList
                        )[0].expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Integer
    assertEquals(arg.value, 10)
  }

  @Test fun generatorToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/generator_expression.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    val inputString = input.toString()
    // print(beautifyResult(inputString))
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
	primary=GeneratorExpression(
	 "(" expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='x'
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
) compFor=CompFor(
	compFor=for targetList=TargetList(
	targets=[Identifier(
	name='x'
)]
) in orTest=OrTest(
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
	name='range'
) callTrailer=ArgumentList(
	argument=[Argument(
	expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Integer(
	value=10
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
) ")"
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