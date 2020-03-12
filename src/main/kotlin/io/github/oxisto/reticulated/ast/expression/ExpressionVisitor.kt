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

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.argument.CallTrailerVisitor
import io.github.oxisto.reticulated.ast.expression.boolean_ops.BooleanOpVisitor
import io.github.oxisto.reticulated.ast.expression.call.Call
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

/**
 * Think of splitting the class
 */
class ExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {

  override fun visitTest(ctx: Python3Parser.TestContext): Expression {
    if(ctx.childCount != 1){
      throw CouldNotParseException("Currently not implemented.")
    }
    // For now return a orTest
    return ctx.getChild(0).accept(BooleanOpVisitor(this.scope))
  }

  /**
   * It is probably a primary
   *
   */
  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext): Expression {

    // TODO: can be different things: attributeref | subscription | slicing
    // return super.visitAtom_expr(ctx)

    return if (ctx.childCount == 1) {
      // check if an atom really have in every case a childCount from 1
      // It is an atom
      ctx.getChild(0)
        .accept(
          AtomVisitor(
            this.scope
        )
      )
    } else {
      // first child is the primary
      val primary = ctx.getChild(0)
          .accept(
              AtomVisitor(
                  this.scope
              )
          ) as Primary

      // The other children are one out of: attributeref | subscription | slicing | call
      // All of them have a primary as the first child

      // check the trailer and decide what it is
      val trailer = ctx.getChild(Python3Parser.TrailerContext::class.java, 0)

      if ( trailer.childCount == 2 ) {
        if (trailer.getChild(TerminalNode::class.java, 0).text == "." ) {
          // it is an attribute ref, parse the identifier
          val id = trailer.getChild(1).accept(AtomVisitor(this.scope)) as Identifier

          val attributeRef = AttributeRef(primary, id)

          attributeRef
        } else if(trailer.getChild(0).text == "await") {
          // It is an await expression
          AwaitExpr(trailer.getChild(1).accept(this) as Primary)
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
      } else if (
              trailer.childCount == 3 &&
              trailer.getChild(TerminalNode::class.java, 0).text == "("
      ) {
        // it is a call

        // parse the trailer
        val argumentList =
                trailer.accept(
                        CallTrailerVisitor(
                                this.scope
                        )
                )
        // create a call

        Call(primary, argumentList)
      } else {
        throw CouldNotParseException()
      }
    }
  }


}