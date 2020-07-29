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
import io.github.oxisto.reticulated.ast.expression.primary.atom.AtomVisitor
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.booleanops.BooleanOpVisitor
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.primary.AttributeRef
import io.github.oxisto.reticulated.ast.expression.primary.Primary
import io.github.oxisto.reticulated.ast.expression.primary.slice.Slicing
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

/**
 * Think of splitting the class
 */
class ExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {

  override fun visitTest(ctx: Python3Parser.TestContext): Expression {
    if (ctx.childCount != 1) {
      throw CouldNotParseException("Currently not implemented.")
    }
    // TODO: check if it is a conditional Expression
    return ctx.getChild(0).accept(BooleanOpVisitor(this.scope))
  }

  override fun visitExprlist(ctx: Python3Parser.ExprlistContext): Expression {
    // TODO: Implement Expression List Visitor
    return super.visitExprlist(ctx)
  }


}