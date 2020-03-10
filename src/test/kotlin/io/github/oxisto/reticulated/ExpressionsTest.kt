/*
 * Copyright (c) 2020, Fraunhofer AISEC. All rights reserved.
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

import io.github.oxisto.reticulated.ast.expression.*
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.boolean_expr.AndExpr
import io.github.oxisto.reticulated.ast.expression.boolean_expr.OrExpr
import io.github.oxisto.reticulated.ast.expression.boolean_expr.XorExpr
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.CompOperator
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.comprehension.CompIf
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.literal.Integer
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.StatementList
import io.github.oxisto.reticulated.ast.expression.comprehension.CompFor
import io.github.oxisto.reticulated.ast.expression.operator.ShiftExpr
import org.junit.Assert.assertFalse
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

    /*
    print(
            beautifyResult(
                    call.toString()
            )
    )
    */

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
    assertFalse(compFor.isAsync)
    val targetList = compFor.targetList
    assertNotNull(targetList)
    val target1 = targetList.targets[0] as Identifier
    assertNotNull(target1)
    assertEquals(target1.name, "x")
    val target2 = targetList.targets[1] as Identifier
    assertNotNull(target2)
    assertEquals(target2.name, "y")
    val target3 = targetList.targets[2] as Identifier
    assertNotNull(target3)
    assertEquals(target3.name, "z")
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

    val compIter = compFor.compIter as CompIf
    assertNotNull(compIter)
    val expressionNoCond = compIter.expressionNoCond
    assertNotNull(expressionNoCond)
    val lambdaNoCond = expressionNoCond.lambdaNoCond
    assertNull(lambdaNoCond)
    val orTestCI = expressionNoCond.orTest as OrTest
    assertNotNull(orTestCI)
    val subOrTestCI = orTestCI.orTest
    assertNull(subOrTestCI)
    val andTestCI = orTestCI.andTest
    assertNotNull(andTestCI)
    val subAndTestCI = andTestCI.andTest
    assertNull(subAndTestCI)
    val notTestCI = andTestCI.notTest
    assertNotNull(notTestCI)
    val subNotTestCI = notTestCI.notTest
    assertNull(subNotTestCI)
    val comparisonCI = notTestCI.comparison as Comparison
    assertNotNull(comparisonCI)
    val orExprCI = comparisonCI.orExpr
    assertNotNull(orExprCI)
    val subOrExprCI = orExprCI.orExpr
    assertNull(subOrExprCI)
    val xorExprCI = orExprCI.xorExpr
    assertNotNull(xorExprCI)
    val subXorExprCI = xorExprCI.xorExpr
    assertNull(subXorExprCI)
    val andExprCI = xorExprCI.andExpr
    assertNotNull(andExprCI)
    val subAndExprCI = andExprCI.andExpr
    assertNull(subAndExprCI)
    val shiftExprCI = andExprCI.shiftExpr
    assertNotNull(shiftExpr)
    val subShiftExprCI = shiftExprCI.shiftExpr
    assertNull(subShiftExprCI)
    val binaryOperatorCI = shiftExprCI.binaryOperator
    assertNull(binaryOperatorCI)
    val baseOperatorCI = shiftExprCI.baseOperator as PowerExpr
    assertNotNull(baseOperatorCI)
    val awaitExprCI = baseOperatorCI.awaitExpr
    assertNull(awaitExprCI)
    val subBaseOperatorCI = baseOperatorCI.baseOperator
    assertNull(subBaseOperatorCI)
    val primaryCI = baseOperatorCI.primary as Primary
    assertNotNull(primaryCI)
    val nameCI = primaryCI.asIdentifier().name
    assertNotNull(nameCI)

    val subComparisonCI = comparisonCI.comparisons as ArrayList<*>
    assertNotNull(subComparisonCI)
    assertEquals(subComparisonCI.size, 1)
    val elem = subComparisonCI[0] as? Pair<CompOperator, OrExpr> ?: throw AssertionError()
    val compOperatorCI = elem.getFirst()
    assertNotNull(compOperatorCI)
    val compOperatorCISymbol = compOperatorCI.symbol
    assertEquals(compOperatorCISymbol, "<")
    val orExprCIP = elem.getSecond()
    assertNotNull(orExprCIP)
    val subOrExprCIP = orExprCIP.orExpr
    assertNull(subOrExprCIP)
    val xorExprCIP = orExprCIP.xorExpr
    assertNotNull(xorExprCIP)
    val subXorExprCIP = xorExprCIP.xorExpr
    assertNull(subXorExprCIP)
    val andExprCIP = xorExprCIP.andExpr
    assertNotNull(andExprCIP)
    val subAndExprCIP = andExprCIP.andExpr
    assertNull(subAndExprCIP)
    val shiftExprCIP = andExprCIP.shiftExpr
    assertNotNull(shiftExprCIP)
    val subShiftExprCIP = shiftExprCIP.shiftExpr
    assertNull(subShiftExprCIP)
    val binaryOperatorCIP = shiftExprCIP.binaryOperator
    assertNull(binaryOperatorCIP)
    val baseOperatorCIP = shiftExprCIP.baseOperator as PowerExpr
    assertNotNull(baseOperatorCIP)
    val awaitExprCIP = baseOperatorCIP.awaitExpr
    assertNull(awaitExprCIP)
    val subBaseOperatorCIP = baseOperatorCIP.baseOperator
    assertNull(subBaseOperatorCIP)
    val integerCIP = baseOperatorCIP.primary as Integer
    assertNotNull(integerCIP)
    assertEquals(integerCIP.value, 7)

    val compIterCF = compIter.compIter as CompFor
    assertNotNull(compIterCF)
    assertFalse(compIterCF.isAsync)
    val targets = compIterCF.targetList
    assertNotNull(targets)
    val target = targets[0] as Identifier
    assertNotNull(target)
    assertEquals(target.name, "u")
    val orTestCF = compIterCF.orTest
    assertNotNull(orTestCF)
    val subOrTestCF = orTestCF.orTest
    assertNull(subOrTestCF)
    val andTestCF = orTestCF.andTest
    assertNotNull(andTestCF)
    val subAndTestCF = andTestCF.andTest
    assertNull(subAndTestCF)
    val notTestCF = andTestCF.notTest
    assertNotNull(notTestCF)
    val subNotTestCF = notTestCF.notTest
    assertNull(subNotTestCF)
    val comparisonCF = notTestCF.comparison
    assertNotNull(comparisonCF)
    val comparisonsCF = comparisonCF.comparisons
    assertNotNull(comparisonsCF)
    assertEquals(comparisonsCF.size, 0)
    val orExprCF = comparisonCF.orExpr
    assertNotNull(orExprCF)
    val subOrExprCF = orExprCF.orExpr
    assertNull(subOrExprCF)
    val xorExprCF = orExprCF.xorExpr
    assertNotNull(xorExprCF)
    val subXorExprCF = xorExprCF.xorExpr
    assertNull(subXorExprCF)
    val andExprCF = xorExprCF.andExpr
    assertNotNull(andExprCF)
    val subAndExprCF = andExprCF.andExpr
    assertNull(subAndExprCF)
    val shiftExprCF = andExprCF.shiftExpr
    assertNotNull(shiftExprCF)
    val binaryOperatorCF = shiftExprCF.binaryOperator
    assertNull(binaryOperatorCF)
    val subShiftExprCF = shiftExprCF.shiftExpr
    assertNull(subShiftExprCF)
    val baseOperatorCF = shiftExprCF.baseOperator as PowerExpr
    assertNotNull(baseOperatorCF)
    val awaitEpxrCF = baseOperatorCF.awaitExpr
    assertNull(awaitEpxrCF)
    val subBaseOperatorCF = baseOperatorCF.baseOperator
    assertNull(subBaseOperatorCF)
    val callCF = baseOperatorCF.primary as Call
    assertNotNull(callCF)
    val nameCF = callCF.primary as Identifier
    assertNotNull(nameCF)
    assertEquals(nameCF.name, "range")
    val callTrailerCF = callCF.callTrailer as ArgumentList
    assertNotNull(callTrailerCF)
    assertEquals(callTrailerCF.count, 2)
    val firstArgCF = callTrailerCF[0]
    assertNotNull(firstArgCF)
    val firstArgExprCF = firstArgCF.expression as Identifier
    assertNotNull(firstArgExprCF)
    assertEquals(firstArgExprCF.name, "x")
    val secondArgCF = callTrailerCF[1]
    assertNotNull(secondArgCF)
    val secondArgExprCF = secondArgCF.expression as Identifier
    assertNotNull(secondArgExprCF)
    assertEquals(secondArgExprCF.name, "z")

    val compIFCIF = compIterCF.compIter as CompIf
    assertNotNull(compIFCIF)
    val subCompIterCIF = compIFCIF.compIter
    assertNull(subCompIterCIF)
    val exprNoCond = compIFCIF.expressionNoCond
    assertNotNull(exprNoCond)
    val orTestCIF = exprNoCond.orTest
    assertNull(orTestCIF)
    val lambdaNoCondCIF = exprNoCond.lambdaNoCond
    assertNotNull(lambdaNoCondCIF)
    val parameterListCIF = lambdaNoCondCIF.parameterList
    assertNull(parameterListCIF)
    val exprNoCondCIFL = lambdaNoCondCIF.exprNoCond
    assertNotNull(exprNoCondCIFL)
    val orTestCIFL = exprNoCondCIFL.orTest
    assertNull(orTestCIFL)
    val lambdaNoCondCIFL = exprNoCondCIFL.lambdaNoCond
    assertNotNull(lambdaNoCondCIFL)
    val parameterListCIFL = lambdaNoCondCIFL.parameterList
    assertNotNull(parameterListCIFL)
    assertEquals(parameterListCIFL.count, 1)
    val parameterCIFL = parameterListCIFL[0]
    assertNotNull(parameterCIFL)
    val exprOfParameterCIFL = parameterCIFL.expression
    assertNull(exprOfParameterCIFL)
    val idOfParameterCIFL = parameterCIFL.id
    assertNotNull(idOfParameterCIFL)
    assertEquals(idOfParameterCIFL.name, "a")
    val exprNoCondLL = lambdaNoCondCIFL.exprNoCond
    assertNotNull(exprNoCondLL)
    val orTestLL = exprNoCondLL.orTest
    assertNull(orTestLL)
    val lambdaNoCondLL = exprNoCondLL.lambdaNoCond
    assertNotNull(lambdaNoCondLL)
    val parameterListLL = lambdaNoCondLL.parameterList
    assertNotNull(parameterListLL)
    assertEquals(parameterListLL.count, 2)
    val parameter1LL = parameterListLL[0]
    assertNotNull(parameter1LL)
    val idOfParameter1LL = parameter1LL.id
    assertNotNull(idOfParameter1LL)
    assertEquals(idOfParameter1LL.name, "b")
    val parameter2LL = parameterListLL[1]
    assertNotNull(parameter2LL)
    val idOfParameter2LL = parameter2LL.id
    assertNotNull(idOfParameter2LL)
    assertEquals(idOfParameter2LL.name, "c")
    val exprNoCondSLL = lambdaNoCondLL.exprNoCond
    assertNotNull(exprNoCondSLL)
    val lambdaNoCondSLL = exprNoCondSLL.lambdaNoCond
    assertNull(lambdaNoCondSLL)
    val orTestSLL = exprNoCondSLL.orTest
    assertNotNull(orTestSLL)
    val subOrTestSLL = orTestSLL.orTest
    assertNull(subOrTestSLL)
    val andTestSLL = orTestSLL.andTest
    assertNotNull(andTestSLL)
    val subAndTestSLL = andTestSLL.andTest
    assertNull(subAndTestSLL)
    val notTestSLL = andTestSLL.notTest
    assertNotNull(notTestSLL)
    val subNotTestSLL = notTestSLL.notTest
    assertNull(subNotTestSLL)
    val comparisonSLL = notTestSLL.comparison
    assertNotNull(comparisonSLL)
    val orExprSLL = comparisonSLL.orExpr
    assertNotNull(orExprSLL)
    val subOrExprSLL = orExprSLL.orExpr
    assertNull(subOrExprSLL)
    val xorExprSLL = orExprSLL.xorExpr
    assertNotNull(xorExprSLL)
    val subXorExprSLL = xorExprSLL.xorExpr
    assertNull(subXorExprSLL)
    val andExprSLL = xorExprSLL.andExpr
    assertNotNull(andExprSLL)
    val subAndExprSLL = andExprSLL.andExpr
    assertNull(subAndExprSLL)
    val shiftExprSLL = andExprSLL.shiftExpr
    assertNotNull(shiftExprSLL)
    val subShiftExprSLL = shiftExprSLL.shiftExpr
    assertNull(subShiftExprSLL)
    val binaryOperatorSLL = shiftExprSLL.binaryOperator
    assertNull(binaryOperatorSLL)
    val baseOperatorSLL = shiftExprSLL.baseOperator as PowerExpr
    assertNotNull(baseOperatorSLL)
    val awaitExprSLL = baseOperatorSLL.awaitExpr
    assertNull(awaitExprSLL)
    val subBaseOperatorSLL = baseOperatorSLL.baseOperator
    assertNull(subBaseOperatorSLL)
    val nameSLL = baseOperatorSLL.primary as Name
    assertNotNull(nameSLL)
    assertEquals(nameSLL.name, "a")
    val comparisonsSLL = comparisonSLL.comparisons
    assertNotNull(comparisonsSLL)
    assertEquals(comparisonsSLL.size, 4)
    val firstPair = comparisonsSLL[0]
    assertNotNull(firstPair)
    val compOperatorFP = firstPair.getFirst()
    assertNotNull(compOperatorFP)
    assertEquals(compOperatorFP.symbol, "<")
    val orExprFP = firstPair.getSecond()
    val xorExprFP = orExprFP.orExpr as XorExpr
    val powerExprFP0 = xorExprFP
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
    assertNotNull(powerExprFP0)
    val nameFP0 = powerExprFP0.primary as Name
    assertEquals(nameFP0.name, "x")
    val powerExprFP = orExprFP
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
    assertNotNull(powerExprFP)
    val nameFP = powerExprFP.primary as Name
    assertEquals(nameFP.name, "y")
    val secondPair = comparisonsSLL[1]
    assertNotNull(secondPair)
    val compOperatorSP = secondPair.getFirst()
    assertNotNull(compOperatorSP)
    assertEquals(compOperatorSP.symbol, "<=")
    val andExprSP = secondPair
            .getSecond()
            .xorExpr
            .andExpr
    assertNotNull(andExprSP)
    val nameOfSubXorExprSP = ((
                andExprSP
                      .andExpr as ShiftExpr
              )
                    .baseOperator as PowerExpr
            ).primary as Name
    assertNotNull(nameOfSubXorExprSP.name, "b")
    val nameOfAndExprSP = (
            andExprSP
                    .shiftExpr
                    .baseOperator as PowerExpr
            ).primary as Name
    assertNotNull(nameOfAndExprSP.name, "z")
    val thirdPair = comparisonsSLL[2]
    assertNotNull(thirdPair)
    val compOperatorTP = thirdPair.getFirst()
    assertNotNull(compOperatorTP)
    assertEquals(compOperatorTP.symbol, ">=")
    val xorExprTP = thirdPair.getSecond().xorExpr
    assertNotNull(xorExprTP)
    val cNameTP = (
            (
                    xorExprTP.xorExpr as AndExpr
                    ).shiftExpr
                    .baseOperator as PowerExpr
            ).primary as Name
    assertNotNull(cNameTP.name, "c")
    val aNameTP = (
            xorExprTP
                    .andExpr
                    .shiftExpr
                    .baseOperator as PowerExpr
            ).primary as Name
    assertNotNull(aNameTP)
    assertEquals(aNameTP.name, "a")
    val forthPair = comparisonsSLL[3]
    val compOperatorFPP = forthPair.getFirst()
    assertNotNull(compOperatorFPP)
    assertEquals(compOperatorFPP.symbol, ">")
    val bName = (
            forthPair
                    .getSecond()
                    .xorExpr
                    .andExpr
                    .shiftExpr
                    .baseOperator as PowerExpr
            ).primary as Name
    assertNotNull(bName.name, "b")
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