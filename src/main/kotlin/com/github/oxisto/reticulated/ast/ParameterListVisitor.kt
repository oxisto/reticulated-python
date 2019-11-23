package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Identifier
import com.github.oxisto.reticulated.ast.statement.Parameter
import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class ParameterListVisitor(val scope: Scope) : Python3BaseVisitor<List<Parameter>>() {

  override fun visitTypedargslist(ctx: Python3Parser.TypedargslistContext?): List<Parameter> {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val list = ArrayList<Parameter>()

    // loop through the children
    for(tree in ctx.children)
    {
      // skip commas, etc.
      if(tree is TerminalNode)
      {
        continue;
      }

      val id = tree.accept(IdentifierVisitor(this.scope))

      // create parameter
      val parameter = Parameter(id)

      list.add(parameter)
    }

    return list;
  }

}