/*
 * Copyright (c) 2020, Fraunhofer AISEC. All rights reserved.
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

import io.github.oxisto.reticulated.ast.statement.ParameterList
import io.github.oxisto.reticulated.ast.statement.ParameterListVisitor
import io.github.oxisto.reticulated.ast.statement.Statement
import io.github.oxisto.reticulated.ast.statement.StatementVisitor
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

      val stmt = tree.accept(StatementVisitor(this.scope)) as Statement
      statements.add(stmt)
    }

    return FileInput(statements)
  }

  override fun visitParameters(ctx: Python3Parser.ParametersContext?): Node {
    if (ctx == null) {
      throw EmptyContextException()
    }

    if (ctx.childCount == 2) {
      return ParameterList()
    }

    // second parameter is the list of (typed) arguments
    val list = ctx.getChild(1).accept(
      ParameterListVisitor(
        this.scope
      )
    )

    return ParameterList(list)
  }

  override fun visitSuite(ctx: Python3Parser.SuiteContext?): Node {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val list = ArrayList<Statement>()

    for (tree in ctx.children) {
      // skip commas, etc.
      if (tree is TerminalNode) {
        continue
      }

      val stmt = tree.accept(StatementVisitor(this.scope)) as Statement
      list.add(stmt)
    }

    return Suite(list)
  }

}
