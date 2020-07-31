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

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This class offers visitors for a yield_expression
 *
 */
class YieldExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {
  override fun visitYield_expr(ctx: Python3Parser.Yield_exprContext): Enclosure {
    val yieldExpression = ctx.getChild(1).accept(this)
    return if (ctx.getChild(1).childCount == 1)
      YieldExpression(yieldExpression, null)
    else
      YieldExpression(null, yieldExpression)
  }

  override fun visitYield_arg(ctx: Python3Parser.Yield_argContext): Expression {
    val index = ctx.childCount - 1
    return ctx.getChild(index).accept(ExpressionVisitor(this.scope))
  }
}