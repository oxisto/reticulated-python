package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class TargetListVisitor(val scope: Scope) : Python3BaseVisitor<TargetList>() {

  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext?): TargetList {
    return super.visitAtom_expr(ctx)
  }

}