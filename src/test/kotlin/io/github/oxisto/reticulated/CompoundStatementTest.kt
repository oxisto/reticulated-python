package io.github.oxisto.reticulated

import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.IfStatement
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CompoundStatementTest {

  @Test
  fun testIf() {
    val file = File(
      javaClass
        .classLoader
        .getResource("if.py")!!
        .file
    )

    val result = PythonParser().parse(file.path)
    assertNotNull(result)

    val main = result.root.statements.first() as? FunctionDefinition

    assertNotNull(main)

    val `if` = main.body.statements.first() as? IfStatement

    assertNotNull(`if`)

    val comp = `if`.condition as? Comparison

    assertNotNull(comp)
    assertEquals("a", (comp.left as? Identifier)?.name)
  }
}