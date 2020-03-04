package io.github.oxisto.reticulated.ast.expression.lambda

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class LambdaNoCondVisitor(val scope: Scope): Python3BaseVisitor<LambdaNoCond>() {
    override fun visitLambdef_nocond(ctx: Python3Parser.Lambdef_nocondContext?): LambdaNoCond {
        // TODO: Implement
        return super.visitLambdef_nocond(ctx)
    }
}