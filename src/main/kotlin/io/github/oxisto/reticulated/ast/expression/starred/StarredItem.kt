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


package io.github.oxisto.reticulated.ast.expression.starred

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.booleanexpr.OrExpr
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest

class StarredItem(val expression: Expression?, val orExpr: OrExpr?): Starred() {

  init {
    if (expression == null) {
      if (orExpr == null)
        throw CouldNotParseException(
            "In a StarredItem can´t be the Expression and the OrExpr be null."
        )
    } else {
      if (orExpr != null)
        throw CouldNotParseException(
            "In a StarredItem should be either the Expression=$expression or the OrExpr=$orExpr be null."
        )
    }
  }

  override fun toString(): String {
    var result = "StarredItem(" + System.lineSeparator() + "\t"
    result += if (expression == null)
      "\"*\" OrExpr=$orExpr"
    else
      "Expression=$expression"
    return result + System.lineSeparator() + ")"
  }
}