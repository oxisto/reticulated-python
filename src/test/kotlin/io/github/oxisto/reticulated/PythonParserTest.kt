/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
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
package io.github.oxisto.reticulated

import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.StringLiteral
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.parameter.Parameter
import io.github.oxisto.reticulated.ast.statement.parameter.Parameters
import java.io.File
import kotlin.test.*


class PythonParserTest {
  @Test
  fun testMain() {
    val classUnderTest = PythonParser()
    val file = File(
        javaClass
            .classLoader
            .getResource("main.py")!!
            .file
    )

    val input = classUnderTest.parse(file.path).root
    assertNotNull(input)

    // print(input.toString())

    // first function without arguments
    val func1 = input.statements[0]
    assertTrue(func1 is FunctionDefinition)
    assertNull(func1.annotation)
    assertEquals("func_no_arguments", func1.funcName.name)
    assertEquals(0, (func1.parameters as Parameters).count)
    var suite = func1.suite

    var expr = suite.first()
    assertTrue(expr is ExpressionStatement)

    var call = expr.expression
    assertTrue(call is Call)

    val callName = call.primary as Identifier
    assertEquals(callName.name, "print")

    val arg = call.arguments.firstOrNull() as StringLiteral
    assertEquals(arg.value, "test")

    // second function with one argument
    val func2 = input.statements[1]
    assertTrue(func2 is FunctionDefinition)
    assertNull(func2.annotation)
    assertEquals("func_one_argument", func2.funcName.name)

    val param = func2.parameters.firstOrNull() as Parameter
    assertEquals(param.id.name, "i")

    suite = func2.suite
    expr = suite.first()
    assertTrue(expr is ExpressionStatement)

    call = expr.expression as Call

    val callName2 = call.primary as Identifier
    assertEquals(callName2.name, "print")

    val param2 = call.arguments.first() as Identifier
    assertEquals(param2.name, "i")

    // third function wit two arguments
    val func3 = input.statements[2]
    assertTrue(func3 is FunctionDefinition)
    assertNull(func3.annotation)
    assertEquals("func_two_arguments", func3.funcName.name)
    val parameter3 = func3.parameters
    val firstParameter = parameter3[0]
    assertEquals(firstParameter.id.name, "i")
    val secondParameter = parameter3[1]
    assertEquals(secondParameter.id.name, "j")
    suite = func3.suite

    val firstCall = (suite[0] as ExpressionStatement).expression as Call
    val firstCallName = firstCall.primary as Identifier
    assertEquals(firstCallName.name, "print")
    val firstCallParam = firstCall.arguments.firstOrNull() as Identifier
    assertEquals(firstCallParam.name, "i")
    val secondCall = (suite[1] as ExpressionStatement).expression as Call
    val secondCallName = secondCall.primary as Identifier
    assertEquals(secondCallName.name, "print")
    val secondCallParam = secondCall.arguments.firstOrNull() as Identifier
    assertEquals(secondCallParam.name, "j")
  }

  @Test
  @Ignore
  fun testTypeHintFunction() {
    val classUnderTest = PythonParser()
    val file = File(
        javaClass
            .classLoader
            .getResource("hint.py")!!
            .file
    )

    val input = classUnderTest.parse(file.path)

    assertNotNull(input)
  }

  @Test
  fun testTypeSolve() {
    val classUnderTest = PythonParser()
    val file = File(
        javaClass
            .classLoader
            .getResource("solve.py")!!
            .file
    )

    val input = classUnderTest.parse(file.path).root

    // print(input)

    val func = input.statements[0]
    assertTrue(func is FunctionDefinition)
    assertEquals(func.funcName.name, "func")

    val param = func.parameters.firstOrNull() as Parameter
    assertEquals(param.id.name, "i")

    // get the first statement of the suite
    val suite = func.suite
    val call = (suite.statements.first() as ExpressionStatement).expression as Call

    val callName = call.primary as Identifier
    assertEquals(callName.name, "print")

    val name = call.arguments.first()
    assertTrue(name is Identifier)
    assertEquals("i", name.name)
  }
}
