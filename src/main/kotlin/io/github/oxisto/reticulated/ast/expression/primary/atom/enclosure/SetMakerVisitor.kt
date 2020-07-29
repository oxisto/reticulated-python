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
import io.github.oxisto.reticulated.ast.expression.booleanexpr.BooleanExprVisitor
import io.github.oxisto.reticulated.ast.expression.booleanexpr.OrExpr
import io.github.oxisto.reticulated.ast.expression.comprehension.CompFor
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.comprehension.ComprehensionVisitor
import io.github.oxisto.reticulated.ast.expression.starred.Starred
import io.github.oxisto.reticulated.ast.expression.starred.StarredItem
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class SetMakerVisitor (val scope: Scope) : Python3BaseVisitor<Expression>() {
  override fun visitDictorsetmaker(ctx: Python3Parser.DictorsetmakerContext): Expression {
    return if (ctx.childCount == 4 &&
        ctx.getChild(3) is Python3Parser.Comp_forContext) {
      // It is a DictComprehension
      DictComprehension(
          ctx.getChild(0).accept(ExpressionVisitor(this.scope)),
          ctx.getChild(2).accept(ExpressionVisitor(this.scope)),
          ctx.getChild(3).accept(ComprehensionVisitor(this.scope)) as CompFor
      )
    } else if (
        ctx.childCount >= 2 &&
        (
            ctx.getChild(0).text == "**" &&
                ctx.getChild(1) is Python3Parser.TestContext ||
                ctx.getChild(0) is Python3Parser.TestContext &&
                ctx.getChild(1).text == ":"
            )
    ) {
      // it is a KeyDatumList
      handleKeyDatumList(ctx)
    } else if (ctx.childCount == 2 &&
        ctx.getChild(1) is Python3Parser.Comp_forContext)  {
      // it is a Comprehension for a set
       Comprehension(
          ctx.getChild(0).accept(ExpressionVisitor(this.scope)),
          ctx.getChild(1).accept(ComprehensionVisitor(this.scope)) as CompFor
      )
    } else {
      // it is a StarredList for a set
      val starredItems = ArrayList<Starred>()
      for(index in 0 until ctx.childCount step 2)
        starredItems.add(
            ctx.getChild(index)
                .accept(ExpressionVisitor(this.scope))
        )
      StarredList(starredItems)
    }
  }

  private fun handleKeyDatumList(ctx: Python3Parser.DictorsetmakerContext): KeyDatumList {
    val keyDatums = ArrayList<KeyDatum>()
    var index = 0
    do {
      if (ctx.getChild(index) is Python3Parser.TestContext) {
        // it is a KeyDatum : Expr ":" Expr
        keyDatums.add(
            KeyDatum(
                ctx.getChild(index)
                    .accept(
                        ExpressionVisitor(this.scope)
                    ),
                ctx.getChild(index + 2)
                    .accept(
                        ExpressionVisitor(this.scope)
                    ),
                null
            )
        )
        index += 4
      } else {
        // it is a KeyDatum : "**" OrExpr
        keyDatums.add(
            KeyDatum(
                null, null,
                ctx.getChild(index + 1)
                    .accept(
                        BooleanExprVisitor(this.scope)
                    ) as OrExpr
            )
        )
        index += 3
      }
    } while (index < ctx.childCount)
    return KeyDatumList(keyDatums)
  }
}