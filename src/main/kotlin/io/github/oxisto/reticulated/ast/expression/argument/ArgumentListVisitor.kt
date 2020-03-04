package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.EmptyContextException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class ArgumentListVisitor(val scope: Scope) : Python3BaseVisitor<ArgumentList>() {

  /**
   * It is the trailer form the call in the form of: "(" [ argument_list [","] | comprehension ] ")"
   * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls }]
   */
  override fun visitTrailer(ctx: Python3Parser.TrailerContext?): ArgumentList {
    if (ctx == null) {
      throw EmptyContextException()
    }

    return if (ctx.childCount == 3) {

      // second child should be the argument list
      val trailer = ctx.getChild(1)
      if ( trailer.childCount == 1 ){

        val argumentContext = trailer.getChild(0)
        if ( argumentContext.childCount == 2 ) {
          // The trailer is a comprehension
          argumentContext.accept(ComprehensionVisitor(this.scope))
        }
      }
      // The trailer is a argument_list
      trailer.accept(this)
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