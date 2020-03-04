package io.github.oxisto.reticulated.ast.expression.argument;

import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * This class represents the positional_argument.
 * It is not in the exactly the same as in the language spec,
 * because it is already in the relating visitor decidable if it is a single argument or a positional argument.
 * It has the EBNF representation: positional_argument ::= "*" expression
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
class PositionalArgument(expression: Expression) : Argument(expression) {
    init {
        super.expression.parent = this
    }
}
