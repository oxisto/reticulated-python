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
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AsyncComprehensionTest {
    @Test
    fun testAsyncArgumentComprehension() {
        val file = File(
                javaClass
                        .classLoader
                        .getResource("expressions/comprehension/async_comprehension_argument.py")!!
            .file
    )

      val input = PythonParser()
              .parse(file.path)
              .root
      assertNotNull(input)
      // print(input)
      val call = input.statements[0] as Call
      assertNotNull(call)
      val primary = call.primary as Identifier
      assertEquals(primary.name, "fun")
      val trailer = call.callTrailer as Comprehension
      assertNotNull(trailer)
      val expression = trailer.expression as Identifier
      assertEquals(expression.name, "i")
      val compFor = trailer.compFor
      assertNotNull(compFor)
      val isAsync = compFor.isAsync
      assertTrue(isAsync)
      val target = compFor.targetList as Identifier
      assertEquals(target.name, "i")
      val orTest = compFor.orTest as Call
      val prim = orTest.primary as Identifier
      assertEquals(prim.name, "range")
      val callTrailer = orTest.callTrailer as Integer
      assertEquals(callTrailer.value, 10)
    }
}
