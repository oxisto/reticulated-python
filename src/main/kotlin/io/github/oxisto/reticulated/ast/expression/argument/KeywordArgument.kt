package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * This class represents a keyword_argument (It is not explicit part of the lang spec).
 * It has the EBNF representation:
 *      keyword_arguments ::= keyword_argument ( "," keyword_argument )*
 *      keyword_argument  ::= keyword_item | kwarg
 *      keyword_item      ::= identifier "=" expression
 *      kwarg             ::= "**" expression
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
abstract class KeywordArgument(expression: Expression) : Argument(expression) {
    init {
        super.expression.parent = this
    }

}