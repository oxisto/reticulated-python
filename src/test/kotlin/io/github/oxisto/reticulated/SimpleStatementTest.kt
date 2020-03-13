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

import io.github.oxisto.reticulated.ast.expression.ConditionalExpression
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.simple.AssignmentExpression
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.simple.ImportStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SimpleStatementTest {
  @Test
  fun testParse() {
    val file = File(javaClass.classLoader.getResource("simple_stmt.py")!!.file)

    val input = PythonParser().parse(file.path).root
    assertNotNull(input)

    assertEquals(2, input.statements.size)

    val s0 = input.statements[0] as StatementList
    val assign = s0.first()
    assertTrue(assign is AssignmentExpression)
    val identifier = assign.target as Identifier
    assertEquals("i", identifier.name)

    val assigned = (
          (
              (
                  assign.expression as ConditionalExpression
                  ).orTest as OrTest
              ).andTest
              .notTest
              .comparison!!
              .orExpr
              .xorExpr
              .andExpr
              .shiftExpr
              .baseOperator as PowerExpr
        ).primary
    assertTrue(assigned is Integer)
    assertEquals(4, assigned.value)

    val s1 = input.statements[1] as StatementList
    val exprStatement = s1.first()
    assertTrue(exprStatement is ExpressionStatement)

    val conditionalExpression = exprStatement.expression as ConditionalExpression
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
    val call = baseOperatorCall.primary
    assertTrue(call is Call)
    assertEquals("print", call.primary.asIdentifier().name)
  }

  @Test
  fun testImport() {
    val file = File(javaClass.classLoader.getResource("import.py")!!.file)

    val input = PythonParser().parse(file.path).root
    assertNotNull(input)

    val s0 = input.statements[0] as StatementList
    val import = s0.first()
    assertTrue(import is ImportStatement)
    assertEquals("os", import.module.name)
  }
}