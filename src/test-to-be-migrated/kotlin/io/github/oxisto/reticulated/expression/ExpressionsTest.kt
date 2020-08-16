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
import io.github.oxisto.reticulated.ast.expression.primary.AttributeRef
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.simple.ImportStatement
import org.junit.Test
import java.io.File
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

    // print(input)

    val importStatement = input.statementAsOrNull<ImportStatement>(0)
    assertNotNull(importStatement)

    val module = importStatement.module
    assertEquals(module.name, "os")

    val call = input.statementAsOrNull(1) as Call?
    assertNotNull(call)

    val prim = call.primary as Identifier
    assertEquals(prim.name, "print")

    val trailer = call.callTrailer as AttributeRef
    assertNotNull(call)

    val primary = trailer.primary as Identifier
    assertEquals(primary.name, "os")

    val id = trailer.identifier
    assertEquals(id.name, "name")
  }
}
