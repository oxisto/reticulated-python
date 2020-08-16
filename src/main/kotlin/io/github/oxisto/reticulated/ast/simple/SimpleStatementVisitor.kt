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

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.Name
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * Multiple simple statements can be on the same line
 */
class SimpleStatementsVisitor(val scope: Scope) : Python3BaseVisitor<List<SimpleStatement>>() {
  override fun visitSimple_stmt(ctx: Python3Parser.Simple_stmtContext): List<SimpleStatement> {
    val list = mutableListOf<SimpleStatement>()

    // loop through all possible children
    for (small in ctx.small_stmt()) {
      list.add(small.accept(SimpleStatementVisitor(scope)))
    }

    return list
  }
}

class SimpleStatementVisitor(val scope: Scope) : Python3BaseVisitor<SimpleStatement>() {

  override fun visitSmall_stmt(ctx: Python3Parser.Small_stmtContext?): SimpleStatement {
    return super.visitSmall_stmt(ctx)
  }

  override fun visitImport_stmt(ctx: Python3Parser.Import_stmtContext): ImportStatement {
    // TODO: Define a name in the local namespace for the import statement
    return super.visitImport_stmt(ctx) as ImportStatement
  }

  override fun visitImport_name(ctx: Python3Parser.Import_nameContext): ImportStatement {

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
    return if (ctx.childCount == 1)
      ExpressionStatement(ctx.getChild(0).accept(ExpressionVisitor(this.scope)))
    else {

      // probably an assignment statement, but there are cases when an assignment has more than 3 children e.g. a = b = 123
      // for now assume that child 0 = target; child 2 = expression
      // val targetList = ctx.getChild(0).accept(TargetListVisitor(this.scope))
      // TODO: Support multiple targets in an AssignmentExpression
      val target = ctx.getChild(0).accept(ExpressionVisitor(this.scope))

      val expression = ctx.getChild(2).accept(ExpressionVisitor(this.scope))

      AssignmentStatement(listOf(target), expression)
    }
  }
}
