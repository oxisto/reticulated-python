package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.expression.IdentifierVisitor
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.ParserRuleContext

class ParameterVisitor(val scope: Scope) : Python3BaseVisitor<Parameter>() {

  override fun visitTfpdef(ctx: Python3Parser.TfpdefContext): Parameter {
    return handleParameter(ctx)
  }

  override fun visitVfpdef(ctx: Python3Parser.VfpdefContext): Parameter {
    return handleParameter(ctx)
  }

  private fun handleParameter(ctx: ParserRuleContext): Parameter {
    return if (ctx.childCount == 3) {
      // only the first one
      val id = ctx.getChild(0).accept(
              IdentifierVisitor(
                      this.scope
              )
      )

      // third one is the type
      val expression = ctx.getChild(2).accept(
              ExpressionVisitor(
                      this.scope
              )
      )

      Parameter(id, expression)
    } else {
      // only the first one
      val id = ctx.getChild(0).accept(
              IdentifierVisitor(
                      this.scope
              )
      )
      Parameter(id)
    }
  }
}