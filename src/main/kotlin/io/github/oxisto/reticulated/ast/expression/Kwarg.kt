package io.github.oxisto.reticulated.ast.expression

/**
 * This class represents the kwarg (not explicit part of the language spec).
 * It has the EBNF representation: **expression
 * [see: https://docs.python.org/3/reference/expressions.html#calls]
 */
open class Kwarg(expression: Expression) : KeywordArgument(expression) {
    init {
        expression.parent = this
    }

}