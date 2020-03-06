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

package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNodeImpl

class ArgumentListVisitor(val scope: Scope): Python3BaseVisitor<ArgumentList>() {

    override fun visitArglist(ctx: Python3Parser.ArglistContext): ArgumentList {

        val arguments = ArrayList<Argument>()

        // loop through children
        for (tree in ctx.children) {
            if(tree !is TerminalNodeImpl) {
                arguments.add(
                        tree.accept(
                                ArgumentVisitor(
                                        this.scope
                                )
                        )
                )
            }
        }

        return ArgumentList(arguments)
    }
}