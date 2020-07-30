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

package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.booleanops.BooleanOpVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This class offers visitors for a Expression an a ExpressionList.
 */
class ExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {

  override fun visitTest(ctx: Python3Parser.TestContext): Expression {
    return if (ctx.childCount == 1)
      ctx.getChild(0).accept(BooleanOpVisitor(this.scope))
    else {
      val orTest = ctx.getChild(0)
          .accept(BooleanOpVisitor(this.scope))
      val orTestOptional = ctx.getChild(2)
          .accept(BooleanOpVisitor(this.scope))
      val expressionOptional = ctx.getChild(4).accept(this)
      ConditionalExpression(orTest, orTestOptional, expressionOptional)
    }
  }

  override fun visitTestlist(ctx: Python3Parser.TestlistContext): Expression {
    val expressions = ArrayList<Expression>()
    for (index in 0 until ctx.childCount step 2)
      expressions.add(
          ctx.getChild(index).accept(this)
      )
    return if (expressions.size == 1)
      expressions[0]
    else
      ExpressionList(expressions)
  }
}