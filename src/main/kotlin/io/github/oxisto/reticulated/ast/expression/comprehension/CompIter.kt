package io.github.oxisto.reticulated.ast.expression.comprehension

import java.lang.IllegalArgumentException

class CompIter(val compFor: CompFor?, val compIf: CompIf?) : BaseComprehension() {
    init {
        if(compFor == null){
            if(compIf == null){
                throw IllegalArgumentException()
            }
        } else {
            if ( compIf != null ) {
                throw IllegalArgumentException()
            }
        }
    }

    constructor(compFor: CompFor) : this(compFor, null)

    constructor(compIf: CompIf) : this(null, compIf)

    override fun toString(): String {
        val result:String
        if(compFor == null){
            result = "compIf=$compIf"
        }else{
            result = "compFor=$compFor"
        }
        return "CompIter($result)"
    }
}