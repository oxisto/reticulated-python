package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.Node

/**
 * This class represents the argument_list.
 * It has the EBNF representation (It is not exactly the same as in the python spec, but logically equivalent):
 *    argument_list ::= positional_arguments ["," starred_and_keyword] ["," keyword_arguments] |
 *                      starred_and_keywords ["," keyword_arguments] | keyword_arguments
 *    starred_and_keywords ::= ( positional_argument | keyword_item ) ( "," positional_argument | "," keyword_item )*
 *    positional_arguments ::= ( positional_argument | argument ) ( "," positional_argument | "," argument )*
 *    keyword_arguments ::= keyword_argument ( "," keyword_argument )*
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
class ArgumentList(private val arguments: List<Argument> = ArrayList()) : Node(), Container<Argument> {

  override val children get() = this.arguments

}