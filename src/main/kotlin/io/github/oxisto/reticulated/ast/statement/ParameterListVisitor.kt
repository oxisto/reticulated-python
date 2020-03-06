package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.EmptyContextException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class ParameterListVisitor(val scope: Scope) : Python3BaseVisitor<List<Parameter>>() {

  override fun visitTypedargslist(ctx: Python3Parser.TypedargslistContext?): List<Parameter> {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val list = ArrayList<Parameter>()

    // loop through the children
    for (tree in ctx.children) {
      // skip commas, etc.
      if (tree is TerminalNode) {
        continue
      }

      val parameter = tree.accept(ParameterVisitor(this.scope))

      list.add(parameter)
    }

    return list;
  }

}