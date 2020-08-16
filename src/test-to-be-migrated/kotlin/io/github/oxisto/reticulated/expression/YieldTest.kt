package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.ExpressionList
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.YieldAtom
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.parameter.Parameters
import org.junit.Test
import java.io.File
import kotlin.test.*

class YieldTest {
  @Test
  fun yieldTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/yield.py")!!
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
    val firstFunction = input.statements[0] as FunctionDefinition
    assertNull(firstFunction.returnDecorator)
    val firstName = firstFunction.funcName
    assertNotNull(firstName)
    assertEquals(firstName.name, "foo")
    val firstParameters = firstFunction.parameters as Parameters
    assertNotNull(firstParameters)
    assertTrue(firstParameters.parameters.isEmpty())
    val firstYieldAtom = firstFunction.suite as YieldAtom
    val firstYieldExpression = firstYieldAtom.yieldExpression
    assertNull(firstYieldExpression.expression)
    val firstValue = firstYieldExpression.expressionList as Integer
    assertEquals(firstValue.value, 1)

    val secondFunction = input.statements[1] as FunctionDefinition
    assertNull(secondFunction.returnDecorator)
    val secondName = secondFunction.funcName
    assertEquals(secondName.name, "bar")
    val secondParameters = secondFunction.parameters as Parameters
    assertTrue(secondParameters.parameters.isEmpty())
    val secondYieldAtom = secondFunction.suite as YieldAtom
    val secondYieldExpression = secondYieldAtom.yieldExpression
    assertNull(secondYieldExpression.expression)
    val expressions = secondYieldExpression.expressionList as ExpressionList
    val expression1 = expressions[0] as Integer
    assertEquals(expression1.value, 1)
    val expression2 = expressions[1] as Integer
    assertEquals(expression2.value, 2)
    val expression3 = expressions[2] as Integer
    assertEquals(expression3.value, 3)

    val thirdFunction = input.statements[2] as FunctionDefinition
    assertNull(secondFunction.returnDecorator)
    val thirdName = thirdFunction.funcName
    assertEquals(thirdName.name, "foo_bar")
    val thirdParameters = thirdFunction.parameters as Parameters
    assertTrue(thirdParameters.parameters.isEmpty())
    val thirdYieldAtom = thirdFunction.suite as YieldAtom
    val thirdYieldExpression = thirdYieldAtom.yieldExpression
    assertNull(thirdYieldExpression.expressionList)
    val generatorExpression = thirdYieldExpression.expression as GeneratorExpression
    val generatorIdentifier = generatorExpression.expression as Identifier
    assertEquals(generatorIdentifier.name, "x")
    val compFor = generatorExpression.compFor
    assertFalse(compFor.isAsync)
    val targetList = compFor.targetList as Identifier
    assertEquals(targetList.name, "x")
    val call = compFor.orTest as Call
    val callName = call.primary as Identifier
    assertEquals(callName.name, "range")
    val callParam = call.callTrailer as Integer
    assertEquals(callParam.value, 100)
  }
}
