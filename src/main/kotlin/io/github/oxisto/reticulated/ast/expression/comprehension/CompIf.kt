package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.expression.ExpressionNoCond

class CompIf(val expressionNoCond: ExpressionNoCond, val compIter: CompIter? = null) : BaseComprehension() {
    override fun toString(): String {
        var result = "if expressionNoCond=$expressionNoCond"
        if ( compIter != null )
            result += " compIter=$compIter"
        return "CompIf(compIf=$result)"
    }
}