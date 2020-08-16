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
import io.github.oxisto.reticulated.ast.expression.argument.Arguments
import io.github.oxisto.reticulated.ast.expression.argument.KeywordItem
import io.github.oxisto.reticulated.ast.expression.argument.Kwarg
import io.github.oxisto.reticulated.ast.expression.argument.PositionalArgument
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.StringLiteral
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.parameter.Parameters
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class KwargTest {

  @Test
  fun testKwargCallRef() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/argument/kwarg.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    // print(input)

    val firstFunctionDefinition = input.statements[0] as FunctionDefinition
    assertNotNull(firstFunctionDefinition)
    assertNull(firstFunctionDefinition.returnDecorator)
    val firstIdentifier = firstFunctionDefinition.funcName
    assertEquals(firstIdentifier.name, "func")
    val parameters = (
        firstFunctionDefinition.parameters as Parameters
        ).parameters
    assertEquals(parameters.size, 3)
    val firstParameter = parameters[0] as Identifier
    assertEquals(firstParameter.name, "param")
    val secondParameter = parameters[1] as StarredParameter
    assertNotNull(secondParameter)
    val valueOfSecondParameter = secondParameter.parameter as Identifier
    assertEquals(valueOfSecondParameter.name, "args")
    val thirdParameter = parameters[2] as DoubleStarredParameter
    assertNotNull(thirdParameter)
    val valueOfThirdParameter = thirdParameter.parameter as Identifier
    assertEquals(valueOfThirdParameter.name, "kwargs")

    val firstSuite = firstFunctionDefinition.suite
    assertNotNull(firstSuite)
    val firstCall = firstSuite as Call
    assertNotNull(firstCall)
    val firstCallName = firstCall.primary as Identifier
    assertEquals(firstCallName.name, "print")
    val firstCallArgs = firstCall.callTrailer as Arguments
    assertNotNull(firstCallArgs)
    val firstArgument = firstCallArgs[0] as Identifier
    assertEquals(firstArgument.name, "param")
    val secondArgument = firstCallArgs[1] as Identifier
    assertEquals(secondArgument.name, "args")
    val thirdArgument = firstCallArgs[2] as Identifier
    assertEquals(thirdArgument.name, "kwargs")

    val functionDefinition = input.statements[1] as FunctionDefinition
    assertNotNull(functionDefinition)
    assertNull(functionDefinition.returnDecorator)
    val id = functionDefinition.funcName
    assertEquals(id.name, "fun_func")
    val parameterList = functionDefinition.parameters as Parameters
    assertNotNull(parameterList)
    val param1 = parameterList.parameters[0] as StarredParameter
    assertNotNull(param1)
    val idOfParam1 = param1.id
    assertEquals(idOfParam1.name, "args_f")
    val param2 = parameterList.parameters[1] as DoubleStarredParameter
    assertNotNull(param2)
    val idOfParam2 = param2.id
    assertEquals(idOfParam2.name, "kwargs_f")

    val secondCall = functionDefinition.suite as Call
    assertNotNull(secondCall)
    val callName = secondCall.primary as Identifier
    assertEquals(callName.name, "func")
    val callTrailer = secondCall.callTrailer as Arguments
    assertNotNull(callTrailer)
    val secondParam1 = callTrailer[0] as StringLiteral
    assertEquals(secondParam1.value, "a")
    val secondParam2 = callTrailer[1] as StringLiteral
    assertEquals(secondParam2.value, "1")
    val secondParam3 = callTrailer[2] as StringLiteral
    assertEquals(secondParam3.value, "2")
    val secondParam4 = callTrailer[3] as PositionalArgument
    assertNotNull(secondParam4)
    val idOfSecondParam4 = secondParam4.expression as Identifier
    assertEquals(idOfSecondParam4.name, "args_f")
    val secondParam5 = callTrailer[4] as Kwarg
    assertNotNull(secondParam5)
    val idOfSecondParam5 = secondParam5.expression as Identifier
    assertEquals(idOfSecondParam5.name, "kwargs_f")

    val call = input.statements[2] as Call
    assertNotNull(call)
    val primary = call.primary as Identifier
    assertEquals(primary.name, "fun_func")
    val argList = call.callTrailer as Arguments
    assertNotNull(argList)
    val arg1 = argList[0] as StringLiteral
    assertEquals(arg1.value, "$")
    val arg2 = argList[1] as StringLiteral
    assertEquals(arg2.value, "%")
    val kwarg1 = argList[2] as KeywordItem
    assertNotNull(kwarg1)
    val kwarg1Id = kwarg1.identifier
    assertEquals(kwarg1Id.name, "kwargs1")
    val kwarg1Expression = kwarg1.expression as StringLiteral
    assertEquals(kwarg1Expression.value, "test1")
    val kwarg2 = argList[3] as KeywordItem
    assertNotNull(kwarg2)
    val kwarg2Id = kwarg2.identifier
    assertEquals(kwarg2Id.name, "kwargs2")
    val kwarg2Expression = kwarg2.expression as StringLiteral
    assertEquals(kwarg2Expression.value, "test2")
  }

}
