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
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.Primary
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.comprehension.CompIf
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.booleanexpr.AndExpr
import io.github.oxisto.reticulated.ast.expression.booleanexpr.XorExpr
import io.github.oxisto.reticulated.ast.expression.booleanops.AndTest
import io.github.oxisto.reticulated.ast.expression.booleanops.NotTest
import io.github.oxisto.reticulated.ast.expression.primary.call.EmptyCallTrailer
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.operator.ShiftExpr
import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ComprehensionTest {

  @Test
  fun testComprehensionArgument() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/comprehension/comprehension_argument.py")!!
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
    val conditionalExpression = expressionStatement.starredExpression.expression as ConditionalExpression
    assertNotNull(conditionalExpression)
    val orTestCall = conditionalExpression.orTest as OrTest
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
    /*
    println(
        beautifyResult(
            call.toString()
        )
    )
  */
    assertNotNull(call)
    val name = call.primary as Identifier
    assertNotNull(name)
    assertEquals(name.name, "fun")
    val comprehension = call.callTrailer as Comprehension
    assertNotNull(comprehension)

    val expression = (
        (
            (
                (
                    comprehension.expression as ConditionalExpression
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
    val rangeCallIdentifier = rangeCall.primary as Identifier
    assertNotNull(rangeCallIdentifier)
    assertEquals(rangeCallIdentifier.name, "range")
    val argumentList = rangeCall.callTrailer as ArgumentList
    assertNotNull(argumentList)
    val arguments = argumentList[0]
    assertNotNull(arguments)
    val integer = (
        (
            (
                (
                    arguments.expression as ConditionalExpression
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
    assertNotNull(integer)
    assertEquals(integer.value, 10)

  }

  @Test
  fun testFullComprehensionArgument() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/comprehension/full_comprehension_argument.py")!!
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
    val conditionalExpression = expressionStatement.starredExpression.expression as ConditionalExpression
    assertNotNull(conditionalExpression)
    val orTestCall = conditionalExpression.orTest as OrTest
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

    /*
    print(
        beautifyResult(
            call.toString()
        )
    )
    */

    assertNotNull(call)
    val name = call.primary as Identifier
    assertNotNull(name)
    assertEquals(name.name, "fun")
    val comprehension = call.callTrailer as Comprehension
    assertNotNull(comprehension)
    val expression = (
        (
            (
                (
                    comprehension.expression as ConditionalExpression
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
    val rangeCallIdentifier = rangeCall.primary as Identifier
    assertNotNull(rangeCallIdentifier)
    assertEquals(rangeCallIdentifier.name, "range")
    val argumentList = rangeCall.callTrailer as ArgumentList
    assertNotNull(argumentList)
    val arguments = argumentList[0]
    assertNotNull(arguments)
    val integer = (
        (
            (
                (
                    arguments.expression as ConditionalExpression
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
    assertNotNull(integer)
    assertEquals(integer.value, 10)

    val compIter = compFor.compIter
    assertNotNull(compIter)
    val compIf = compIter.compIf
    assertNotNull(compIf)
    val expressionNoCond = compIf.expressionNoCond
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

    val subComparisonCI = comparisonCI.comparisons
    assertNotNull(subComparisonCI)
    assertEquals(subComparisonCI.size, 1)
    val elem = subComparisonCI[0]
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

    val compIterFC = compIf.compIter
    assertNotNull(compIterFC)
    val compForCF = compIterFC.compFor
    assertNotNull(compForCF)
    assertFalse(compForCF.isAsync)
    val targets = compForCF.targetList
    assertNotNull(targets)
    val target = targets[0] as Identifier
    assertNotNull(target)
    assertEquals(target.name, "u")
    val orTestCF = compForCF.orTest
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
    val firstArgExprCF = (
        (
            (
                (
                    firstArgCF.expression as ConditionalExpression
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
    assertNotNull(firstArgExprCF)
    assertEquals(firstArgExprCF.name, "x")
    val secondArgCF = callTrailerCF[1]
    assertNotNull(secondArgCF)
    val secondArgExprCF = (
        (
            (
                (
                    secondArgCF.expression as ConditionalExpression
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
    assertNotNull(secondArgExprCF)
    assertEquals(secondArgExprCF.name, "z")

    val compIterIFCIF = compForCF.compIter
    assertNotNull(compIterIFCIF)
    val compIFCIF = compIterIFCIF.compIf as CompIf
    assertNotNull(compIFCIF)
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
    val nameSLL = baseOperatorSLL.primary as Identifier
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
    val nameFP0 = powerExprFP0.primary as Identifier
    assertEquals(nameFP0.name, "x")
    val powerExprFP = orExprFP
        .xorExpr
        .andExpr
        .shiftExpr
        .baseOperator as PowerExpr
    assertNotNull(powerExprFP)
    val nameFP = powerExprFP.primary as Identifier
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
        ).primary as Identifier
    assertNotNull(nameOfSubXorExprSP.name, "b")
    val nameOfAndExprSP = (
        andExprSP
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Identifier
    assertNotNull(nameOfAndExprSP.name, "z")
    val thirdPair = comparisonsSLL[2]
    assertNotNull(thirdPair)
    val compOperatorTP = thirdPair.getFirst()
    assertNotNull(compOperatorTP)
    assertEquals(compOperatorTP.symbol, ">=")
    val xorExprTP = thirdPair.getSecond().xorExpr
    assertNotNull(xorExprTP)
    val cIdentifierTP = (
        (
            xorExprTP.xorExpr as AndExpr
            ).shiftExpr
            .baseOperator as PowerExpr
        ).primary as Identifier
    assertNotNull(cIdentifierTP.name, "c")
    val aIdentifierTP = (
        xorExprTP
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Identifier
    assertNotNull(aIdentifierTP)
    assertEquals(aIdentifierTP.name, "a")
    val forthPair = comparisonsSLL[3]
    val compOperatorFPP = forthPair.getFirst()
    assertNotNull(compOperatorFPP)
    assertEquals(compOperatorFPP.symbol, ">")
    val bIdentifier = (
        forthPair
            .getSecond()
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Identifier
    assertNotNull(bIdentifier.name, "b")

    val compIterCIF = compIFCIF.compIter
    assertNotNull(compIterCIF)
    val subCompIterCIF = compIterCIF.compIf as CompIf
    assertNotNull(subCompIterCIF)
    val subSubCompIterCIF = subCompIterCIF.compIter
    assertNull(subSubCompIterCIF)
    val expressionNoCondICIF = subCompIterCIF.expressionNoCond
    assertNotNull(expressionNoCondICIF)
    val lambdaNoCondICIF = expressionNoCondICIF.lambdaNoCond
    assertNull(lambdaNoCondICIF)
    val orTestICIF = expressionNoCondICIF.orTest
    assertNotNull(orTestICIF)
    val subOrTestICIF = orTestICIF.orTest as AndTest
    assertNotNull(subOrTestICIF)
    val andTestICIF = subOrTestICIF.andTest as NotTest
    assertNotNull(andTestICIF)
    val notTestICIF = andTestICIF.notTest
    assertNull(notTestICIF)
    val comparisonICIF = andTestICIF.comparison
    assertNotNull(comparisonICIF)
    val orExprICIF = comparisonICIF.orExpr
    assertNotNull(orExprICIF)
    val subOrExprICIF = orExprICIF.orExpr
    assertNull(subOrExprICIF)
    val xorExprICIF = orExprICIF.xorExpr
    assertNotNull(xorExprICIF)
    val subXorExprICIF = xorExprICIF.xorExpr
    assertNull(subXorExprICIF)
    val andExprICIF = xorExprICIF.andExpr
    assertNotNull(andExprICIF)
    val subAndExprICIF = andExprICIF.andExpr
    assertNull(subAndExprICIF)
    val shiftExprICIF = andExprICIF.shiftExpr
    assertNotNull(shiftExprICIF)
    val subShiftExprICIF = shiftExprICIF.shiftExpr
    assertNull(subShiftExprICIF)
    val binaryOperatorICIF = shiftExprICIF.binaryOperator
    Assert.assertNull(binaryOperatorICIF)
    val baseOperatorICIF = shiftExprICIF.baseOperator as PowerExpr
    assertNotNull(baseOperatorICIF)
    val awaitExprICIF = baseOperatorICIF.awaitExpr
    assertNull(awaitExprICIF)
    val subBaseOperatorICIF = baseOperatorICIF.baseOperator
    assertNull(subBaseOperatorICIF)
    val iCIFIdentifier = baseOperatorICIF.primary as Identifier
    assertNotNull(iCIFIdentifier.name, "x")

    val comparisonsICIF = comparisonICIF.comparisons
    assertNotNull(comparisonsICIF)
    assertEquals(comparisonsICIF.size, 1)
    val firstICIFPair = comparisonsICIF[0]
    assertNotNull(firstICIFPair)
    val compOperatorICIFP = firstICIFPair.getFirst()
    assertNotNull(compOperatorICIFP)
    assertEquals(compOperatorICIFP.symbol, "is")
    val orExprICIFP = firstICIFPair.getSecond()
    assertNotNull(orExprICIFP)
    val subOrExprICIFP = orExprICIFP.orExpr
    assertNull(subOrExprICIFP)
    val xorExprICIFP = orExprICIFP.xorExpr
    assertNotNull(xorExprICIFP)
    val subXorExprICIFP = xorExprICIFP.xorExpr
    assertNull(subXorExprICIFP)
    val andExprICIFP = xorExprICIFP.andExpr
    assertNotNull(andExprICIFP)
    val subAndExprICIFP = andExprICIFP.andExpr
    assertNull(subAndExprICIFP)
    val shiftExprICIFP = andExprICIFP.shiftExpr
    assertNotNull(shiftExprICIFP)
    val subShiftExprICIFP = shiftExprICIFP.shiftExpr
    assertNull(subShiftExprICIFP)
    val binaryOperatorICIFP = shiftExprICIFP.binaryOperator
    assertNull(binaryOperatorICIFP)
    val baseOperatorICIFP = shiftExprICIFP.baseOperator as PowerExpr
    assertNotNull(baseOperatorICIFP)
    val awaitExprICIFP = baseOperatorICIFP.awaitExpr
    assertNull(awaitExprICIFP)
    val subBaseOperatorICIFP = baseOperatorICIFP.baseOperator
    assertNull(subBaseOperatorICIFP)
    val iCIFPIdentifierY = baseOperatorICIFP.primary as Identifier
    assertNotNull(iCIFPIdentifierY)
    assertEquals(iCIFPIdentifierY.name, "y")

    val notTestICIFO = subOrTestICIF.notTest
    assertNotNull(notTestICIFO)
    val subNotTestICIFO = notTestICIFO.notTest
    assertNull(subNotTestICIFO)
    val comparisonICIFO = notTestICIFO.comparison
    assertNotNull(comparisonICIFO)
    val orExprICIFO = comparisonICIFO.orExpr
    assertNotNull(orExprICIFO)
    val subOrExprICIFO = orExprICIFO.orExpr
    assertNull(subOrExprICIFO)
    val xorExprICIFO = orExprICIFO.xorExpr
    assertNotNull(xorExprICIFO)
    val subXorExprICIFO = xorExprICIFO.xorExpr
    assertNull(subXorExprICIFO)
    val andExprICIFO = xorExprICIFO.andExpr
    assertNotNull(andExprICIFO)
    val subAndExprICIFO = andExprICIFO.andExpr
    assertNull(subAndExprICIFO)
    val shiftExprICIFO = andExprICIFO.shiftExpr
    assertNotNull(shiftExprICIFO)
    val subShiftExprICIFO = shiftExprICIFO.shiftExpr
    assertNull(subShiftExprICIFO)
    val binaryOperatorICIFO = shiftExprICIFO.binaryOperator
    assertNull(binaryOperatorICIFO)
    val baseOperatorICIFO = shiftExprICIFO.baseOperator as PowerExpr
    assertNotNull(baseOperatorICIFO)
    val awaitExprICIFO = baseOperatorICIFO.awaitExpr
    assertNull(awaitExprICIFO)
    val subBaseOperatorICIFO = baseOperatorICIFO.baseOperator
    assertNull(subBaseOperatorICIFO)
    val iCIFOIdentifierZ = baseOperatorICIFO.primary as Identifier
    assertNotNull(iCIFOIdentifierZ)
    assertEquals(iCIFOIdentifierZ.name, "z")

    val comparisonsICIFO = comparisonICIFO.comparisons
    assertNotNull(comparisonsICIFO)
    assertEquals(comparisonsICIFO.size, 1)
    val firstPairICIFO = comparisonsICIFO[0]
    assertNotNull(firstPairICIFO)
    val compOperatorICIFO = firstPairICIFO.getFirst()
    assertNotNull(compOperatorICIFO)
    assertEquals(compOperatorICIFO.symbol, "not in")
    val orExprFU = firstPairICIFO.getSecond()
    assertNotNull(orExprFU)
    val subOrExprFU = orExprFU.orExpr
    assertNull(subOrExprFU)
    val xorExprFU = orExprFU.xorExpr
    assertNotNull(xorExprFU)
    val subXorExprFU = xorExprFU.xorExpr
    assertNull(subXorExprFU)
    val andExprFU = xorExprFU.andExpr
    assertNotNull(andExprFU)
    val subAndExprFU = andExprFU.andExpr
    assertNull(subAndExprFU)
    val shiftExprFU = andExprFU.shiftExpr
    assertNotNull(shiftExprFU)
    val subShiftExprFU = shiftExprFU.shiftExpr
    assertNull(subShiftExprFU)
    val binaryOperatorFU = shiftExprFU.binaryOperator
    assertNull(binaryOperatorFU)
    val baseOperatorFU = shiftExprFU.baseOperator as PowerExpr
    assertNotNull(baseOperatorFU)
    val awaitExprFU = baseOperatorFU.awaitExpr
    assertNull(awaitExprFU)
    val subBaseOperatorFU = baseOperatorFU.baseOperator
    assertNull(subBaseOperatorFU)
    val firstCallFU = baseOperatorFU.primary as Call
    assertNotNull(firstCallFU)
    val nameOfFirstCallFU = firstCallFU.primary as Identifier
    assertNotNull(nameOfFirstCallFU)
    assertEquals(nameOfFirstCallFU.name, "fun")
    val callTrailerFCFU = firstCallFU.callTrailer as ArgumentList
    assertNotNull(callTrailerFCFU)
    assertEquals(callTrailerFCFU.count, 2)
    val firstArgumentFCFU = callTrailerFCFU[0]
    assertNotNull(firstArgumentFCFU)
    val callFFCFU = (
        (
            (
                (
                    firstArgumentFCFU.expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
    ).primary as Call
    assertNotNull(callFFCFU)
    val nameOfCFFCFU = callFFCFU.primary as Identifier
    assertNotNull(nameOfCFFCFU)
    assertEquals(nameOfCFFCFU.name, "fun")
    val callTrailerCFFCFU = callFFCFU.callTrailer as ArgumentList
    assertNotNull(callTrailerCFFCFU)
    assertEquals(callTrailerCFFCFU.count, 1)
    val firstArgumentCFFCFU = callTrailerCFFCFU[0]
    assertNotNull(firstArgumentCFFCFU)
    val callOfCCFFCFU = (
        (
            (
                (
                    firstArgumentCFFCFU.expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
    ).primary as Call
    assertNotNull(callOfCCFFCFU)
    val nameOfCCFFCFU = callOfCCFFCFU.primary as Identifier
    assertNotNull(nameOfCCFFCFU)
    assertEquals(nameOfCCFFCFU.name, "fun")
    val trailerOfCCFFCFU = callOfCCFFCFU.callTrailer as ArgumentList
    assertNotNull(trailerOfCCFFCFU)
    assertEquals(trailerOfCCFFCFU.count, 1)
    val firstArgumentOFCCFFCFU = trailerOfCCFFCFU[0]
    assertNotNull(firstArgumentOFCCFFCFU)
    val callOfCCCFFCFU = (
        (
            (
                (
                    firstArgumentOFCCFFCFU.expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
    ).primary as Call
    assertNotNull(callOfCCCFFCFU)
    val nameOfCCCFFCFU = callOfCCCFFCFU.primary as Identifier
    assertNotNull(nameOfCCCFFCFU)
    assertEquals(nameOfCCCFFCFU.name, "this_is_a_function_call")
    val callTrailerOfCCCFFCFU = callOfCCCFFCFU.callTrailer as EmptyCallTrailer
    assertNotNull(callTrailerOfCCCFFCFU)

    val secondArgumentFCFU = callTrailerFCFU[1]
    assertNotNull(secondArgumentFCFU)
    val callOfFCFU = (
        (
            (
                (
                    secondArgumentFCFU.expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
    ).primary as Call
    assertNotNull(callOfFCFU)
    val nameOFSFCFU = callOfFCFU.primary as Identifier
    assertNotNull(nameOFSFCFU)
    assertEquals(nameOFSFCFU.name, "this_is_a_function_call")
    val callTrailerOFSFCFU = callOfFCFU.callTrailer as EmptyCallTrailer
    assertNotNull(callTrailerOFSFCFU)

    val andTestCIF = orTestICIF.andTest
    assertNotNull(andTestCIF)
    val subAndTestCIF = andTestCIF.andTest
    assertNull(subAndTestCIF)
    val notTestCIF = andTestCIF.notTest
    assertNotNull(notTestCIF)
    val comparisonCIF = notTestCIF.comparison
    assertNull(comparisonCIF)
    val subNotTestCIF = notTestCIF.notTest
    assertNotNull(subNotTestCIF)
    val comparisonSCIF = subNotTestCIF.comparison
    assertNull(comparisonSCIF)
    val subNotTestSCIF = subNotTestCIF.notTest
    assertNotNull(subNotTestSCIF)
    val comparisonSSCIF = subNotTestSCIF.comparison
    assertNull(comparisonSSCIF)
    val subNotTestSSCIF = subNotTestSCIF.notTest
    assertNotNull(subNotTestSSCIF)
    val comparisonSSSCIF = subNotTestSSCIF.comparison
    assertNull(comparisonSSSCIF)
    val subNotTestSSSCIF = subNotTestSSCIF.notTest
    assertNotNull(subNotTestSSSCIF)
    val comparisonX = subNotTestSSSCIF.comparison
    assertNotNull(comparisonX)
    val subNotTestSSSSCIF = subNotTestSSSCIF.notTest
    assertNull(subNotTestSSSSCIF)
    val orExprX = comparisonX.orExpr
    assertNotNull(orExprX)
    val subOrExprX = orExprX.orExpr
    assertNull(subOrExprX)
    val xorExprX = orExprX.xorExpr
    assertNotNull(xorExprX)
    val subXorExprX = xorExprX.xorExpr
    assertNull(subXorExprX)
    val andExprX = xorExprX.andExpr
    assertNotNull(andExprX)
    val subAndExprX = andExprX.andExpr
    assertNull(subAndExprX)
    val shiftExprX = andExprX.shiftExpr
    assertNotNull(shiftExprX)
    val subShiftExprX = shiftExprX.shiftExpr as PowerExpr
    assertNotNull(subShiftExprX)
    val awaitExprX = subShiftExprX.awaitExpr
    assertNull(awaitExprX)
    val baseOperatorSSHX = subShiftExprX.baseOperator
    assertNull(baseOperatorSSHX)
    val nameOfSubShiftExprX = subShiftExprX.primary as Identifier
    assertNotNull(nameOfSubShiftExprX)
    assertEquals(nameOfSubShiftExprX.name, "x")
    val binaryOperatorX = shiftExprX.binaryOperator
    assertNotNull(binaryOperatorX)
    assertEquals(binaryOperatorX.symbol, ">>")
    val baseOperatorX = shiftExprX.baseOperator as PowerExpr
    assertNotNull(baseOperatorX)
    val awaitExprBX = baseOperatorX.awaitExpr
    assertNull(awaitExprBX)
    val subBaseOperatorX = baseOperatorX.baseOperator
    assertNull(subBaseOperatorX)
    val nameOfBaseOperatorX = baseOperatorX.primary as Identifier
    assertNotNull(nameOfBaseOperatorX)
    assertEquals(nameOfBaseOperatorX.name, "y")
    val comparisonsX = comparisonX.comparisons
    assertNotNull(comparisonsX)
    assertEquals(comparisonsX.size, 1)
    val firstPairOfComparisonsX = comparisonsX[0]
    assertNotNull(firstPairOfComparisonsX)
    val compOperatorX = firstPairOfComparisonsX.getFirst()
    assertNotNull(compOperatorX)
    assertEquals(compOperatorX.symbol, "!=")
    val orExprCX = firstPairOfComparisonsX.getSecond()
    assertNotNull(orExprCX)
    val subOrExprCX = orExprCX.orExpr
    assertNull(subOrExprCX)
    val xorExprCX = orExprCX.xorExpr
    assertNotNull(xorExprCX)
    val subXorExprCX = xorExprCX.xorExpr
    assertNull(subXorExprCX)
    val andExprCX = xorExprCX.andExpr
    assertNotNull(andExprCX)
    val subAndExprCS = andExprCX.andExpr
    assertNull(subAndExprCS)
    val shiftExprCX = andExprCX.shiftExpr
    assertNotNull(shiftExprCX)
    val subShiftExprCX = shiftExprCX.shiftExpr as PowerExpr
    assertNotNull(subShiftExprCX)
    val awaitExprSCX = subShiftExprCX.awaitExpr
    assertNull(awaitExprSCX)
    val baseOperatorSCX  = subShiftExprCX.baseOperator
    assertNull(baseOperatorSCX)
    val nameOfSCX = subShiftExprCX.primary as Identifier
    assertNotNull(nameOfSCX)
    assertEquals(nameOfSCX.name, "z")
    val binaryOperatorCX = shiftExprCX.binaryOperator
    assertNotNull(binaryOperatorCX)
    assertEquals(binaryOperatorCX.symbol, "<<")
    val baseOperatorCX = shiftExprCX.baseOperator as PowerExpr
    assertNotNull(baseOperatorCX)
    val awaitExprCX = baseOperatorCX.awaitExpr
    assertNull(awaitExprCX)
    val subBaseOperatorCX = baseOperatorCX.baseOperator
    assertNull(subBaseOperatorCX)
    val nameOfBCX = baseOperatorCX.primary as Identifier
    assertNotNull(nameOfBCX)
    assertEquals(nameOfBCX.name, "x")
  }

    @Test fun comprehensionArgumentToStringTest(){
        val file = File(
            javaClass
                .classLoader
                .getResource("expressions/comprehension/comprehension_argument.py")!!
                .file
        )

        val input = PythonParser()
            .parse(file.path)
            .root

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
	primary=Call(
	primary=Identifier(
	name='fun'
) callTrailer=Comprehension(
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
), Identifier(
	name='y'
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
)])""".replace("\n", System.lineSeparator()))
    }
    @Test fun fullComprehensionArgumentToStringTest(){
        val file = File(
            javaClass
                .classLoader
                .getResource("expressions/comprehension/full_comprehension_argument.py")!!
                .file
        )

        val input = PythonParser()
            .parse(file.path)
            .root

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
	primary=Call(
	primary=Identifier(
	name='fun'
) callTrailer=Comprehension(
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
), Identifier(
	name='y'
), Identifier(
	name='z'
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
) compIter=CompIter(
	compIf=CompIf(
	compIf=if expressionNoCond=ExpressionNoCond(
	ortest=OrTest(
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
) compOperator=SMALLER orEpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Integer(
	value=7
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
) compIter=CompIter(
	compFor=CompFor(
	compFor=for targetList=TargetList(
	targets=[Identifier(
	name='u'
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
)
), Argument(
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
	primary=Identifier(
	name='z'
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
) compIter=CompIter(
	compIf=CompIf(
	compIf=if expressionNoCond=ExpressionNoCond(
	lambdaNoCond=LambdaNoCond(
	lambdaNoCond=lambda : exprNoCond=ExpressionNoCond(
	lambdaNoCond=LambdaNoCond(
	lambdaNoCond=lambda parameterList=ParameterList(parameters=[Parameter(
	id=Identifier(
	name='a'
)
)]) : exprNoCond=ExpressionNoCond(
	lambdaNoCond=LambdaNoCond(
	lambdaNoCond=lambda parameterList=ParameterList(parameters=[Parameter(
	id=Identifier(
	name='b'
)
), Parameter(
	id=Identifier(
	name='c'
)
)]) : exprNoCond=ExpressionNoCond(
	ortest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='a'
)
)
)
)
)
) compOperator=SMALLER orEpr=OrExpr(
	orExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='x'
)
)
)
)
) | xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='y'
)
)
)
)
)
) compOperator=SMALLER_OR_EQUAL orEpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	andExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='b'
)
)
) & shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='z'
)
)
)
)
)
) compOperator=LARGER_OR_EQUAL orEpr=OrExpr(
	xorExpr=XorExpr(
	xorExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='c'
)
)
)
) ^ andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='a'
)
)
)
)
)
) compOperator=LARGER orEpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='b'
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
) compIter=CompIter(
	compIf=CompIf(
	compIf=if expressionNoCond=ExpressionNoCond(
	ortest=OrTest(
	orTest=AndTest(
	andTest=NotTest(
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
) compOperator=IS orEpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='y'
)
)
)
)
)
)
)
) and notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='z'
)
)
)
)
)
) compOperator=NOT_IN orEpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Call(
	primary=Identifier(
	name='fun'
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
	primary=Call(
	primary=Identifier(
	name='fun'
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
	primary=Call(
	primary=Identifier(
	name='fun'
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
	primary=Call(
	primary=Identifier(
	name='this_is_a_function_call'
) callTrailer=EmptyCallTrailer( '(' ')' )
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
)
), Argument(
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
	primary=Call(
	primary=Identifier(
	name='this_is_a_function_call'
) callTrailer=EmptyCallTrailer( '(' ')' )
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
) or andTest=AndTest(
	notTest=NotTest(
	not notTest=NotTest(
	not notTest=NotTest(
	not notTest=NotTest(
	not notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	shiftExpr=PowerExpr(
	primary=Identifier(
	name='x'
)
) binaryOperator=>> additiveExpr=PowerExpr(
	primary=Identifier(
	name='y'
)
)
)
)
)
) compOperator=NOT_EQUAL orEpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	shiftExpr=PowerExpr(
	primary=Identifier(
	name='z'
)
) binaryOperator=<< additiveExpr=PowerExpr(
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