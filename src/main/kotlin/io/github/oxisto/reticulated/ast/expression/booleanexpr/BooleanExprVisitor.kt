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

package io.github.oxisto.reticulated.ast.expression.booleanexpr

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.operator.OperatorVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This visitor is called for all boolean expressions.
 *
 */
class BooleanExprVisitor(val scope: Scope) : Python3BaseVisitor<BaseBooleanExpr>() {
  /**
   * The visitor for an or_expr.
   * It´s EBNF representation is:
   *      or_expr ::= xor_expr | or_expr "|" xor_expr
   * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#binary-bitwise-operations}]
   */
  override fun visitExpr(ctx: Python3Parser.ExprContext): BaseBooleanExpr {
    val getXorExprByPosition = { position: Int ->
      ctx
        .getChild(position)
        .accept(this)
    }
    return if (ctx.childCount == 3)
      OrExpr(
        ctx.getChild(0)
          .accept(this) as BaseBooleanExpr,
        getXorExprByPosition(2)
      )
    else getXorExprByPosition(0)
  }

  /**
   * The visitor for an xor_expr.
   * It´s EBNF representation is:
   *      xor_expr ::= and_expr | xor_expr "^" and_expr
   * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#binary-bitwise-operations}]
   */
  override fun visitXor_expr(ctx: Python3Parser.Xor_exprContext): BaseBooleanExpr {
    val getAndExprByPosition = { position: Int ->
      ctx.getChild(position)
        .accept(this)
    }
    return if (ctx.childCount == 3)
      XorExpr(
        ctx.getChild(0)
          .accept(this) as BaseBooleanExpr, getAndExprByPosition(2)
      )
    else getAndExprByPosition(0)
  }

  /**
   * The visitor for an and_expr.
   * It´s EBNF representation is:
   *      and_expr ::= shift_expr | and_expr "&" shift_expr
   * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#binary-bitwise-operations}]
   */
  override fun visitAnd_expr(ctx: Python3Parser.And_exprContext): BaseBooleanExpr {
    val getShiftExprByPosition = { position: Int ->
      ctx.getChild(position)
        .accept(
          OperatorVisitor(
            this.scope
          )
        )
    }
    return if (ctx.childCount == 3) {
      val child = ctx.getChild(0)
      val andExpr = if (child is Python3Parser.And_exprContext) {
        child.accept(this) as BaseBooleanExpr
      } else {
        child.accept(
          OperatorVisitor(
            this.scope
          )
        )
      }
      val shiftExpr = getShiftExprByPosition(2)
      AndExpr(andExpr, shiftExpr)
    } else getShiftExprByPosition(0)
  }
}
