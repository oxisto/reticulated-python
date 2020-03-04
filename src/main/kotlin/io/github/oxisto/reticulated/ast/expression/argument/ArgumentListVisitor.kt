package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.EmptyContextException
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