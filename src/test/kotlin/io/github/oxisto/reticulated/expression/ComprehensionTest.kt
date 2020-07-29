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
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.comprehension.CompIf
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.booleanexpr.AndExpr
import io.github.oxisto.reticulated.ast.expression.booleanexpr.OrExpr
import io.github.oxisto.reticulated.ast.expression.booleanexpr.XorExpr
import io.github.oxisto.reticulated.ast.expression.booleanops.AndTest
import io.github.oxisto.reticulated.ast.expression.booleanops.NotTest
import io.github.oxisto.reticulated.ast.expression.primary.call.EmptyCallTrailer
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.comprehension.CompFor
import io.github.oxisto.reticulated.ast.expression.lambda.LambdaNoCond
import io.github.oxisto.reticulated.ast.expression.operator.ShiftExpr
import io.github.oxisto.reticulated.ast.simple.target.TargetList
import io.github.oxisto.reticulated.ast.statement.parameter.ParameterList
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

    val call = input.statements[0] as Call

    // println(
        // beautifyResult(
            // input.toString()
        // )
    // )

    assertNotNull(call)
    val name = call.primary as Identifier
    assertNotNull(name)
    assertEquals(name.name, "fun")
    val comprehension = call.callTrailer as Comprehension
    assertNotNull(comprehension)

    val expression = comprehension.expression  as Identifier
    assertNotNull(expression)
    assertEquals(expression.name, "x")
    val compFor = comprehension.compFor
    assertNotNull(compFor)
    assertFalse(compFor.isAsync)
    val targetList = compFor.targetList as TargetList
    assertNotNull(targetList)
    val target1 = targetList.targets[0] as Identifier
    assertNotNull(target1)
    assertEquals(target1.name, "x")
    val target2 = targetList.targets[1] as Identifier
    assertNotNull(target2)
    assertEquals(target2.name, "y")
    val rangeCall = compFor.orTest as Call
    assertNotNull(rangeCall)
    val rangeCallIdentifier = rangeCall.primary as Identifier
    assertNotNull(rangeCallIdentifier)
    assertEquals(rangeCallIdentifier.name, "range")
    val integer = rangeCall.callTrailer as Integer
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
    // print(
        // beautifyResult(
            // input.toString()
        // )
    // )
    val statements = input.statements
    assertNotNull(statements)
    val call = statements[0] as Call

    assertNotNull(call)
    val name = call.primary as Identifier
    assertNotNull(name)
    assertEquals(name.name, "fun")
    val comprehension = call.callTrailer as Comprehension
    assertNotNull(comprehension)
    val expression = comprehension.expression  as Identifier
    assertNotNull(expression)
    assertEquals(expression.name, "x")
    val compFor = comprehension.compFor
    assertNotNull(compFor)
    assertFalse(compFor.isAsync)
    val targetList = compFor.targetList as TargetList
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
    val rangeCall = compFor.orTest as Call
    assertNotNull(rangeCall)
    val rangeCallIdentifier = rangeCall.primary as Identifier
    assertNotNull(rangeCallIdentifier)
    assertEquals(rangeCallIdentifier.name, "range")
    val integer = rangeCall.callTrailer as Integer
    assertNotNull(integer)
    assertEquals(integer.value, 10)

    val compIf = compFor.compIter as CompIf
    val comparisonCI = compIf.expressionNoCond as Comparison
    assertNotNull(comparisonCI)
    val orExprIdentifier = comparisonCI.orExpr as Identifier
    assertEquals(orExprIdentifier.name, "x")

    val subComparisonCI = comparisonCI.comparisons
    assertNotNull(subComparisonCI)
    assertEquals(subComparisonCI.size, 1)
    val elem = subComparisonCI[0]
    val compOperatorCI = elem.getFirst()
    assertNotNull(compOperatorCI)
    val compOperatorCISymbol = compOperatorCI.symbol
    assertEquals(compOperatorCISymbol, "<")
    val integerCIP = elem.getSecond() as Integer
    assertNotNull(integerCIP)
    assertEquals(integerCIP.value, 7)

    val compForCF = compIf.compIter as CompFor
    assertNotNull(compForCF)
    assertFalse(compForCF.isAsync)
    val target = compForCF.targetList as Identifier
    assertNotNull(target)
    assertEquals(target.name, "u")
    val callCF = compForCF.orTest as Call
    assertNotNull(callCF)
    val nameCF = callCF.primary as Identifier
    assertNotNull(nameCF)
    assertEquals(nameCF.name, "range")
    val callTrailerCF = callCF.callTrailer as ArgumentList
    assertNotNull(callTrailerCF)
    assertEquals(callTrailerCF.count, 2)
    val firstArgExprCF = callTrailerCF[0] as Identifier
    assertNotNull(firstArgExprCF)
    assertEquals(firstArgExprCF.name, "x")
    val secondArgExprCF = callTrailerCF[1] as Identifier
    assertNotNull(secondArgExprCF)
    assertEquals(secondArgExprCF.name, "z")

    val compIFCIF = compForCF.compIter as CompIf
    assertNotNull(compIFCIF)
    val lambdaNoCondCIF = compIFCIF.expressionNoCond as LambdaNoCond
    assertNotNull(lambdaNoCondCIF)
    val parameterListCIF = lambdaNoCondCIF.parameterList
    assertNull(parameterListCIF)
    val lambdaNoCondCIFL = lambdaNoCondCIF.exprNoCond as LambdaNoCond
    assertNotNull(lambdaNoCondCIFL)
    val idOfParameterCIFL = lambdaNoCondCIFL.parameterList as Identifier
    assertNotNull(idOfParameterCIFL)
    assertEquals(idOfParameterCIFL.name, "a")
    val lambdaNoCondLL = lambdaNoCondCIFL.exprNoCond as LambdaNoCond
    assertNotNull(lambdaNoCondLL)
    val parameterListLL = lambdaNoCondLL.parameterList as ParameterList
    assertNotNull(parameterListLL)
    assertEquals(parameterListLL.count, 2)
    val idOfParameter1LL = parameterListLL[0] as Identifier
    assertNotNull(idOfParameter1LL)
    assertEquals(idOfParameter1LL.name, "b")
    val idOfParameter2LL = parameterListLL[1] as Identifier
    assertNotNull(idOfParameter2LL)
    assertEquals(idOfParameter2LL.name, "c")
    val exprNoCondSLL = lambdaNoCondLL.exprNoCond as Comparison
    val nameSLL = exprNoCondSLL.orExpr as Identifier
    assertNotNull(nameSLL)
    assertEquals(nameSLL.name, "a")
    val comparisonsSLL = exprNoCondSLL.comparisons
    assertNotNull(comparisonsSLL)
    assertEquals(comparisonsSLL.size, 4)
    val firstPair = comparisonsSLL[0]
    assertNotNull(firstPair)
    val compOperatorFP = firstPair.getFirst()
    assertNotNull(compOperatorFP)
    assertEquals(compOperatorFP.symbol, "<")
    val orExprFP = firstPair.getSecond() as OrExpr
    val nameFP0 = orExprFP.orExpr as Identifier
    assertEquals(nameFP0.name, "x")
    val nameFP = orExprFP.xorExpr as Identifier
    assertEquals(nameFP.name, "y")

    val secondPair = comparisonsSLL[1]
    assertNotNull(secondPair)
    val compOperatorSP = secondPair.getFirst()
    assertNotNull(compOperatorSP)
    assertEquals(compOperatorSP.symbol, "<=")
    val andExprSP = secondPair.getSecond() as AndExpr
    assertNotNull(andExprSP)
    val nameOfSubXorExprSP = andExprSP.andExpr as Identifier
    assertNotNull(nameOfSubXorExprSP.name, "b")
    val nameOfAndExprSP = andExprSP.shiftExpr as Identifier
    assertNotNull(nameOfAndExprSP.name, "z")
    val thirdPair = comparisonsSLL[2]
    assertNotNull(thirdPair)
    val compOperatorTP = thirdPair.getFirst()
    assertNotNull(compOperatorTP)
    assertEquals(compOperatorTP.symbol, ">=")
    val xorExprTP = thirdPair.getSecond() as XorExpr
    assertNotNull(xorExprTP)
    val cIdentifierTP = xorExprTP.xorExpr as Identifier
    assertNotNull(cIdentifierTP.name, "c")
    val aIdentifierTP = xorExprTP.andExpr as Identifier
    assertNotNull(aIdentifierTP)
    assertEquals(aIdentifierTP.name, "a")
    val forthPair = comparisonsSLL[3]
    val compOperatorFPP = forthPair.getFirst()
    assertNotNull(compOperatorFPP)
    assertEquals(compOperatorFPP.symbol, ">")
    val bIdentifier = forthPair.getSecond() as Identifier
    assertNotNull(bIdentifier.name, "b")

    val subCompIterCIF = compIFCIF.compIter as CompIf
    assertNotNull(subCompIterCIF)
    val subSubCompIterCIF = subCompIterCIF.compIter
    assertNull(subSubCompIterCIF)
    val orTestICIF = subCompIterCIF.expressionNoCond as OrTest
    assertNotNull(orTestICIF)
    val subOrTestICIF = orTestICIF.orTest as AndTest
    assertNotNull(subOrTestICIF)
    val comparisonICIF = subOrTestICIF.andTest as Comparison
    assertNotNull(comparisonICIF)
    val orExprICIF = comparisonICIF.orExpr as Identifier
    assertNotNull(orExprICIF.name, "x")

    val comparisonsICIF = comparisonICIF.comparisons
    assertNotNull(comparisonsICIF)
    assertEquals(comparisonsICIF.size, 1)
    val firstICIFPair = comparisonsICIF[0]
    assertNotNull(firstICIFPair)
    val compOperatorICIFP = firstICIFPair.getFirst()
    assertNotNull(compOperatorICIFP)
    assertEquals(compOperatorICIFP.symbol, "is")
    val iCIFPIdentifierY = firstICIFPair.getSecond() as Identifier
    assertNotNull(iCIFPIdentifierY)
    assertEquals(iCIFPIdentifierY.name, "y")

    val comparisonICIFO = subOrTestICIF.notTest as Comparison
    assertNotNull(comparisonICIFO)
    val iCIFOIdentifierZ = comparisonICIFO.orExpr as Identifier
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
    val firstCallFU = firstPairICIFO.getSecond() as Call
    assertNotNull(firstCallFU)
    val nameOfFirstCallFU = firstCallFU.primary as Identifier
    assertNotNull(nameOfFirstCallFU)
    assertEquals(nameOfFirstCallFU.name, "fun")
    val callTrailerFCFU = firstCallFU.callTrailer as ArgumentList
    assertNotNull(callTrailerFCFU)
    assertEquals(callTrailerFCFU.count, 2)
    val callFFCFU = callTrailerFCFU[0] as Call
    assertNotNull(callFFCFU)
    val nameOfCFFCFU = callFFCFU.primary as Identifier
    assertNotNull(nameOfCFFCFU)
    assertEquals(nameOfCFFCFU.name, "fun")
    val callOfCCFFCFU = callFFCFU.callTrailer as Call
    assertNotNull(callOfCCFFCFU)
    val nameOfCCFFCFU = callOfCCFFCFU.primary as Identifier
    assertNotNull(nameOfCCFFCFU)
    assertEquals(nameOfCCFFCFU.name, "fun")
    val callOfCCCFFCFU = callOfCCFFCFU.callTrailer as Call
    assertNotNull(callOfCCCFFCFU)
    val nameOfCCCFFCFU = callOfCCCFFCFU.primary as Identifier
    assertNotNull(nameOfCCCFFCFU)
    assertEquals(nameOfCCCFFCFU.name, "this_is_a_function_call")
    val callTrailerOfCCCFFCFU = callOfCCCFFCFU.callTrailer as EmptyCallTrailer
    assertNotNull(callTrailerOfCCCFFCFU)

    val callOfFCFU = callTrailerFCFU[1] as Call
    assertNotNull(callOfFCFU)
    val nameOFSFCFU = callOfFCFU.primary as Identifier
    assertNotNull(nameOFSFCFU)
    assertEquals(nameOFSFCFU.name, "this_is_a_function_call")
    val callTrailerOFSFCFU = callOfFCFU.callTrailer as EmptyCallTrailer
    assertNotNull(callTrailerOFSFCFU)

    val notTestCIF = orTestICIF.andTest as NotTest
    assertNotNull(notTestCIF)
    val subNotTestCIF = notTestCIF.notTest as NotTest
    assertNotNull(subNotTestCIF)
    val subNotTestSSCIF = notTestCIF.notTest as NotTest
    assertNotNull(subNotTestSSCIF)
    val subNotTestSSSCIF = subNotTestSSCIF.notTest as NotTest
    assertNotNull(subNotTestSSSCIF)
    val subNotTestSSSCIFF = subNotTestSSSCIF.notTest as NotTest
    assertNotNull(subNotTestSSSCIFF)
    val orExprX = subNotTestSSSCIFF.notTest as Comparison
    assertNotNull(orExprX)
    val shiftExprX = orExprX.orExpr as ShiftExpr
    assertNotNull(shiftExprX)
    val nameOfSubShiftExprX = shiftExprX.shiftExpr as Identifier
    assertNotNull(nameOfSubShiftExprX)
    assertEquals(nameOfSubShiftExprX.name, "x")
    val binaryOperatorX = shiftExprX.binaryOperator
    assertNotNull(binaryOperatorX)
    assertEquals(binaryOperatorX.symbol, ">>")
    val nameOfBaseOperatorX = shiftExprX.baseOperator as Identifier
    assertNotNull(nameOfBaseOperatorX)
    assertEquals(nameOfBaseOperatorX.name, "y")
    val comparisonsX = orExprX.comparisons
    assertNotNull(comparisonsX)
    assertEquals(comparisonsX.size, 1)
    val firstPairOfComparisonsX = comparisonsX[0]
    assertNotNull(firstPairOfComparisonsX)
    val compOperatorX = firstPairOfComparisonsX.getFirst()
    assertNotNull(compOperatorX)
    assertEquals(compOperatorX.symbol, "!=")
    val shiftExprCX = firstPairOfComparisonsX.getSecond() as ShiftExpr
    assertNotNull(shiftExprCX)
    val nameOfSCX = shiftExprCX.shiftExpr as Identifier
    assertNotNull(nameOfSCX)
    assertEquals(nameOfSCX.name, "z")
    val binaryOperatorCX = shiftExprCX.binaryOperator
    assertNotNull(binaryOperatorCX)
    assertEquals(binaryOperatorCX.symbol, "<<")
    val nameOfBCX = shiftExprCX.baseOperator as Identifier
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
        assertEquals(inputString, """FileInput(statements=[Call(
	primary=Identifier(
	name='fun'
) callTrailer=Comprehension(
	expression=Identifier(
	name='x'
) compFor=CompFor(
	"for" targetList=TargetList(
	targets=[Identifier(
	name='x'
), Identifier(
	name='y'
)]
)  "in" orTest=Call(
	primary=Identifier(
	name='range'
) callTrailer=Integer(
	value=10
)
)
)
)
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
        assertEquals(inputString, """FileInput(statements=[Call(
	primary=Identifier(
	name='fun'
) callTrailer=Comprehension(
	expression=Identifier(
	name='x'
) compFor=CompFor(
	"for" targetList=TargetList(
	targets=[Identifier(
	name='x'
), Identifier(
	name='y'
), Identifier(
	name='z'
)]
)  "in" orTest=Call(
	primary=Identifier(
	name='range'
) callTrailer=Integer(
	value=10
)
) compIter=CompIf(
	"if" expressionNoCond=Comparison(
	orExpr=Identifier(
	name='x'
) compOperator=SMALLER orEpr=Integer(
	value=7
)
) compIter=CompFor(
	"for" targetList=Identifier(
	name='u'
)  "in" orTest=Call(
	primary=Identifier(
	name='range'
) callTrailer=ArgumentList(
	arguments=[Identifier(
	name='x'
), Identifier(
	name='z'
)]
)
) compIter=CompIf(
	"if" expressionNoCond=LambdaNoCond(
	"lambda" : exprNoCond=LambdaNoCond(
	"lambda" parameterList=Identifier(
	name='a'
) : exprNoCond=LambdaNoCond(
	"lambda" parameterList=ParameterList(parameters=[Identifier(
	name='b'
), Identifier(
	name='c'
)]) : exprNoCond=Comparison(
	orExpr=Identifier(
	name='a'
) compOperator=SMALLER orEpr=OrExpr(
	orExpr=Identifier(
	name='x'
) "|" xorExpr=Identifier(
	name='y'
)
) compOperator=SMALLER_OR_EQUAL orEpr=AndExpr(
	andExpr=Identifier(
	name='b'
) "&" shiftExpr=Identifier(
	name='z'
)
) compOperator=LARGER_OR_EQUAL orEpr=XorExpr(
	xorExpr=Identifier(
	name='c'
) "^" andExpr=Identifier(
	name='a'
)
) compOperator=LARGER orEpr=Identifier(
	name='b'
)
)
)
)
) compIter=CompIf(
	"if" expressionNoCond=OrTest(
	orTest=AndTest(
	andTest=Comparison(
	orExpr=Identifier(
	name='x'
) compOperator=IS orEpr=Identifier(
	name='y'
)
) "and" notTest=Comparison(
	orExpr=Identifier(
	name='z'
) compOperator=NOT_IN orEpr=Call(
	primary=Identifier(
	name='fun'
) callTrailer=ArgumentList(
	arguments=[Call(
	primary=Identifier(
	name='fun'
) callTrailer=Call(
	primary=Identifier(
	name='fun'
) callTrailer=Call(
	primary=Identifier(
	name='this_is_a_function_call'
) callTrailer=EmptyCallTrailer( '(' ')' )
)
)
), Call(
	primary=Identifier(
	name='this_is_a_function_call'
) callTrailer=EmptyCallTrailer( '(' ')' )
)]
)
)
)
) "or" andTest=NotTest(
	"not" notTest=NotTest(
	"not" notTest=NotTest(
	"not" notTest=NotTest(
	"not" notTest=Comparison(
	orExpr=ShiftExpr(
	shiftExpr=Identifier(
	name='x'
) binaryOperator=>> additiveExpr=Identifier(
	name='y'
)
) compOperator=NOT_EQUAL orEpr=ShiftExpr(
	shiftExpr=Identifier(
	name='z'
) binaryOperator=<< additiveExpr=Identifier(
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
)])""".replace("\n", System.lineSeparator()))

    }
}