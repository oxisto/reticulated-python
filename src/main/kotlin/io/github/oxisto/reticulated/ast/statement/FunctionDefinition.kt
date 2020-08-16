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

package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Suite
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.statement.parameter.Parameters

/**
 * A function definition
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-funcdef
 */
class FunctionDefinition(
    val funcName: Identifier,
    val parameters: Parameters,
    suite: Suite,
    val annotation: Expression? = null
) : Definition(suite) {
  // TODO: decorators

  init {
    funcName.parent = this
    parameters.parent = this
  }

  override fun isFunctionDefinition(): Boolean {
    return true
  }

  override fun isClassDefinition(): Boolean {
    return false
  }

  override fun toString(): String {
    var result = "FunctionDefinition(" + System.lineSeparator() +
        "\t\"def\" funcname=$funcName \"(\" "
    result += "parameters=$parameters"
    result += " \")\""
    if (annotation != null)
      result += "\"->\" expression=$annotation"
    return "$result \":\" suite =$suite" + System.lineSeparator() +
        ")"
  }

}
