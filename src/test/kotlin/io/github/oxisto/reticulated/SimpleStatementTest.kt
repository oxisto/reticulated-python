package io.github.oxisto.reticulated

import io.github.oxisto.reticulated.ast.expression.Call
import io.github.oxisto.reticulated.ast.expression.Integer
import io.github.oxisto.reticulated.ast.simple.AssignmentExpression
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.simple.ImportStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SimpleStatementTest {
  @Test
  fun testParse() {
    val file = File(javaClass.classLoader.getResource("simple_stmt.py").file)

    val input = PythonParser().parse(file.path)
    assertNotNull(input)

    assertEquals(2, input.statements.size)

    val s0 = input.statements[0] as StatementList
    val assign = s0.first()
    assertTrue(assign is AssignmentExpression)
    assertEquals("i", assign.target.name.name)

    val assigned = assign.expression
    assertTrue(assigned is Integer)
    assertEquals(4, assigned.value)

    val s1 = input.statements[1] as StatementList
    val exprStatement = s1.first()
    assertTrue(exprStatement is ExpressionStatement)

    val call = exprStatement.expression
    assertTrue(call is Call)
    assertEquals("print", call.primary.asIdentifier().name)
  }

  @Test
  fun testImport() {
    val file = File(javaClass.classLoader.getResource("import.py").file)

    val input = PythonParser().parse(file.path)
    assertNotNull(input)

    val s0 = input.statements[0] as StatementList
    val import = s0.first()
    assertTrue(import is ImportStatement)
    assertEquals("os", import.module.name)
  }
}