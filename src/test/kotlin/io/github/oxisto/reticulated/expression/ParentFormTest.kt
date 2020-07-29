package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.ConditionalExpression
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.ParentForm
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.SetDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.starred.StarredExpression
import io.github.oxisto.reticulated.ast.expression.starred.StarredItem
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
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

   @Test fun prenthFormToStringTest () {
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

     val inputString  = input.toString()

     assertEquals(inputString, """FileInput(statements=[StarredExpression(
	 "(" starredExpression=Integer(
	value=1
) ")"
)])""".replace("\n", System.lineSeparator()))
   }

}