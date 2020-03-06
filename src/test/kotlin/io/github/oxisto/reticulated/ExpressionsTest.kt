/*
 * Copyright (c) 2019, Fraunhofer AISEC. All rights reserved.
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

import io.github.oxisto.reticulated.ast.expression.Call
import io.github.oxisto.reticulated.ast.expression.Identifier
import io.github.oxisto.reticulated.ast.expression.Name
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.literal.Integer
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Assert.assertNull
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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

    val s1 = input.statements[1] as StatementList
    val expr = s1.statements[0] as ExpressionStatement
    println(expr)
    val call = expr.expression as Call
    val arg = call.callTrailer as ArgumentList
    val arg0 = arg[0]
    assertNotNull(arg0)
  }

  @Test
  fun testKwargCallRef() {
    val file = File(
                  javaClass
                    .classLoader
                    .getResource("kwarg.py")!!
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
    print(expr)
    assertNotNull(expr)
    val call = expr.expression as Call
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

  @Test
  fun testComprehensionArgument() {
    // booms because it is not implemented
    val file = File(
            javaClass
                    .classLoader
                    .getResource("comprehension_argument.py")!!
                    .file
    )

    val input = PythonParser()
            .parse(file.path)
            .root

    assertNotNull(input)
    val statements = input.statements
    assertNotNull(statements)
    val statementList = statements[0] as StatementList
    assertNotNull(statementList)
    val expressionStatement = statementList[0] as ExpressionStatement
    assertNotNull(expressionStatement)
    val call = expressionStatement.expression as Call

    println(
            beautifyResult(
                    call.toString()
            )
    )

    assertNotNull(call)
    val name = call.primary as Name
    assertNotNull(name)
    assertEquals(name.name, "fun")
    val comprehension = call.callTrailer as Comprehension
    assertNotNull(comprehension)
    val expression = comprehension.expression as Name
    assertNotNull(expression)
    assertEquals(expression.name, "x")
    val compFor = comprehension.compFor
    assertNotNull(compFor)
    val targetList = compFor.targetList
    assertNotNull(targetList)
    val target1 = targetList.targets[0] as Identifier
    assertNotNull(target1)
    assertEquals(target1.name, "x")
    val target2 = targetList.targets[1] as Identifier
    assertNotNull(target2)
    assertEquals(target2.name, "y")
    val orTest = compFor.orTest
    assertNotNull(orTest)
    val subOrTest = orTest.orTest
    assertNull(subOrTest)
    val andTest = orTest.andTest
    assertNotNull(andTest)
    val subAndTest = andTest.andTest
    assertNull(subAndTest)
    val notTest = andTest.notTest
    assertNotNull(notTest)
    val subNotTest = notTest.notTest
    assertNull(subNotTest)
    val comparison = notTest.comparison
    assertNotNull(comparison)
    val comparisons = comparison.comparisons
    assertNotNull(comparisons)
    assertEquals(comparisons.size, 0)
    val orExpr = comparison.orExpr
    assertNotNull(orExpr)
    val subOrExpr = orExpr.orExpr
    assertNull(subOrExpr)
    val xorExpr = orExpr.xorExpr
    assertNotNull(xorExpr)
    val subXorExpr = xorExpr.xorExpr
    assertNull(subXorExpr)
    val andExpr = xorExpr.andExpr
    assertNotNull(andExpr)
    val subAndExpr = andExpr.andExpr
    assertNull(subAndExpr)
    val shiftExpr = andExpr.shiftExpr
    assertNotNull(shiftExpr)
    val subShiftExpr = shiftExpr.shiftExpr
    assertNull(subShiftExpr)
    val binaryOperator = shiftExpr.binaryOperator
    assertNull(binaryOperator)
    val powerExpr = shiftExpr.baseOperator as PowerExpr
    assertNotNull(powerExpr)
    val awaitExpr = powerExpr.awaitExpr
    assertNull(awaitExpr)
    val subBaseOperator = powerExpr.baseOperator
    assertNull(subBaseOperator)
    val rangeCall = powerExpr.primary as Call
    assertNotNull(rangeCall)
    val rangeCallName = rangeCall.primary as Name
    assertNotNull(rangeCallName)
    assertEquals(rangeCallName.name, "range")
    val argumentList = rangeCall.callTrailer as ArgumentList
    assertNotNull(argumentList)
    val arguments = argumentList[0]
    assertNotNull(arguments)
    val integer = arguments.expression as Integer
    assertNotNull(integer)
    assertEquals(integer.value, 10)
  }

  @Test
  fun testFullComprehensionArgument() {
    // booms because it is not implemented
    val file = File(
            javaClass
                    .classLoader
                    .getResource("full_comprehension_argument.py")!!
                    .file
    )

    val input = PythonParser()
            .parse(file.path)
            .root

    assertNotNull(input)
    val statements = input.statements
    assertNotNull(statements)
    val statementList = statements[0] as StatementList
    assertNotNull(statementList)
    val expressionStatement = statementList[0] as ExpressionStatement
    assertNotNull(expressionStatement)
    val call = expressionStatement.expression as Call

    print(
            beautifyResult(
                    call.toString()
            )
    )

    assertNotNull(call)
    val name = call.primary as Name
    assertNotNull(name)
    assertEquals(name.name, "fun")
    val comprehension = call.callTrailer as Comprehension
    assertNotNull(comprehension)
    val expression = comprehension.expression as Name
    assertNotNull(expression)
    assertEquals(expression.name, "x")
    val compFor = comprehension.compFor
    assertNotNull(compFor)
    val targetList = compFor.targetList
    assertNotNull(targetList)
    val target1 = targetList.targets[0] as Identifier
    assertNotNull(target1)
    assertEquals(target1.name, "x")
    val target2 = targetList.targets[1] as Identifier
    assertNotNull(target2)
    assertEquals(target2.name, "y")
    val orTest = compFor.orTest
    assertNotNull(orTest)
    val subOrTest = orTest.orTest
    assertNull(subOrTest)
    val andTest = orTest.andTest
    assertNotNull(andTest)
    val subAndTest = andTest.andTest
    assertNull(subAndTest)
    val notTest = andTest.notTest
    assertNotNull(notTest)
    val subNotTest = notTest.notTest
    assertNull(subNotTest)
    val comparison = notTest.comparison
    assertNotNull(comparison)
    val comparisons = comparison.comparisons
    assertNotNull(comparisons)
    assertEquals(comparisons.size, 0)
    val orExpr = comparison.orExpr
    assertNotNull(orExpr)
    val subOrExpr = orExpr.orExpr
    assertNull(subOrExpr)
    val xorExpr = orExpr.xorExpr
    assertNotNull(xorExpr)
    val subXorExpr = xorExpr.xorExpr
    assertNull(subXorExpr)
    val andExpr = xorExpr.andExpr
    assertNotNull(andExpr)
    val subAndExpr = andExpr.andExpr
    assertNull(subAndExpr)
    val shiftExpr = andExpr.shiftExpr
    assertNotNull(shiftExpr)
    val subShiftExpr = shiftExpr.shiftExpr
    assertNull(subShiftExpr)
    val binaryOperator = shiftExpr.binaryOperator
    assertNull(binaryOperator)
    val powerExpr = shiftExpr.baseOperator as PowerExpr
    assertNotNull(powerExpr)
    val awaitExpr = powerExpr.awaitExpr
    assertNull(awaitExpr)
    val subBaseOperator = powerExpr.baseOperator
    assertNull(subBaseOperator)
    val rangeCall = powerExpr.primary as Call
    assertNotNull(rangeCall)
    val rangeCallName = rangeCall.primary as Name
    assertNotNull(rangeCallName)
    assertEquals(rangeCallName.name, "range")
    val argumentList = rangeCall.callTrailer as ArgumentList
    assertNotNull(argumentList)
    val arguments = argumentList[0]
    assertNotNull(arguments)
    val integer = arguments.expression as Integer
    assertNotNull(integer)
    assertEquals(integer.value, 10)
    //TODO: furtherTests

  }

  fun beautifyResult(input: String): String{
    var result = String()
    var count = -1
    for(line in input.split(System.lineSeparator())){
      if(line.isNotEmpty() && line[0] == ')'){
        count --
      }
      val tmp = count
      while(count > 0){
        result += "\t"
        count--
      }
      count = tmp
      result += line + System.lineSeparator()
      if(!(line.isNotEmpty() && line[0] == ')')){
        count++
      }
    }
    return result
  }
}