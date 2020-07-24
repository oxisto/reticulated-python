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


package io.github.oxisto.reticulated.ast.expression.starred

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.booleanexpr.BooleanExprVisitor
import io.github.oxisto.reticulated.ast.expression.booleanexpr.OrExpr
import io.github.oxisto.reticulated.ast.expression.comprehension.BaseComprehension
import io.github.oxisto.reticulated.ast.expression.comprehension.CompFor
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.Enclosure
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class StarredVisitor(val scope: Scope) : Python3BaseVisitor<Starred>() {

  override fun visitTestlist_comp(ctx: Python3Parser.Testlist_compContext): Starred {
    val list = ArrayList<StarredItem>()
    for (index in 0 until ctx.childCount step 2) {
      if (ctx.getChild(index).childCount == 1)
        list.add(
            StarredItem(
                ctx.getChild(index)
                    .accept(
                        ExpressionVisitor(this.scope)
                    ),
                null
            )
        )
      else if (ctx.childCount == 2 && ctx.getChild(0).text == "*")
        list.add(
            StarredItem(
                null,
                ctx.getChild(index)
                    .accept(
                      BooleanExprVisitor(this.scope)
                    ) as OrExpr
            )
        )
      else
        throw CouldNotParseException("A StarredItem should be a Expression or a \"*\" OrExpr.")
    }
    return StarredList(list)
  }

  override fun visitStar_expr(ctx: Python3Parser.Star_exprContext): Starred {
    throw NotImplementedError("Currently not Implemented")
  }

  override fun visitTestlist_star_expr(ctx: Python3Parser.Testlist_star_exprContext): Starred {
    return if (ctx.childCount == 1 && ctx.getChild(0) is Python3Parser.TestContext)
      StarredExpression(
        ctx.getChild(0)
            .accept(
                ExpressionVisitor(
                    this.scope
                )
            )
      )
    else {
      val items = ArrayList<StarredItem>()
      for (index in 0 until ctx.childCount)
        items.add(ctx.getChild(index).accept(this) as StarredItem)
      StarredExpression(null, items)
    }
  }

}