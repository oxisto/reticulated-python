package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class ListVisitor(val scope: Scope) : Python3BaseVisitor<List<Identifier>>() {

  override fun visitTypedargslist(ctx: Python3Parser.TypedargslistContext?): List<Identifier> {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val list = ArrayList<Identifier>()

    // loop through the children
    for(tree in ctx.children)
    {
      // skip commas, etc.
      if(tree is TerminalNode)
      {
        continue;
      }

      val id = tree.accept(Visitor(this.scope))

      if( id is Identifier) {
        list.add(id)
      }
    }

    return list;
  }

}