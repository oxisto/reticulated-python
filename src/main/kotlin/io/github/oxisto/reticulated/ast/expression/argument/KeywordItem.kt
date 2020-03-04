package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.Identifier

/**
 * This class represents the keyword_item.
 * It has the EBNF representation: keyword_item ::= identifier "=" expression
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
class KeywordItem(val identifier: Identifier, expression: Expression): KeywordArgument(expression) {
    init {
        identifier.parent = this
        super.expression.parent = this
    }
}