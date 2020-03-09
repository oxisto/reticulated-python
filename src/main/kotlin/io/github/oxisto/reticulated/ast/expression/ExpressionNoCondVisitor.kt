package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.boolean_ops.BooleanOpVisitor
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.lambda.LambdaNoCondVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class ExpressionNoCondVisitor(val scope: Scope): Python3BaseVisitor<ExpressionNoCond>() {
    override fun visitTest_nocond(ctx: Python3Parser.Test_nocondContext): ExpressionNoCond {
        if(ctx.childCount != 1){
            throw CouldNotParseException()
        }
        val child = ctx.getChild(0)

        return if ( child is Python3Parser.Or_testContext){
            ExpressionNoCond(
                    child.accept(BooleanOpVisitor(this.scope)) as OrTest,
                    null
            )
        } else {
            if(child !is Python3Parser.Lambdef_nocondContext){
                throw CouldNotParseException(
                        "The second child of the ctx=$ctx was neither a Test_nocondContext nor a Lambdef_nocondContext."
                )
            }
            ExpressionNoCond(
                    null,
                    child.accept(
                            LambdaNoCondVisitor(
                                    this.scope
                            )
                    )
            )
        }
    }
}