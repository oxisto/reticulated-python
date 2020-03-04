package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.lambda.LambdaNoCond
import java.lang.IllegalArgumentException

class ExpressionNoCond(val orTest: OrTest?, val lambdaNoCond: LambdaNoCond?): Node(){
    init {
        if ( orTest == null ) {
            if ( lambdaNoCond == null ) {
                throw IllegalArgumentException()
            }
        } else {
            if ( lambdaNoCond != null) {
                throw IllegalArgumentException()
            }
        }
    }

    override fun toString(): String {
        val result:String
        if(orTest == null){
            result = "lambdaNoCond=$lambdaNoCond"
        } else {
            result = "ortest=$orTest"
        }
        return "ExpressionNoCond(result)";
    }

}