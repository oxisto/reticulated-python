package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.EmptyContextException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class ArgumentListVisitor(val scope: Scope) : Python3BaseVisitor<ArgumentList>() {

  /**
   * It is the trailer form the call in the form of: "(" [ argument_list [","] | comprehension ] ")"
   */
  override fun visitTrailer(ctx: Python3Parser.TrailerContext?): ArgumentList {
    if (ctx == null) {
      throw EmptyContextException()
    }

    return if (ctx.childCount == 3) {
      // TODO: Handle comprehension

      // second child should be the argument list
      ctx.getChild(1).accept(this)
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