/*
 * Copyright (c) 2020, Fraunhofer AISEC. All rights reserved.
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
import io.github.oxisto.reticulated.ast.expression.literal.*
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class AtomVisitor(val scope: Scope) : Python3BaseVisitor<Atom>() {

  override fun visitAtom(ctx: Python3Parser.AtomContext): Atom {
  // TODO: check if it is a enclosure
    val atom = ctx.getChild(0).accept(this)

    return atom
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
      intOrNull != null -> {
        Integer(intOrNull)
      }
      text.startsWith("\"") || text.startsWith("\'") -> {
        StringLiteral(
            text.replace("\"", "")
                .replace("\'", "")
        )
      }
      floatOrNull != null -> {
        FloatNumber(floatOrNull)
      }
      isImagNumber -> {
        val textWithoutLast = text.substring(0, text.lastIndex)
        val intOrNullOfImag = textWithoutLast.toIntOrNull()
        val floatOrNullOfImag = textWithoutLast.toFloatOrNull()
        when {
          intOrNullOfImag != null -> {
            ImagNumber(null, Integer(intOrNullOfImag))
          }
          floatOrNullOfImag != null -> {
            ImagNumber(FloatNumber(floatOrNullOfImag), null)
          }
          else -> {
            throw CouldNotParseException()
          }
        }
      }
      byteValue != null -> {
        BytesLiteral(byteValue)
      }
      else -> {
        Identifier(node.text)
      }
    }
  }

}