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

import io.github.oxisto.reticulated.ast.*
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.simple.SimpleStatement
import io.github.oxisto.reticulated.ast.simple.SimpleStatementVisitor
import io.github.oxisto.reticulated.ast.statement.parameter.Parameters
import io.github.oxisto.reticulated.ast.statement.parameter.ParametersVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class StatementVisitor(val scope: Scope) : Python3BaseVisitor<Statement>() {

  override fun visitStmt(ctx: Python3Parser.StmtContext): Statement {

    return if (ctx.childCount == 1 && ctx.getChild(0) is Python3Parser.Compound_stmtContext) {
      // its a compound statement
      ctx.getChild(0).accept(this)
    } else {
      // create a statement list
      val list = ArrayList<SimpleStatement>()

      // loop through children
      for (tree in ctx.children) {
        list.add(
            tree.accept(
                SimpleStatementVisitor(
                    this.scope
                )
            )
        )
      }

      return if (list.size == 1) {
        list.first()
      } else {
        StatementList(list)
      }
    }
  }

  override fun visitFuncdef(ctx: Python3Parser.FuncdefContext): Statement {
    if (ctx.DEF() == null ||
        ctx.NAME() == null ||
        ctx.parameters() == null ||
        ctx.suite() == null) {
      throw CouldNotParseException("Incorrect function definition")
    }

    // second is the name
    val id = ctx.NAME().accept(
        AtomVisitor(
            this.scope
        )
    ) as Identifier

    // create a new scope for this function
    val functionScope = Scope(
        this.scope,
        ScopeType.FUNCTION
    )

    // parse parameters
    val parameters = ctx.parameters()
        .accept(
            ParametersVisitor(
                functionScope
            )
        )

    // parse annotation
    var annotation: Expression? = null
    if (ctx.ARROW() != null) {
      annotation = ctx.test()?.accept(ExpressionVisitor(functionScope))
    }

    // last is the suite
    val suite = ctx.suite().accept(
        SuiteVisitor(
            functionScope
        )
    )

    // create a new function definition
    val def = FunctionDefinition(id, parameters, suite, annotation)

    // add it to the function scope
    functionScope.handleParameterList(parameters)

    return def
  }

}
