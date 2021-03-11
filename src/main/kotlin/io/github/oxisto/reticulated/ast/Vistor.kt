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
import io.github.oxisto.reticulated.ast.statement.StatementVisitor
import io.github.oxisto.reticulated.ast.statement.Statements
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class Visitor(val scope: Scope) : Python3BaseVisitor<Node>() {

  override fun visitFile_input(ctx: Python3Parser.File_inputContext?): FileInput {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val statements = ArrayList<Statement>()

    // loop through children
    for (tree in ctx.children) {
      if (tree is TerminalNode) {
        continue
      }

      val stmt = tree.accept(StatementVisitor(this.scope))
      if (stmt is Statements) {
        statements.addAll(stmt)
      } else {
        statements.add(stmt)
      }
    }

    return FileInput(statements)
  }
}

class SuiteVisitor(val scope: Scope) : Python3BaseVisitor<Suite>() {

  override fun visitSuite(ctx: Python3Parser.SuiteContext): Suite {
    // a suite is either one simple statement
    ctx.simple_stmt()?.let {
      return Suite(listOf(it.accept(StatementVisitor(this.scope))))
    }

    // or a list of statement
    val list = ArrayList<Statement>()

    for (tree in ctx.stmt()) {
      val stmt = tree.accept(StatementVisitor(this.scope))
      if (stmt is Statements) {
        list.addAll(stmt)
      } else {
        list.add(stmt)
      }
    }

    return Suite(list)
  }
}
