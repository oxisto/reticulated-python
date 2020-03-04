package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.CallTrailer
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.comprehension.CompFor
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.comprehension.ComprehensionVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class CallTrailerVisitor(val scope: Scope) : Python3BaseVisitor<CallTrailer>() {

  /**
   * It is the trailer form the call in the EBNF form:
   *      call ::= primary call_trailer
   *      call_trailer ::= "(" [ argument_list [","] | comprehension ] ")"
   * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls }]
   */
  override fun visitTrailer(ctx: Python3Parser.TrailerContext): CallTrailer {

    // TODO: when does it have != 3 children
    return if (ctx.childCount == 3) {

      // second child should be the argument list
      val trailer = ctx.getChild(1)
      if ( trailer.childCount == 1 ){

        val argumentContext = trailer.getChild(0)
        if ( argumentContext.childCount == 2 ) {
          // The trailer is a comprehension
          val expression: Expression = argumentContext.getChild(0).accept(ExpressionVisitor(this.scope))
          val compFor: CompFor = argumentContext.getChild(1).accept(ComprehensionVisitor(this.scope)) as CompFor
          Comprehension(expression, compFor)
        }
      }
      // The trailer is a argument_list
      trailer.accept(
              ArgumentListVisitor(
                      this.scope
              )
      )
    } else {
      ArgumentList()
    }
  }
}