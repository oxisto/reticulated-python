package io.github.oxisto.reticulated.ast.expression.literal

class FloatNumber(val value: Float) : Literal<Float>() {

    override fun toString(): String {
        return "FloatNumber(value=$value)"
    }
}