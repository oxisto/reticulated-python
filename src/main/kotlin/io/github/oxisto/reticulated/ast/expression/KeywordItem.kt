package io.github.oxisto.reticulated.ast.expression

/**
 * This class represents the keyword_item.
 * It has the EBNF representation: keyword_item ::= identifier "=" expression
 * [see: https://docs.python.org/3/reference/expressions.html#calls]
 */
class KeywordItem(val identifier: Identifier, expression: Expression): KeywordArgument(expression) {

    init {
        identifier.parent = this
        expression.parent = this
    }
}