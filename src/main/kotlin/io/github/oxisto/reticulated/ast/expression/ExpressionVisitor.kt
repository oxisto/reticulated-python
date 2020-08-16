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

import io.github.oxisto.reticulated.Pair
import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.comparison.CompOperator
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.lambda.Lambda
import io.github.oxisto.reticulated.ast.expression.primary.PrimaryVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.Enclosure
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.YieldExpression
import io.github.oxisto.reticulated.ast.statement.parameter.Parameter
import io.github.oxisto.reticulated.ast.statement.parameter.Parameters
import io.github.oxisto.reticulated.ast.statement.parameter.ParametersVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This class offers visitors for a Expression an a ExpressionList.
 */
class ExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {

  // test: or_test ('if' or_test 'else' test)? | lambdef;
  override fun visitTest(ctx: Python3Parser.TestContext): Expression {
    return if (ctx.childCount == 1)
    // just a wrapper, pass it down
      ctx.getChild(0).accept(ExpressionVisitor(this.scope))
    else if (ctx.or_test() != null && ctx.or_test().size == 2) {
      val body = ctx.or_test(0).accept(ExpressionVisitor(this.scope))
      val test = ctx.or_test(1).accept(ExpressionVisitor(this.scope))
      val orElse = ctx.test().accept(ExpressionVisitor(this.scope))

      ConditionalExpression(test, body, orElse)
    } else {
      super.visitTest(ctx)
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

  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext): Expression {
    return ctx.accept(PrimaryVisitor(scope))
  }

  override fun visitLambdef_nocond(ctx: Python3Parser.Lambdef_nocondContext): Lambda {
    var parameters: Parameters = Parameters()
    val expressionNoCond: Expression
    val getExpressionNoCondByPosition = { position: Int ->
      ctx
        .getChild(position)
        .accept(
          ExpressionVisitor(
            this.scope
          )
        )
    }
    expressionNoCond = if (ctx.childCount == 3)
      getExpressionNoCondByPosition(2)
    else {
      parameters = ctx
        .getChild(1)
        .accept(
          ParametersVisitor(
            this.scope
          )
        )
      getExpressionNoCondByPosition(3)
    }
    return Lambda(parameters, expressionNoCond)
  }

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

  override fun visitStar_expr(ctx: Python3Parser.Star_exprContext?): Expression {
    if (ctx?.STAR() == null || ctx.expr() == null) {
      return super.visitStar_expr(ctx)
    }

    var star = Parameter.StarType.STAR
    if (ctx.STAR().symbol.text == "**") {
      star = Parameter.StarType.DOUBLE_STAR
    }

    return Starred(ctx.expr().accept(ExpressionVisitor(scope)), star)
  }

  override fun visitComparison(ctx: Python3Parser.ComparisonContext): Expression {
    if (ctx.comp_op().size == 0) {
      return super.visitComparison(ctx)
    }

    val comparisons = mutableListOf<Pair<CompOperator, Expression>>()

    val left = ctx.expr(0).accept(ExpressionVisitor(this.scope))

    for (i in 0..ctx.comp_op().lastIndex) {
      val symbol = ctx.comp_op(i).text
      val op: CompOperator = CompOperator.getCompOperatorBySymbol(symbol)
        ?: throw CouldNotParseException("Operator $symbol not supported")

      val expr = ctx.expr(i + 1).accept(ExpressionVisitor(this.scope))

      comparisons.add(Pair(op, expr))
    }

    return Comparison(left, comparisons)
  }
}
