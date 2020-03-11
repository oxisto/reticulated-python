package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.call.Call
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertNotNull

class KwargTest {

  @Test
  fun testKwargCallRef() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/kwarg.py")!!
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
    print(expr)
    assertNotNull(expr)
    val call = expr.expression as Call
    assertNotNull(call)
    val primary = call.primary
    assertNotNull(primary)
    val argList = call.callTrailer as ArgumentList
    assertNotNull(argList)
    val kwarg1 = argList[2]
    assertNotNull(kwarg1)
    val kwarg2 = argList[3]
    assertNotNull(kwarg2)

    assertNotNull(primary)
  }

}