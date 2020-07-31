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
import io.github.oxisto.reticulated.ast.expression.booleanexpr.OrExpr
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest

/**
 * This class represents a key_datum pair.
 * It´s EBNF representations is:
 *        key_datum ::= expression ":" expression | "**" or_expr
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#grammar-token-key-datum}]
 */
class KeyDatum(val key: Expression?, val datum: Expression?, val orExpr: OrExpr?): Enclosure() {

  init {
    if (orExpr == null) {
      if (key == null || datum == null)
        throw CouldNotParseException(
            "A KeyDatum should contain a OrTest or a key-Expression and a datum-Expression."
        )
    } else {
      if (key != null || datum != null)
        throw CouldNotParseException(
            "A KeyDatum should not contain a orExpr=$orExpr and a Key-Expr=$key and a datum-Expr=$datum."
        )
    }
  }

  override fun toString(): String {
    var result = "KeyDatum(" + System.lineSeparator() + "\t"
    if (key != null)
      result += "key=$key \":\" datum=$datum"
    if (orExpr != null)
      result += "\"\"**\"\" orExpr=$orExpr"
    return result + System.lineSeparator() + ")"
  }
}