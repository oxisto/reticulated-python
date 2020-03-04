package io.github.oxisto.reticulated.ast.expression.literal

class StringLiteral(val value: String) : Literal<String>() {
    override fun toString(): String {
        return value
    }
}