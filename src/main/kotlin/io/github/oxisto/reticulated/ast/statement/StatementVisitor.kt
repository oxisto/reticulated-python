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
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class StatementVisitor(val scope: Scope) : Python3BaseVisitor<Statement>() {

  override fun visitStmt(ctx: Python3Parser.StmtContext?): Statement {
    if (ctx == null) {
      throw EmptyContextException()
    }

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

      StatementList(list)
    }
  }

  override fun visitFuncdef(ctx: Python3Parser.FuncdefContext?): Statement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // TODO: decorators

    // assume that the first child is 'def'

    // second is the name
    val id = ctx.getChild(1).accept(
        AtomVisitor(
            this.scope
        )
    ) as Identifier

    // create a new scope for this function
    val functionScope = Scope(
      this.scope,
      ScopeType.FUNCTION
    )

    // third is the parameter list
    val parameterList = ctx.getChild(2)
        .accept(
            Visitor(
                functionScope
            )
        ) as ParameterList

    // forth is ':' or '->'
    val op = ctx.getChild(3)
    var expression: Expression? = null
    if (op.text == "->") {
      // fifth is the optional type hint
      expression = ctx.getChild(4).accept(
        ExpressionVisitor(
          functionScope
        )
      )
    }

    // last is the suite
    val suite = ctx.getChild(ctx.childCount - 1).accept(
      Visitor(
        functionScope
      )
    ) as Suite

    // create a new function definition
    val def = FunctionDefinition(id, parameterList, suite, expression)

    // add it to the function scope
    functionScope.handleParameterList(parameterList)

    return def
  }

}