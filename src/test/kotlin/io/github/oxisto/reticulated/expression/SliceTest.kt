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
import io.github.oxisto.reticulated.ast.expression.primary.Subscription
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.ListDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.slice.*
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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
    // print(
//        beautifyResult(
            // input.toString()
//        )
    // )
    val subscription = input.statements[0] as Subscription
    assertNotNull(subscription)
    val primary0 = subscription.primary as Slicing
    assertNotNull(primary0)
    val primary1 = primary0.primary as Slicing
    assertNotNull(primary1)
    val listDisplay = primary1.primary as ListDisplay
    assertNotNull(listDisplay)
    assertNull(listDisplay.comprehension)
    val starredList = listDisplay.starredList as StarredList
    assertNotNull(starredList)
    val value = starredList[0] as Integer
    assertEquals(value.value, 1)
    val sI1Value = starredList[1] as Integer
    assertEquals(sI1Value.value, 2)

    val properSlice = primary1.sliceList as ProperSlice
    assertNotNull(properSlice)
    val lowerBound2Value = properSlice.lowerBound as Integer
    assertEquals( lowerBound2Value.value, 0)
    val upperBound2Value = properSlice.upperBound as Integer
    assertEquals(upperBound2Value.value, 3)
    val stride2 = properSlice.stride as Stride
    assertNotNull(stride2)
    val stride2Value = stride2.expression as Integer
    assertEquals(stride2Value.value, 2)

    val properSlice1 = primary0.sliceList as ProperSlice
    assertNotNull(properSlice1)
    assertNull(properSlice1.lowerBound)
    assertNull(properSlice1.upperBound)
    assertNull(properSlice1.stride)

    val sliceList0 = subscription.expressionList as Integer
    assertEquals(sliceList0.value, 0)
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
    assertEquals(input.toString(), """FileInput(statements=[Subscription(
	primary=Slicing(
	 primary=Slicing(
	 primary=ListDisplay(
	 "[" starredList=StarredList(
	starredItems=[Integer(
	value=1
), Integer(
	value=2
)]
) "]"
) "[" slice_list=ProperSlice(
	LowerBound=Integer(
	value=0
) ":" UpperBound=Integer(
	value=3
) Stride(
	":" Expression=Integer(
	value=2
)
)
)  "]"
) "[" slice_list=ProperSlice(
	 ":" 
)  "]"
) [ expressionList=Integer(
	value=0
) ] 
)])""".replace("\n", System.lineSeparator()))
  }
}