package io.github.oxisto.reticulated.ast.expression.call

import io.github.oxisto.reticulated.ast.expression.call.CallTrailer

class EmptyCallTrailer: CallTrailer() {
    override fun toString(): String {
        return "EmptyCallTrailer( '(' ')' )"
    }
}