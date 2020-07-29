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

package io.github.oxisto.reticulated.ast.expression.primary.atom

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.comprehension.CompFor
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.comprehension.ComprehensionVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.*
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.*
import io.github.oxisto.reticulated.ast.expression.starred.*
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

/**
 * This class offers visitors for all atoms (Identifier, Literal and Enclosure)
 */
class AtomVisitor(val scope: Scope) : Python3BaseVisitor<Atom>() {

  override fun visitAtom(ctx: Python3Parser.AtomContext): Atom {
    return when (ctx.childCount) {
      1 -> ctx.getChild(0).accept(this)
      2 -> when (ctx.getChild(0).text) { // it is a enclosure
        "(" -> ParentForm(null)
        "[" -> ListDisplay(null, null)
        else -> DictDisplay(null, null) // firsChild is "{"
      }

      else -> when (ctx.getChild(0).text) { // it is a enclosure, childCount == 3
        "(" -> {
          when {
            ctx.getChild(1).childCount == 2 -> GeneratorExpression(
                ctx.getChild(1)
                    .getChild(0)
                    .accept(
                        ExpressionVisitor(this.scope)
                    ),
                ctx.getChild(1)
                    .getChild(1)
                    .accept(
                        ComprehensionVisitor(this.scope)
                    ) as CompFor
            )
            ctx.getChild(1) is Python3Parser.Testlist_compContext -> ParentForm(
                ctx.getChild(1)
                    .accept(
                        StarredVisitor(this.scope)
                    )
            )
            else -> YieldAtom( // if (ctx.getChild(1) is Python3Parser.Yield_exprContext)
                ctx.getChild(0)
                    .accept(
                        YieldExpressionVisitor(this.scope)
                    ) as YieldExpression
            )
          }
        }

        "[" -> {
          if (ctx.getChild(1).childCount == 2 &&
              ctx.getChild(1).getChild(1) is Python3Parser.Comp_forContext)
            ListDisplay(
                null,
                Comprehension(
                    ctx.getChild(1).getChild(0)
                        .accept(
                            ExpressionVisitor(this.scope)
                        ) as Expression,
                    ctx.getChild(1).getChild(1)
                    .accept(
                        ComprehensionVisitor(this.scope)
                    ) as CompFor
                )
            )
          else
            ListDisplay(
                ctx.getChild(1)
                    .accept(
                        StarredVisitor(this.scope)
                    ),
                null
            )
        }

        else -> { //  "{"
          when (val elem = ctx.getChild(1).accept(SetMakerVisitor(this.scope))) {
            is DictComprehension -> DictDisplay(null, elem )
            is KeyDatumList ->  DictDisplay( elem,null)
            is StarredList -> SetDisplay(elem, null)
            else -> SetDisplay(null, elem as Comprehension)
          }
        }
      }
    }
  }

  override fun visitTerminal(node: TerminalNode): Atom {
    // check for some literals now
    val text = node.text

    val intOrNull = text.toIntOrNull()
    val floatOrNull = text.toFloatOrNull()
    val isImagNumber = text.length > 1 && (
        text.last() == 'J' || text.last() == 'j'
        )
    val isBytesLiteral = text.length > 2 && text.substring(0, 2) == "0x"
    val byteValue = if(isBytesLiteral) {
      text.substring(2, text.length)
          .toByteOrNull()
    } else { null }

    return when {
      intOrNull != null -> Integer(intOrNull)

      text.startsWith("\"") || text.startsWith("\'") -> {
        StringLiteral(
            text.replace("\"", "")
                .replace("\'", "")
        )
      }

      floatOrNull != null -> FloatNumber(floatOrNull)

      isImagNumber -> {
        val textWithoutLast = text.substring(0, text.lastIndex)
        val intOrNullOfImag = textWithoutLast.toIntOrNull()
        val floatOrNullOfImag = textWithoutLast.toFloatOrNull()
        if(intOrNullOfImag != null)
          ImagNumber(null, Integer(intOrNullOfImag))
        else
          ImagNumber(FloatNumber(floatOrNullOfImag as Float), null)
      }

      byteValue != null -> BytesLiteral(byteValue)

      else -> Identifier(node.text)
    }
  }
  
}