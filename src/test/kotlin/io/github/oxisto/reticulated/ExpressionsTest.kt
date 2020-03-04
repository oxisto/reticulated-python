package io.github.oxisto.reticulated

import io.github.oxisto.reticulated.ast.expression.Call
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
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

    val functionDefinition = input.statements[1] as FunctionDefinition
    assertNotNull(functionDefinition)
    val parameterList = functionDefinition.parameterList
    assertNotNull(parameterList)
    val param1 = parameterList.parameters[0]
    assertNotNull(param1)
    // TODO: parameter expression is null
    // val param1Expression = param1.expression as Expression
    // assertNotNull(param1Expression)
    val param2 = parameterList.parameters[1]
    assertNotNull(param2)
    // val param2Expression = param2.expression as Expression
    // assertNotNull(param2Expression)

    val statementList = input.statements[2] as StatementList
    assertNotNull(statementList)
    val expr = statementList.statements[0] as ExpressionStatement
    assertNotNull(expr)
    val call = expr.expression as Call
    assertNotNull(call)
    val primary = call.primary
    assertNotNull(primary)
    val argList = call.argumentList
    assertNotNull(argList)
    val kwarg1 = argList[4]
    assertNotNull(kwarg1)
    val kwarg2 = argList[6]
    assertNotNull(kwarg2)

    assertNotNull(primary)
  }

  @Test
  fun testComprehensionArgument() {
    // booms because it is not implemented
    val file = File(
            javaClass
                .classLoader
                .getResource("comprehension_argument.py")!!
                .file
    )

    val input = PythonParser()
            .parse(file.path)
            .root

    assertNotNull(input)

    // TODO: further assertinons
  }
}