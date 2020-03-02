package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.EmptyContextException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import jdk.jshell.spi.ExecutionControl
import java.lang.Exception

/**
 *This visitor is called for a single argument. This could be a positional
 */
class ArgumentVisitor(val scope: Scope) : Python3BaseVisitor<Argument>() {

  override fun visitArgument(ctx: Python3Parser.ArgumentContext?): Argument {
    if (ctx == null) {
      throw EmptyContextException()
    }
    val getExpressionByPosition = {
      positionOfTheExpression: Int -> ctx
        .getChild(positionOfTheExpression)
        .accept(
          ExpressionVisitor(
            this.scope
          )
        )
    }

    when (ctx.childCount) {
        1 -> {
          // It is a "normal" argument with the form: expression
          val expression = getExpressionByPosition(0)
          return Argument(expression)
        }
        2 -> {
          // It is a kwarg, (parent: keyword_arguments), in the form: "**" kwargs or "*" posArgs
          val expression = getExpressionByPosition(1)
            return Kwarg(expression)
        }
        3 -> {
          // It is a keyword_item, (parent: keyword_arguments), in the form: identifier "=" expression
          val identifier = ctx.getChild(0).accept(
                  IdentifierVisitor(
                          this.scope
                  )
          )
          val expression = getExpressionByPosition(2)
          return KeywordItem(identifier, expression)
        }
        else -> throw Exception("Could not parse")
    }
  }

}