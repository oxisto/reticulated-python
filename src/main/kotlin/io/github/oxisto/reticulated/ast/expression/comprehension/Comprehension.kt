package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.expression.Expression

class Comprehension(val expression: Expression, val compFor: CompFor) : BaseComprehension() {

    override fun toString(): String {
        return "Comprehension(expression=$expression compFor=$compFor)"
    }
}