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

package io.github.oxisto.reticulated.ast

import io.github.oxisto.reticulated.ast.statement.Statement
import java.util.*

class FileInput(val statements: ArrayList<Statement> = ArrayList()) : Node() {

  init {
    for (stmt in statements) {
      stmt.parent = this
    }
  }

  override fun toString(): String {
    return "FileInput(statements=$statements)"
  }

  inline fun <reified T> statementAsOrNull(i: Int): T? {
    val statement = statements.getOrNull(i)

    if (statement != null && statement is T?) {
      return statement
    }

    return null
  }


}
