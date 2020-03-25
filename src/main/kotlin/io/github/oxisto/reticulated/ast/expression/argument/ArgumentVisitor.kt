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

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.*
import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This visitor is called for a single argument.
 * It is called for:
 *      normal arguments (expression),
 *      posArgs         ( "*" expression ),
 *      kwargs          ( "**" expression ),
 *      keyword_items,  ( identifier "=" expression )
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
class ArgumentVisitor(val scope: Scope) : Python3BaseVisitor<Argument>() {

  override fun visitArgument(ctx: Python3Parser.ArgumentContext): Argument {
    if(ctx.childCount < 1 || ctx.childCount > 3){
        throw CouldNotParseException("The childCount of the ctx=$ctx was not expected.")
    }
    val getExpressionByPosition = {
      positionOfTheExpression: Int -> ctx
        .getChild(positionOfTheExpression)
        .accept(
                ExpressionVisitor(
                        this.scope
                )
        )
    }

    when (ctx.childCount) {
        1 -> {
            // It is a "normal" argument with the form: expression
            val expression = getExpressionByPosition(0)
            return Argument(expression)
        }
        2 -> {

            // It is a kwarg, (parent: keyword_arguments), in the form: "**" kwargs or "*" posArgs
            val expression = getExpressionByPosition(1)
            val stars = ctx.getChild(0).text
            if(stars == "*"){
                return PositionalArgument(expression)
            } else if(stars == "**") {
                return Kwarg(expression)
            }
            throw CouldNotParseException("Firs child of $ctx does not begin with '*' or '**'.")
        }
        3 -> {
            // It is a keyword_item, (parent: keyword_arguments), in the form: identifier "=" expression
            val identifier = ctx.getChild(0).accept(
                AtomVisitor(
                    this.scope
                )
            ) as Identifier
            val expression = getExpressionByPosition(2)
            return KeywordItem(identifier, expression)
        }
        else -> throw CouldNotParseException("ctx: $ctx has ${ctx.childCount} children!")
    }
  }

}