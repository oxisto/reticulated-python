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

import io.github.oxisto.reticulated.ast.expression.booleanops.BaseBooleanOp

/**
 * This class represents a conditional_expression
 * It´s EBNF representation is:
 *        conditional_expression ::= or_test ["if" or_test "else" expression]
 * [see: https://docs.python.org/3/reference/expressions.html#conditional-expressions]
 */
class ConditionalExpression(
  val orTest: BaseBooleanOp,
  val orTestOptional: BaseBooleanOp,
  val expressionOptional: Expression
) : Expression() {

  override fun toString(): String {
    var result = "ConditionalExpression(" + System.lineSeparator() +
      "\torTest=$orTest"
    result += " if orTest=$orTestOptional else expression=$expressionOptional"
    result += System.lineSeparator() + ")"
    return "ConditionalExpression(" + System.lineSeparator() +
      "\torTest=$orTest \"if\" " +
      "orTest=$orTestOptional \"else\" " +
      "expression=$expressionOptional" +
      System.lineSeparator() +
      ")"
  }
}
