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

package io.github.oxisto.reticulated.ast.expression.primary

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.ExpressionList
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentListVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.primary.slice.*
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

/**
 * this class offers visitors for all primaries.
 */
class PrimaryVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {

  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext): Primary {
    return if (ctx.childCount == 1) {
      // It is an atom
      ctx.getChild(0)
          .accept(
              AtomVisitor(
                  this.scope
              )
          ) as Primary
    } else {
      // first child is the primary
      var primary = if (ctx.getChild(0) is Python3Parser.AtomContext)
        ctx.getChild(0)
            .accept(
                AtomVisitor(
                    this.scope
                )
            ) as Primary
      else
        ctx.getChild(0)
            .accept(this) as Primary

      // The other children are one out of: attributeref | subscription | slicing | call
      // All of them have a primary as the first child

      // check the trailer and decide what it is
      val trailer = ctx.getChild(ctx.childCount - 1)

      when (trailer.getChild(0).text) {
        "(" -> {
          // it is a call
          for (index in 1 until ctx.childCount - 1)
            primary = AttributeRef(
                primary,
                ctx.getChild(index)
                    .getChild(1)
                    .accept(
                        AtomVisitor(this.scope)
                    ) as Identifier
            )
          val argumentList =
              trailer.accept(
                  ArgumentListVisitor(
                      this.scope
                  )
              )
          Call(primary, argumentList)
        }
        "." -> {
          // it is an attribute ref, parse the identifier
          val id = trailer.getChild(1)
              .accept(AtomVisitor(this.scope)) as Identifier
          AttributeRef(primary, id)

        }
        else -> { // "["
          // it is s Slicing or a subscription. Parse all trailers
          var res = primary
          for (index in 1 until ctx.childCount) {
            val indices = ctx.getChild(index).getChild(1).accept(this)
            res = if (indices is SliceList || indices is ProperSlice)    // It is a Slicing
              Slicing(res, indices as Primary)
            else Subscription(res, indices as Expression)    // It is a Subscription
          }
          res
        }
      }
    }
  }

  override fun visitSubscriptlist(ctx: Python3Parser.SubscriptlistContext): Expression {
    val sliceItems = ArrayList<Expression>()
    var containsProperSlice = false
    for (index in 0 until ctx.childCount step 2) {
      val sliceItem = ctx.getChild(index).accept(this)
      if (sliceItem is ProperSlice)
        containsProperSlice = true
      sliceItems.add(sliceItem)
    }
    return when {
      sliceItems.size == 1 -> sliceItems[0]
      containsProperSlice -> SliceList(sliceItems)
      else -> ExpressionList(sliceItems)
    }
  }

  override fun visitSubscript(ctx: Python3Parser.SubscriptContext): Primary {
    return if (ctx.childCount == 1 && ctx.getChild(0) is Python3Parser.TestContext)
      ctx.getChild(0)
          .accept(ExpressionVisitor(this.scope)) as Primary
    else handleProperSlice(ctx)
  }

  override fun visitSliceop(ctx: Python3Parser.SliceopContext): Primary {
    var expression: Expression? = null
    if (ctx.childCount == 2)
      expression = ctx.getChild(1).accept(ExpressionVisitor(this.scope))
    return Stride(expression)
  }

  private fun handleProperSlice(ctx: Python3Parser.SubscriptContext): ProperSlice {
    var stride: Stride? = null
    if (ctx.getChild(ctx.childCount - 1) is Python3Parser.SliceopContext)
      stride = ctx.getChild(ctx.childCount - 1).accept(this) as Stride
    return ProperSlice(getLowerBound(ctx), getUpperBound(ctx), stride)
  }

  private fun getLowerBound(ctx: Python3Parser.SubscriptContext): Expression? {
    var lowerBound: Expression? = null
    if (ctx.getChild(0) is Python3Parser.TestContext)
      lowerBound = ctx.getChild(0).accept(ExpressionVisitor(this.scope))
    return lowerBound
  }

  private fun getUpperBound(ctx: Python3Parser.SubscriptContext): Expression? {
    var upperBound: Expression? = null
    if (ctx.childCount >= 2 && ctx.getChild(1) is Python3Parser.TestContext)
      upperBound = ctx.getChild(1).accept(ExpressionVisitor(this.scope))
    if (ctx.childCount >= 3 &&
        ctx.getChild(0) is Python3Parser.TestContext &&
        ctx.getChild(2) is Python3Parser.TestContext)
      upperBound = ctx.getChild(2).accept(ExpressionVisitor(this.scope))
    return upperBound
  }
}
