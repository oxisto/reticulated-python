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

package io.github.oxisto.reticulated.ast.expression

/**
 * This class represents a conditional_expression
 *
 * [see: https://docs.python.org/3/reference/expressions.html#conditional-expressions]
 */
class ConditionalExpression(
  /**
   * The expression this condition is tested against
   */
  val test: Expression,
  /**
   * The expression that is returned if the condition is true
   */
  val body: Expression,
  /**
   * The expression that is returned if the condition is not true
   */
  val orElse: Expression
) : Expression() {

  override fun toString(): String {
    return "IfExpression(test=$test, body=$body, orElse=$orElse)"
  }
}
