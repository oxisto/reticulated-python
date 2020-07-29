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
import io.github.oxisto.reticulated.ast.expression.primary.AttributeRef
import io.github.oxisto.reticulated.ast.simple.AssignmentExpression
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.simple.ImportStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import org.w3c.dom.Attr
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

    // println(input.toString())

    val assign = input.statements[0]
    assertTrue(assign is AssignmentExpression)
    val identifier = assign.target as Identifier
    assertEquals("i", identifier.name)
    val assigned = assign.expression
    assertTrue(assigned is Integer)
    assertEquals(4, assigned.value)

    val call = input.statements[1]
    assertTrue(call is Call)
    assertEquals("print", call.primary.asIdentifier().name)
    val param = call.callTrailer as Identifier
    assertEquals("i", param.name)
  }

  @Test
  fun testImport() {
    val file = File(javaClass.classLoader.getResource("import.py")!!.file)

    val input = PythonParser().parse(file.path).root
    assertNotNull(input)

    // println(input)

    val import = input.statements[0]
    assertTrue(import is ImportStatement)
    assertEquals("os", import.module.name)

    val call = input.statements[1] as Call
    val callName = call.primary as Identifier
    assertEquals(callName.name, "print")
    val attributeRef = call.callTrailer as AttributeRef
    val mod = attributeRef.primary as Identifier
    assertEquals(mod.name, "os")
    val id = attributeRef.identifier
    assertEquals(id.name, "name")
  }

  @Test fun parseToStringTest () {
    val file = File(javaClass.classLoader.getResource("simple_stmt.py")!!.file)

    val input = PythonParser().parse(file.path).root
    assertNotNull(input)
    assertEquals(input.toString(), """FileInput(statements=[AssignmentExpression(
	target=Identifier(
	name='i'
) expression=Integer(
	value=4
)
), Call(
	primary=Identifier(
	name='print'
) callTrailer=Identifier(
	name='i'
)
)])""".replace("\n", System.lineSeparator()))
  }

  @Test fun importToStringTest () {
    val file = File(javaClass.classLoader.getResource("import.py")!!.file)

    val input = PythonParser().parse(file.path).root
    assertNotNull(input)
    assertEquals(input.toString(), """FileInput(statements=[ImportStatement(
	module=Identifier(
	name='os'
)
), Call(
	primary=Identifier(
	name='print'
) callTrailer=AttributeRef(
	primary=Identifier(
	name='os'
)"."identifier=Identifier(
	name='name'
)
)
)])""".replace("\n", System.lineSeparator()))
  }
}