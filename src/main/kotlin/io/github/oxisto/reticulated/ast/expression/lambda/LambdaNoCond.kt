package io.github.oxisto.reticulated.ast.expression.lambda

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.ExpressionNoCond
import io.github.oxisto.reticulated.ast.statement.ParameterList

class LambdaNoCond(val parameterList: ParameterList?, val exprNoCond: ExpressionNoCond): Node() {
    override fun toString(): String {
        var result = "lambda "
        if ( parameterList != null ) {
            result += "parameterList=$parameterList "
        }
        result += ": exprNoCond=$exprNoCond"
        return "LambdaNoCond(lambdaNoCond=$result)"
    }
}