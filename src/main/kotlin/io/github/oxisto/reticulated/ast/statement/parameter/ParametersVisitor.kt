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

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNodeImpl

class ParametersVisitor(val scope: Scope) : Python3BaseVisitor<Parameters>() {

  override fun visitParameters(ctx: Python3Parser.ParametersContext?): Parameters {
    if (ctx?.OPEN_PAREN() == null ||
      ctx.CLOSE_PAREN() == null
    ) {
      super.visitParameters(ctx)
    }

    ctx?.typedargslist()?.let { return it.accept(this) }

    return Parameters()
  }

  override fun visitTypedargslist(ctx: Python3Parser.TypedargslistContext): Parameters {
    return handleArgList(ctx)
  }

  override fun visitVarargslist(ctx: Python3Parser.VarargslistContext): Parameters {
    return handleArgList(ctx)
  }

  private fun handleArgList(ctx: ParserRuleContext): Parameters {
    val list = mutableListOf<Parameter>()

    // loop through the children
    var index = 0
    while (index < ctx.childCount) {
      var parameter: Parameter

      if (ctx.getChild(index) is TerminalNodeImpl) {
        parameter = ctx.getChild(index + 1).accept(ParameterVisitor(this.scope))
        if (ctx.getChild(index).text == "*") {
          parameter.star = Parameter.StarType.STAR
        } else {
          parameter.star = Parameter.StarType.DOUBLE_STAR
        }
        index += 3
      } else {
        parameter = ctx.getChild(index).accept(ParameterVisitor(this.scope))
        index += 2
      }

      list.add(parameter)
    }

    return Parameters(list)
  }
}
