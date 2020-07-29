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
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.SetDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.StringLiteral
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SetTest {

  @Test
  fun starredSetTest () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/starred_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    // print(input.toString())

    val set = input.statements[0] as SetDisplay
    assertNotNull(set)
    assertNull(set.comprehension)
    val starredList = set.starredList
    assertNotNull(starredList)
    val firstElement = starredList[0] as Integer
    assertEquals(firstElement.value, 1)
    val valueOfSecondElement = starredList[1] as StringLiteral
    assertEquals(valueOfSecondElement.value, "a")
  }

  @Test
  fun comprehensionSetTest () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/comprehension_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    // print(input.toString())

    val set = input.statements[0] as SetDisplay
    assertNotNull(set)
    assertNull(set.starredList)
    val comprehension = set.comprehension
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
  }


  @Test
  fun starredSetToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/starred_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    val inputString = input.toString()
    // print(beautifyResult(inputString))
    assertEquals(inputString, """FileInput(statements=[SetDisplay(
	"{"starredList=StarredList(
	starredItems=[Integer(
	value=1
), StringLiteral(
	value=a
)]
) "}"
)])""".replace("\n", System.lineSeparator()))
  }

  @Test
  fun comprehensionSetToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/comprehension_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    val inputString = input.toString()
    // print(beautifyResult(inputString))
    assertEquals(inputString, """FileInput(statements=[SetDisplay(
	"{"comprehension=Comprehension(
	expression=Identifier(
	name='x'
) compFor=CompFor(
	"for" targetList=Identifier(
	name='x'
)  "in" orTest=Call(
	primary=Identifier(
	name='range'
) callTrailer=Integer(
	value=10
)
)
)
) "}"
)])""".replace("\n", System.lineSeparator()))
  }
}