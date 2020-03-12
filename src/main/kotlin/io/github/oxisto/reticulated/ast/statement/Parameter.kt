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

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.Identifier
import io.github.oxisto.reticulated.solver.Identifiable

/**
 * A parameter, i.e. in a function definition.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-parameter
 */
open class Parameter(final override val id: Identifier, val expression: Expression? = null) : Node(), Identifiable {

  init {
    id.parent = this
    expression?.parent = this
  }

  override fun toString(): String {
    var result = "Parameter(" + System.lineSeparator() +
        "\tid=$id"
    if(expression != null){
      result += " expression=$expression"
    }
    result += System.lineSeparator() + ")"
    return result
  }
}