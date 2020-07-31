package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.TestUtils.Companion.beautifyResult
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.argument.PositionalArgument
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.ListDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.starred.StarredItem
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StarredTest {
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
    val call = input.statements[0] as Call
    val callName = call.primary as Identifier
    assertEquals(callName.name, "print")
    val argumentList = call.callTrailer as ArgumentList
    val firstArgument = argumentList[0] as PositionalArgument
    val firstListDisplay = firstArgument.expression as ListDisplay
    assertNull(firstListDisplay.comprehension)
    val firstValue = firstListDisplay.starredList as Integer
    assertEquals(firstValue.value, 1)

    val secondArgument = argumentList[1] as PositionalArgument
    val secondListDisplay = secondArgument.expression as ListDisplay
    assertNull(secondListDisplay.comprehension)
    val starredList = secondListDisplay.starredList as StarredList
    val firstStarredItem = starredList[0] as Integer
    assertEquals(firstStarredItem.value, 2)
    val secondStarredItem = starredList[1] as StarredItem
    val secondSIListDisplay = secondStarredItem.orExpr as ListDisplay
    assertNull(secondSIListDisplay.comprehension)
    val secondValue = secondSIListDisplay.starredList as Integer
    assertEquals(secondValue.value, 3)
  }
}