package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Expression
import com.github.oxisto.reticulated.ast.expression.Identifier
import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import org.antlr.v4.runtime.tree.TerminalNode

class IdentifierVisitor(val scope: Scope) : Python3BaseVisitor<Identifier>() {

  override fun visitTerminal(node: TerminalNode?): Identifier {
    if (node == null) {
      throw EmptyContextException();
    }

    // TODO: literals and stuff
    //return super.visitTerm(ctx)

    val id = Identifier(node.text)

    return id
  }

}