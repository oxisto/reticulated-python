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
import io.github.oxisto.reticulated.ast.expression.Identifier

/**
 * A function definition
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-funcdef
 */
class FunctionDefinition(
  val id: Identifier,
  val parameterList: ParameterList = ParameterList(), suite: Suite,
  val expression: Expression?
) : Definition(suite) {
  // TODO: decorators

  init {
    id.parent = this
    parameterList.parent = this
  }

  override fun isFunctionDefinition(): Boolean {
    return true
  }

  override fun isClassDefinition(): Boolean {
    return false
  }

  override fun toString(): String {
    return "FunctionDefinition(id=$id, parameters=$parameterList)"
  }

}