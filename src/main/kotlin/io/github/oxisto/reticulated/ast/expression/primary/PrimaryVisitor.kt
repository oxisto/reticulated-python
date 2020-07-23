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

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.argument.CallTrailerVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.primary.slice.*
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class PrimaryVisitor(val scope: Scope): Python3BaseVisitor<Primary>() {

  /**
   * It is probably a primary
   *
   */
  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext): Primary {

    return if (ctx.childCount == 1) {
      // It is an atom
      // TODO: check if an atom really have in every case a childCount from 1

      ctx.getChild(0)
          .accept(
              AtomVisitor(
                  this.scope
              )
          )
    } else {
      // first child is the primary
      val primary = if (ctx.getChild(0) is Python3Parser.AtomContext)
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
      val trailer = ctx.getChild(Python3Parser.TrailerContext::class.java, 0)

      if ( trailer.childCount == 2 ) {
        if (trailer.getChild(TerminalNode::class.java, 0).text == "." ) {
          // it is an attribute ref, parse the identifier

          var attributeRef = primary
          for (index in 1 until trailer.childCount) {
            val id = trailer.getChild(index).accept(AtomVisitor(this.scope)) as Identifier
            attributeRef = AttributeRef(attributeRef, id)
          }

          attributeRef
        } else if(
            trailer.getChild(0).text == "(" &&
            trailer.getChild(1).text == ")"
        ) {
          // It is a call without arguments
          // parse the trailer
          val argumentList =
              trailer.accept(
                  CallTrailerVisitor(
                      this.scope
                  )
              )
          Call(primary, argumentList)
        } else {
          throw Exception("could not parse")
        }
      } else if (trailer.childCount == 3) {
        when (trailer.getChild(TerminalNode::class.java, 0).text) {
          "(" -> {
            // it is a call

            // parse the trailer
            val argumentList =
                trailer.accept(
                    CallTrailerVisitor(
                        this.scope
                    )
                )

            Call(primary, argumentList)
          }
          "[" -> {
            // it is s Slicing or a subscription. Parse all trailers
            var res = primary
            for (index in 1 until ctx.childCount) {
              res = Slicing(
                  res,
                  ctx.getChild(index).getChild(1).accept(this) as SliceList
                  )
            }
            res
          }
          else -> {
            throw CouldNotParseException()
          }
        }
      } else {
        throw CouldNotParseException()
      }
    }
  }

  override fun visitSubscriptlist(ctx: Python3Parser.SubscriptlistContext): Primary {
    val sliceItems = ArrayList<SliceItem>()
    for (index in 0 until ctx.childCount)
      sliceItems.add(ctx.getChild(index).accept(this) as SliceItem)
    return SliceList(sliceItems)
  }

  override fun visitSubscript(ctx: Python3Parser.SubscriptContext): Primary {
    return if (ctx.childCount == 1 && ctx.getChild(0) is Python3Parser.TestContext)
      SliceItem(
          ctx.getChild(0)
              .accept(ExpressionVisitor(this.scope)),
          null
      )
    else SliceItem(null, handleProperSlice(ctx))
  }

  override fun visitSliceop(ctx: Python3Parser.SliceopContext): Primary {
    if (ctx.getChild(0).text != ":")
      throw CouldNotParseException("Expected a stride but got: " + ctx.getChild(0) + ".")
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

  private fun getLowerBound(ctx: Python3Parser.SubscriptContext): LowerBound? {
    var lowerBound: LowerBound? = null
    if (ctx.getChild(0) is Python3Parser.TestContext)
      lowerBound = LowerBound(ctx.getChild(0).accept(ExpressionVisitor(this.scope)))
    return lowerBound
  }

  private fun getUpperBound(ctx: Python3Parser.SubscriptContext): UpperBound? {
    var upperBound: UpperBound? = null
    if (ctx.childCount >= 2 && ctx.getChild(1) is Python3Parser.TestContext)
        upperBound = UpperBound(ctx.getChild(1).accept(ExpressionVisitor(this.scope)))
    if (ctx.childCount >= 3 &&
        ctx.getChild(0) is Python3Parser.TestContext &&
        ctx.getChild(2) is Python3Parser.TestContext)
      upperBound = UpperBound(ctx.getChild(2).accept(ExpressionVisitor(this.scope)))
    return upperBound
  }
}
