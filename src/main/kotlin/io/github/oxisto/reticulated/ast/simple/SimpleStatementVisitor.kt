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

package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.EmptyContextException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.Name
import io.github.oxisto.reticulated.ast.simple.target.TargetVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class SimpleStatementVisitor(val scope: Scope) : Python3BaseVisitor<SimpleStatement>() {

  override fun visitSimple_stmt(ctx: Python3Parser.Simple_stmtContext?): SimpleStatement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // not sure how to handle this. are there cases with more than 1 child? Yes, e.g.: a = 1 + 1
    return ctx.getChild(0).accept(this)
  }

  override fun visitImport_stmt(ctx: Python3Parser.Import_stmtContext?): ImportStatement {
    // TODO: Define a name in the local namespace for the import statement
    return super.visitImport_stmt(ctx) as ImportStatement
  }

  override fun visitImport_name(ctx: Python3Parser.Import_nameContext?): ImportStatement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // TODO: Support dotted modules
    val module = ctx
        .getChild(1)
        .accept(
            AtomVisitor(this.scope)
    ) as Identifier

    // build a name
    val name = Name(module.name)

    // bind to scope
    name.bind(this.scope)

    return ImportStatement(module)
  }

  override fun visitExpr_stmt(ctx: Python3Parser.Expr_stmtContext): SimpleStatement {

    // need some kind of logic here how to decide what exactly this is
    if (ctx.childCount == 1) {
      val expression = ctx.getChild(0).accept(
        ExpressionVisitor(this.scope)
      )

      return ExpressionStatement(expression)
    } else if (ctx.childCount == 3) {

      // probably an assignment statement, but there are cases when an assignment has more than 3 children e.g. a = b = 123
      // for now assume that child 0 = target; child 2 = expression
      // val targetList = ctx.getChild(0).accept(TargetListVisitor(this.scope))
      val target = ctx.getChild(0).accept(TargetVisitor(this.scope))

      val expression = ctx.getChild(2).accept(ExpressionVisitor(this.scope))

      return AssignmentExpression(target, expression)
    }

    throw Exception()
  }
}