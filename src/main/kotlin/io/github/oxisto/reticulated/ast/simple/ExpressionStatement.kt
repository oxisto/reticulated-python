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

package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * Represents a statement that contains an expression.
 */
open class ExpressionStatement(val expression: Expression) : SimpleStatement() {

  inline fun <reified T : Expression> asExpression(): T? {
    if (expression is T) {
      return expression
    }

    return null
  }

  override fun toString(): String {
    return "ExpressionStatement(expression=$expression)"
  }
}
