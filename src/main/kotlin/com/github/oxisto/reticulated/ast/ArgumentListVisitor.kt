package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Argument
import com.github.oxisto.reticulated.ast.expression.ArgumentList
import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser
import com.github.oxisto.reticulated.grammar.Python3Visitor

class ArgumentListVisitor(val scope: Scope) : Python3BaseVisitor<ArgumentList>() {

  override fun visitTrailer(ctx: Python3Parser.TrailerContext?): ArgumentList {
    if (ctx == null) {
      throw EmptyContextException()
    }

    return if (ctx.childCount == 3) {
      // TODO: Handle comprehension

      // second child should be the argument list
      return ctx.getChild(1).accept(this)
    } else {
      ArgumentList()
    }

  }

  override fun visitArglist(ctx: Python3Parser.ArglistContext?): ArgumentList {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val arguments = ArrayList<Argument>()

    // loop through children
    for (tree in ctx.children) {
      arguments.add(tree.accept(ArgumentVisitor(this.scope)))
    }

    return ArgumentList(arguments)
  }
}