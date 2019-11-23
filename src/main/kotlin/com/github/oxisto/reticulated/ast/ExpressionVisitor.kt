package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Call
import com.github.oxisto.reticulated.ast.expression.Expression
import com.github.oxisto.reticulated.ast.expression.Identifier
import com.github.oxisto.reticulated.ast.expression.Primary
import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser

class ExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {

  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext?): Expression {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // TODO: can be different things
    //return super.visitAtom_expr(ctx)

    return if(ctx.childCount == 1)
    {
      ctx.getChild(0).accept(this)
    } else {

      // first child is the primary
      val primary = ctx.getChild(0).accept(this) as Primary

      // create a call
      val call = Call(primary)

      // TODO: parse arguments of trailer

      call
    }
  }

  override fun visitAtom(ctx: Python3Parser.AtomContext?): Expression {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // lets just return an identifier for now
    val id = Identifier(ctx.text)

    return id
  }



}