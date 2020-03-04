package io.github.oxisto.reticulated.ast.simple.target

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.simple.target.TargetList
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNodeImpl

class TargetListVisitor(val scope: Scope) : Python3BaseVisitor<TargetList>() {

  override fun visitExprlist(ctx: Python3Parser.ExprlistContext): TargetList {
    val targets = ArrayList<Target>()
    for(child in ctx.children){
      if(child !is TerminalNodeImpl) {
        targets.add(
                child.accept(
                        TargetVisitor(
                                this.scope
                        )
                )
        )
      }
    }
    return TargetList(targets)
  }

}