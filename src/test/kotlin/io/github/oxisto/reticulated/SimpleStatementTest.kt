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

import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.AttributeRef
import io.github.oxisto.reticulated.ast.simple.AssignmentStatement
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.simple.ImportStatement
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SimpleStatementTest {
  @Test
  fun testParse() {
    val file = File(javaClass.classLoader.getResource("simple_stmt.py")!!.file)

    val input = PythonParser().parse(file.path).root
    assertNotNull(input)
    assertEquals(2, input.statements.size)

    // println(input.toString())

    val assign = input.statements.firstOrNull()
    assertTrue(assign is AssignmentStatement)

    val identifier = assign.targets.firstOrNull() as Identifier
    assertEquals("i", identifier.name)

    val assigned = assign.expression
    assertTrue(assigned is Integer)
    assertEquals(4, assigned.value)

    val expr = input.statements[1]
    assertTrue(expr is ExpressionStatement)

    val call = expr.expression
    assertTrue(call is Call)
    assertEquals("print", call.primary.asIdentifier().name)

    val param = call.arguments.firstOrNull() as Identifier
    assertEquals("i", param.name)
  }

  @Test
  fun testImport() {
    val file = File(javaClass.classLoader.getResource("import.py")!!.file)

    val input = PythonParser().parse(file.path).root
    assertNotNull(input)

    // println(input)

    val import = input.statements.firstOrNull()
    assertTrue(import is ImportStatement)
    assertEquals("os", import.module.name)

    val expr = input.statementAsOrNull<ExpressionStatement>(1)
    assertNotNull(expr is ExpressionStatement)

    val call = expr?.expression
    assertTrue(call is Call)

    val callName = call.primary as Identifier
    assertEquals(callName.name, "print")

    val attributeRef = call.arguments.firstOrNull() as AttributeRef
    val mod = attributeRef.primary as Identifier
    assertEquals(mod.name, "os")

    val id = attributeRef.identifier
    assertEquals(id.name, "name")
  }
}
