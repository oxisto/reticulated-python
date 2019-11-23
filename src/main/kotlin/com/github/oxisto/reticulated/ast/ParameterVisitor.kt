package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.statement.Parameter
import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser

class ParameterVisitor(val scope: Scope) : Python3BaseVisitor<Parameter>() {

  override fun visitTfpdef(ctx: Python3Parser.TfpdefContext?): Parameter {
    if(ctx == null)
    {
      throw EmptyContextException()
    }

    return if(ctx.childCount == 3){
      // only the first one
      val id = ctx.getChild(0).accept(IdentifierVisitor(this.scope))

      // third one is the type
      val expression = ctx.getChild(2).accept(ExpressionVisitor(this.scope))

      Parameter(id, expression)
    } else {
      // only the first one
      val id = ctx.getChild(0).accept(IdentifierVisitor(this.scope))
      Parameter(id)
    }
  }
}