package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.ParentForm
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ParentFormTest {

  @Test
  fun parentFormTest () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/parent_form.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    // print(input.toString())

    val parentForm = input.statements[0] as ParentForm
    assertNotNull(parentForm)
    val valueOfParentForm = parentForm.starredExpression as Integer
    assertEquals(valueOfParentForm.value, 1)
  }
}