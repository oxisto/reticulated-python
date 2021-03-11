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
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.List
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.StringLiteral
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ListTest {

  @Test
  fun emptyListTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/empty_list.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    // print(input.toString())

    val expr = input.statements.firstOrNull() as ExpressionStatement

    val list = expr.expression as List

    assertNotNull(list)
    assertEquals(0, list.elts.size)
  }

  @Test
  fun listTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/list.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    val expr = input.statements.firstOrNull() as ExpressionStatement

    val list = expr.expression as List

    assertNotNull(list)
    assertEquals(2, list.elts.size)

    val firstElement = list[0] as Integer
    assertEquals(firstElement.value, 1)

    val secondElement = list[1] as List
    assertNotNull(secondElement)

    val valueOfSecondElement = secondElement[0] as StringLiteral
    assertEquals(valueOfSecondElement.value, "a")
  }

  /*@Test
  fun comprehensionListTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/comprehension_list.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    // print(input.toString())
    val list = input.statements[0] as ListComprehension
    assertNotNull(list)
    assertNull(list.starredList)
    val comprehension = list.comprehension
    assertNotNull(comprehension)
    val compIdentifier = comprehension.expression as Identifier
    assertEquals(compIdentifier.name, "x")
    val compFor = comprehension.compFor
    assertFalse(compFor.isAsync)
    val targetIdentifier = compFor.targetList as Identifier
    assertEquals(targetIdentifier.name, "x")
    val call = compFor.orTest as Call
    assertNotNull(call)
    val callName = call.primary as Identifier
    assertEquals(callName.name, "range")
    val callArgument = call.callTrailer as Integer
    assertEquals(callArgument.value, 10)
  }*/

}
