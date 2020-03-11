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

package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.*
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.boolean_expr.AndExpr
import io.github.oxisto.reticulated.ast.expression.boolean_expr.OrExpr
import io.github.oxisto.reticulated.ast.expression.boolean_expr.XorExpr
import io.github.oxisto.reticulated.ast.expression.boolean_ops.AndTest
import io.github.oxisto.reticulated.ast.expression.boolean_ops.NotTest
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.call.Call
import io.github.oxisto.reticulated.ast.expression.call.EmptyCallTrailer
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
import javax.naming.NameAlreadyBoundException
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




}