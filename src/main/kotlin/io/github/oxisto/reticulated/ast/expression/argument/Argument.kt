package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * This class represents the argument.
 * It has the EBNF representation: argument ::= expression
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
open class Argument(val expression: Expression) : Node() {
    init {
        expression.parent = this
    }
}