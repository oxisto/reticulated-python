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
import io.github.oxisto.reticulated.TestUtils.Companion.beautifyResult
import io.github.oxisto.reticulated.ast.expression.ConditionalExpression
import io.github.oxisto.reticulated.ast.expression.booleanops.AndTest
import io.github.oxisto.reticulated.ast.expression.booleanops.NotTest
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.ListDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.slice.*
import io.github.oxisto.reticulated.ast.expression.starred.StarredExpression
import io.github.oxisto.reticulated.ast.expression.starred.StarredItem
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.Statement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SliceTest {
  @Test
  fun testSlicingParseTree() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/slice/slice.py")!!
            .file
    )
    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
//    print(
//        beautifyResult(
//            input.toString()
//        )
//    )
    val statementList = input.statements[0] as StatementList
    assertNotNull(statementList)
    val expressionStatement = statementList.statements[0] as ExpressionStatement
    assertNotNull(expressionStatement)
    val starredExpression = expressionStatement.starredExpression
    assertNotNull(starredExpression)
    val conditionalExpression = starredExpression.expression as ConditionalExpression
    assertNotNull(conditionalExpression)
    assertNull(conditionalExpression.orTestOptional)
    assertNull(conditionalExpression.expressionOptional)
    val orTest = conditionalExpression.orTest as OrTest
    assertNotNull(orTest)
    assertNull(orTest.orTest)
    val andTest = orTest.andTest
    assertNotNull(andTest)
    assertNull(andTest.andTest)
    val notTest = andTest.notTest
    assertNotNull(notTest)
    assertNull(notTest.notTest)
    val comparison = notTest.comparison
    assertNotNull(comparison)
    assertTrue(comparison.comparisons.isEmpty())
    val orExpr = comparison.orExpr
    assertNotNull(orExpr)
    assertNull(orExpr.orExpr)
    val xorExpr = orExpr.xorExpr
    assertNotNull(xorExpr)
    assertNull(xorExpr.xorExpr)
    val andExpr = xorExpr.andExpr
    assertNotNull(andExpr)
    assertNull(andExpr.andExpr)
    val shiftExpr = andExpr.shiftExpr
    assertNotNull(shiftExpr)
    assertNull(shiftExpr.shiftExpr)
    assertNull(shiftExpr.binaryOperator)
    val powerExpr = shiftExpr.baseOperator as PowerExpr
    assertNotNull(powerExpr)
    assertNull(powerExpr.awaitExpr)
    assertNull(powerExpr.baseOperator)
    val slicing = powerExpr.primary as Slicing
    assertNotNull(slicing)
    val primary0 = slicing.primary as Slicing
    assertNotNull(primary0)
    val primary1 = primary0.primary as Slicing
    assertNotNull(primary1)
    val listDisplay = primary1.primary as ListDisplay
    assertNotNull(listDisplay)
    assertNull(listDisplay.comprehension)
    val starredList = listDisplay.starredList
    assertNotNull(starredList)
    val starredItem0 = starredList[0]
    assertNotNull(starredItem0)
    assertNull(starredItem0.orExpr)
    val starredItem0Expression = starredItem0.expression as ConditionalExpression
    assertNotNull(starredItem0Expression)
    assertNull(starredItem0Expression.expressionOptional)
    assertNull(starredItem0Expression.orTestOptional)
    val sI0OrTest = starredItem0Expression.orTest as OrTest
    assertNotNull(sI0OrTest)
    assertNull(sI0OrTest.orTest)
    val sI0AndTest = sI0OrTest.andTest
    assertNotNull(sI0AndTest)
    assertNull(sI0AndTest.andTest)
    val sI0NotTest = sI0AndTest.notTest
    assertNotNull(sI0NotTest)
    assertNull(sI0NotTest.notTest)
    val sI0Comparison = sI0NotTest.comparison
    assertNotNull(sI0Comparison)
    assertTrue(sI0Comparison.comparisons.isEmpty())
    val sI0OrExpr = sI0Comparison.orExpr
    assertNotNull(sI0OrExpr)
    assertNull(sI0OrExpr.orExpr)
    val sI0XorExpr = sI0OrExpr.xorExpr
    assertNotNull(sI0XorExpr)
    assertNull(sI0XorExpr.xorExpr)
    val sI0AndExpr = sI0XorExpr.andExpr
    assertNotNull(sI0AndExpr)
    assertNull(sI0AndExpr.andExpr)
    val sI0ShiftExpr = sI0AndExpr.shiftExpr
    assertNotNull(sI0ShiftExpr)
    assertNull(sI0ShiftExpr.shiftExpr)
    assertNull(sI0ShiftExpr.binaryOperator)
    val sI0PowerExpr = sI0ShiftExpr.baseOperator as PowerExpr
    assertNotNull(sI0PowerExpr)
    assertNull(sI0PowerExpr.awaitExpr)
    assertNull(sI0PowerExpr.baseOperator)
    val value = sI0PowerExpr.primary as Integer
    assertEquals(value.value, 1)

    val starredItem1 = starredList[1]
    assertNotNull(starredItem1)
    assertNull(starredItem1.orExpr)
    val sI1ConditionalExpression = starredItem1.expression as ConditionalExpression
    assertNotNull(sI1ConditionalExpression)
    assertNull(sI1ConditionalExpression.orTestOptional)
    assertNull(sI1ConditionalExpression.expressionOptional)
    val sI1OrTest = sI1ConditionalExpression.orTest as OrTest
    assertNotNull(sI1OrTest)
    assertNull(sI1OrTest.orTest)
    val sI1AndTest = sI1OrTest.andTest
    assertNotNull(sI1AndTest)
    assertNull(sI1AndTest.andTest)
    val sI1NotTest = sI1AndTest.notTest
    assertNotNull(sI1NotTest)
    assertNull(sI1NotTest.notTest)
    val sI1Comparison = sI1NotTest.comparison
    assertNotNull(sI1Comparison)
    assertTrue(sI1Comparison.comparisons.isEmpty())
    val sI1OrExpr = sI1Comparison.orExpr
    assertNotNull(sI1OrExpr)
    assertNull(sI1OrExpr.orExpr)
    val sI1XorExpr = sI1OrExpr.xorExpr
    assertNotNull(sI1XorExpr)
    assertNull(sI1XorExpr.xorExpr)
    val sI1AndExpr = sI1XorExpr.andExpr
    assertNotNull(sI1AndExpr)
    assertNull(sI1AndExpr.andExpr)
    val sI1ShiftExpr = sI1AndExpr.shiftExpr
    assertNotNull(sI1ShiftExpr)
    assertNull(sI1ShiftExpr.shiftExpr)
    assertNull(sI1ShiftExpr.binaryOperator)
    val sI1PowerExpr = sI1ShiftExpr.baseOperator as PowerExpr
    assertNotNull(sI1PowerExpr)
    assertNull(sI1PowerExpr.awaitExpr)
    assertNull(sI1PowerExpr.baseOperator)
    val sI1Value = sI1PowerExpr.primary as Integer
    assertEquals(sI1Value.value, 2)

    val sliceList2 = primary1.sliceList
    assertNotNull(sliceList2)
    val sliceItem2 = sliceList2[0]
    assertNotNull(sliceItem2)
    assertNull(sliceItem2.expression)
    val properSlice = sliceItem2.properSlice as ProperSlice
    assertNotNull(properSlice)
    val lowerBound2 = properSlice.lowerBound as LowerBound
    assertNotNull(lowerBound2)
    val lowerBound2Value = (
        (
            (
                (
                    lowerBound2.expression as ConditionalExpression
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
    assertEquals( lowerBound2Value.value, 0)

    val upperBound2 = properSlice.upperBound as UpperBound
    val upperBound2Value = (
        (
            (
                (
                    upperBound2.expression as ConditionalExpression
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
    assertEquals(upperBound2Value.value, 3)

    val stride2 = properSlice.stride as Stride
    assertNotNull(stride2)
    val stride2Value = (
        (
            (
                (
                    stride2.expression as ConditionalExpression
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
    assertEquals(stride2Value.value, 2)

    val sliceList1 = primary0.sliceList
    assertNotNull(sliceList1)
    val properSlice1 = sliceList1[0].properSlice
    assertNotNull(properSlice1)
    assertNull(properSlice1.lowerBound)
    assertNull(properSlice1.upperBound)
    assertNull(properSlice1.stride)

    val slicelist0 = slicing.sliceList
    assertNotNull(slicelist0)
    val sliceItem0 = slicelist0[0]
    assertNotNull(sliceItem0)
    assertNull(sliceItem0.properSlice)
    val slice0Value = (
        (
            (
                (
                    sliceItem0.expression as ConditionalExpression
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
    assertEquals(slice0Value.value, 0)
  }

  @Test
  fun testSlicingParseString () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/slice/slice.py")!!
            .file
    )
    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    assertEquals(input.toString(), """FileInput(statements=[StatementList(
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
	primary=Slicing(
	 primary=Slicing(
	 primary=Slicing(
	 primary=ListDisplay(
	 "[" starredList=StarredList(
	starredItems=[StarredItem(
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
), StarredItem(
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
	primary=Integer(
	value=2
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
) "]"
) "[" slice_list=SliceList(
	SliceItem(
	ProperSlice=ProperSlice(
	LowerBound=LowerBound(
	Expression:ConditionalExpression(
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
	value=0
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
) ":" UpperBound=UpperBound(
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
	primary=Integer(
	value=3
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
) ":" Stride(
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
	primary=Integer(
	value=2
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
)  "]"
) "[" slice_list=SliceList(
	SliceItem(
	ProperSlice=ProperSlice(
	 ":" 
)
)
)  "]"
) "[" slice_list=SliceList(
	SliceItem(
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
	primary=Integer(
	value=0
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
)  "]"
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