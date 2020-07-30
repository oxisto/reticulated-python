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

import io.github.oxisto.reticulated.ast.expression.booleanexpr.BaseBooleanExpr

/**
 * This class represents an starred_item.
 * It´s EBNF representation is:
 *        starred_item ::= assignment_expression | "*" or_expr
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#grammar-token-starred-item}]
 */
class StarredItem(val orExpr: BaseBooleanExpr): Starred() {

  override fun toString(): String {
    return "StarredItem(" + System.lineSeparator() +
        "\t\"*\" OrExpr=$orExpr" + System.lineSeparator() +
        ")"
  }
}