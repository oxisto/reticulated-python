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

package io.github.oxisto.reticulated.ast.statement.parameter

import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.ParserRuleContext

class ParameterVisitor(val scope: Scope) : Python3BaseVisitor<BaseParameter>() {

  override fun visitTfpdef(ctx: Python3Parser.TfpdefContext): BaseParameter {
    return handleParameter(ctx)
  }

  override fun visitVfpdef(ctx: Python3Parser.VfpdefContext): BaseParameter {
    return handleParameter(ctx)
  }

  private fun handleParameter(ctx: ParserRuleContext): BaseParameter {
    val id = ctx.getChild(0).accept(
        AtomVisitor(
            this.scope
        )
    ) as Identifier

    return if (ctx.childCount == 3) {
      // third one is the type
      val expression = ctx.getChild(2).accept(
              ExpressionVisitor(
                      this.scope
              )
      )

      Parameter(id, expression)
    } else id
  }
}