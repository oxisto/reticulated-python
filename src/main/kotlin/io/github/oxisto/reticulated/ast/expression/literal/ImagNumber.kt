package io.github.oxisto.reticulated.ast.expression.literal

import io.github.oxisto.reticulated.ast.Node
import java.lang.IllegalArgumentException

class ImagNumber(val floatNumber:FloatNumber?, val integer:Integer?) : Literal<ImagNumber>() {
    init {
        if(floatNumber == null){
            if(integer == null) {
                throw IllegalArgumentException()
            }
        }else {
            if(integer != null) {
                throw IllegalArgumentException()
            }
        }
    }

    override fun toString():String {
        val result:String
        if(floatNumber == null){
            result = "value=$integer j"
        }else{
            result = "value=$floatNumber j"
        }
        return "ImagNumber($result)"
    }

}