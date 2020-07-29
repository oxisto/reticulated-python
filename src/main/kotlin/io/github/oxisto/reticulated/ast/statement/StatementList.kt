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

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.simple.SimpleStatement

/**
 * A list of simple statements, usually on the same line, separated by semicolon.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-stmt-list
 */
class StatementList(val statements: List<SimpleStatement>) : Statement(), Container<SimpleStatement> {
  /*

  override fun isCompoundStatement(): Boolean {
    return false
  }

  override fun isStatementList(): Boolean {
    return true
  }
*/
  override val children get() = this.statements



  override fun toString(): String {
    return "StatementList(" + System.lineSeparator() +
        "\tstatements=$statements" + System.lineSeparator() +
        ")"
  }

}