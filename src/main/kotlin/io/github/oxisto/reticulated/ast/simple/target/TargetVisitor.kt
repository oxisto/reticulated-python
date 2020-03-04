package io.github.oxisto.reticulated.ast.simple.target

import io.github.oxisto.reticulated.ast.expression.IdentifierVisitor
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.Identifier
import io.github.oxisto.reticulated.ast.expression.Name
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class TargetVisitor(val scope: Scope) : Python3BaseVisitor<Target>() {

  override fun visitAtom(ctx: Python3Parser.AtomContext): Target {
    // TODO: can be more than an identifier (see target)
    val id = ctx.getChild(0).accept(
      IdentifierVisitor(
        this.scope
      )
    )

    // convert identifier to name
    return Identifier(Name.fromIdentifier(id).name)
  }
}