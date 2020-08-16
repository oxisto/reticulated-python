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

package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNodeImpl

/**
 * This visitor is called for an argument_list.
 * The EBNF notation is:
 *      argument_list ::=
 *          positional_arguments [ "," starred_and_keywords ] [ "," keyword_arguments ] |
 *          starred_and_keywords [ "," keyword_arguments ] |
 *          keyword_arguments
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 *
 */
class ArgumentListVisitor(val scope: Scope) : Python3BaseVisitor<Arguments>() {

  override fun visitArglist(ctx: Python3Parser.ArglistContext): Arguments {
    val arguments = mutableListOf<Expression>()

    // loop through children
    for (tree in ctx.children) {
      if (tree !is TerminalNodeImpl) {
        arguments.add(
            tree.accept(
                ArgumentVisitor(
                    this.scope
                )
            )
        )
      }
    }

    return Arguments(arguments)
  }
}
