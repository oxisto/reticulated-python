package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class ListVisitor(val scope: Scope) : Python3BaseVisitor<List>() {

  override fun visitTestlist_comp(ctx: Python3Parser.Testlist_compContext): List {
    val elts = mutableListOf<Expression>()

    for (tree in ctx.children) {
      if (tree is TerminalNode) {
        continue
      }

      elts.add(tree.accept(ExpressionVisitor(scope)))
    }

    return List(elts)
  }
}
