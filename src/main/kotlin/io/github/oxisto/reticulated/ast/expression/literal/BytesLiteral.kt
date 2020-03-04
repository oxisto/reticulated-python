package io.github.oxisto.reticulated.ast.expression.literal

import io.github.oxisto.reticulated.ast.expression.literal.Literal

class BytesLiteral(val value: Array<Byte>) : Literal<Array<Byte>>() {
    override fun toString(): String {
        return "BytesLiteral(value=$value)"
    }

}