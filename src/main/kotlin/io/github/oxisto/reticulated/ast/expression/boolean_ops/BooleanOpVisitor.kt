package io.github.oxisto.reticulated.ast.expression.boolean_ops

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class BooleanOpVisitor(val scope: Scope) : Python3BaseVisitor<BaseBooleanOp>() {
    override fun visitOr_test(ctx: Python3Parser.Or_testContext): BaseBooleanOp {
        // TODO: implement
        return super.visitOr_test(ctx)
    }

    override fun visitAnd_test(ctx: Python3Parser.And_testContext): BaseBooleanOp {
        // TODO: implement
        return super.visitAnd_test(ctx)
    }

    override fun visitNot_test(ctx: Python3Parser.Not_testContext): BaseBooleanOp {
        // TODO: implement
        return super.visitNot_test(ctx)
    }
}