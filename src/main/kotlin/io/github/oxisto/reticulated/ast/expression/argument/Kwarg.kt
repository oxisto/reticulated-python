package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * This class represents the kwarg (not explicit part of the language spec).
 * It has the EBNF representation: **expression
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
open class Kwarg(expression: Expression) : KeywordArgument(expression) {
    init {
        super.expression.parent = this
    }

}