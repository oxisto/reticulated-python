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


package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * This class represents a yield_eypression.
 * It´s EBNF representations is:
 *        yield_expression ::=  "yield" [expression_list | "from" expression]
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#grammar-token-yield-atom}]
 */
class YieldExpression(val expressionList: Expression?, val expression: Expression?) : Enclosure() {

  init {
    if (expressionList != null && expression != null)
      throw CouldNotParseException(
        "A YieldExpression cannot contain both, a expressionList=$expressionList and a expression=$expression."
      )
  }

  override fun toString(): String {
    var result = "YieldExpression(" + System.lineSeparator() +
      "\t\"yield\" "
    if (expressionList != null)
      result += "expressionList=$expressionList"
    if (expression != null)
      result += "\"from\" expression=$expression"
    return result + System.lineSeparator() + ")"
  }
}
