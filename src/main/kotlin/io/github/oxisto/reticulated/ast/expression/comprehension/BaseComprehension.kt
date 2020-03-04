package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.expression.CallTrailer

abstract class BaseComprehension: CallTrailer(){
    abstract override fun toString(): String
}