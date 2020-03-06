/*
 * Copyright (c) 2019, Fraunhofer AISEC. All rights reserved.
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

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import org.antlr.v4.runtime.tree.TerminalNode

class IdentifierVisitor(val scope: Scope) : Python3BaseVisitor<Identifier>() {

  /**
   * see visitTerminal in the ExpressionVisitor
   */
  override fun visitTerminal(node: TerminalNode): Identifier {

    // TODO: literals and stuff
    // TODO: functions without "primary." identifier are not included

    // return super.visitTerm(ctx)

    val id = Identifier(node.text)

    return id
  }

}