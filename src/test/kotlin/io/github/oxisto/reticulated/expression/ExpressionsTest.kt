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
import io.github.oxisto.reticulated.ast.expression.Starred
import io.github.oxisto.reticulated.ast.expression.argument.Arguments
import io.github.oxisto.reticulated.ast.expression.argument.PositionalArgument
import io.github.oxisto.reticulated.ast.expression.primary.AttributeRef
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.List
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.simple.ImportStatement
import io.github.oxisto.reticulated.ast.statement.parameter.Parameter
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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

    // print(input)

    val importStatement = input.statementAsOrNull<ImportStatement>(0)
    assertNotNull(importStatement)

    val module = importStatement.module
    assertEquals(module.name, "os")

    val expr = input.statementAsOrNull(1) as ExpressionStatement?
    assertNotNull(expr)

    val call = expr.expression as Call
    assertNotNull(call)

    val prim = call.primary as Identifier
    assertEquals(prim.name, "print")

    val trailer = call.arguments.firstOrNull() as AttributeRef
    assertNotNull(call)

    val primary = trailer.primary as Identifier
    assertEquals(primary.name, "os")

    val id = trailer.identifier
    assertEquals(id.name, "name")
  }

  @Test
  fun starredTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/starred.py")!!
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
    val expr = input.statements[0] as ExpressionStatement
    val call = expr.expression as Call

    val callName = call.primary as Identifier
    assertEquals("print", callName.name)

    val argumentList = call.arguments
    val firstArgument = argumentList[0] as Starred
    var list = firstArgument.expression as List
    assertEquals(1, list.count)

    val firstValue = list[0] as Integer
    assertEquals(1, firstValue.value, )

    var starred = argumentList[1] as Starred
    assertEquals(Parameter.StarType.STAR, starred.star)
    
    list = starred.expression as List
    assertEquals(2, list.count)

    val secondValue = list[0] as Integer
    assertEquals(2, secondValue.value, )

    starred = list[1] as Starred
    list = starred.expression as List

    val thirdValue = list[0] as Integer
    assertEquals(3, thirdValue.value, )
  }

}
