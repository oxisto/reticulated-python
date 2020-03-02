package io.github.oxisto.reticulated

import io.github.oxisto.reticulated.ast.expression.Call
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
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

    val s1 = input.statements[1] as StatementList
    val expr = s1.statements[0] as ExpressionStatement
    val call = expr.expression as Call
    val arg0 = call.argumentList[0]
    assertNotNull(arg0)
  }

  @Test
  fun testKwargCallRef() {
    val file = File(
                  javaClass
                    .classLoader
                    .getResource("kwarg.py")!!
                    .file
    )

    val input = PythonParser()
            .parse(file.path)
            .root

    assertNotNull(input)

    val s1 = input.statements[0] as StatementList
    val expr = s1.statements[0] as ExpressionStatement
    val call = expr.expression as Call
    val primary = call.primary
    val argList = call.argumentList
    val kwarg1 = argList[8];
    val kwarg2 = argList[10]
    assertNotNull(primary)
    assertNotNull(kwarg1)
    assertNotNull(kwarg2)
  }

  @Test // (timeout = 1_000)
  fun testListComprehension() {
    val file = File(
            javaClass
                .classLoader
                .getResource("list_comprehension.py")!!
                .file
    )

    val input = PythonParser()
            .parse(file.path)
            .root

    assertNotNull(input)

    val s1 = input.statements[0] as StatementList
    val expr = s1.statements[0] as ExpressionStatement
    val call = expr.expression as Call
    val primary = call.primary
    val argList = call.argumentList
    val kwarg1 = argList[8];
    val kwarg2 = argList[10]
    assertNotNull(primary)
    assertNotNull(kwarg1)
    assertNotNull(kwarg2)
  }
}