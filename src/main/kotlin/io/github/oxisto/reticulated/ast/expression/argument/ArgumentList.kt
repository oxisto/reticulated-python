/*
 * Copyright (c) 2020, Christian Banse and Andreas Hager. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.primary.call.CallTrailer

/**
 * This class represents the argument_list.
 * It has the EBNF representation (It is not exactly the same as in the python spec, but logically equivalent):
 *    argument_list ::= positional_arguments ["," starred_and_keyword] ["," keyword_arguments] |
 *                      starred_and_keywords ["," keyword_arguments] | keyword_arguments
 *    starred_and_keywords ::= ( positional_argument | keyword_item ) ( "," positional_argument | "," keyword_item )*
 *    positional_arguments ::= ( positional_argument | argument ) ( "," positional_argument | "," argument )*
 *    keyword_arguments ::= keyword_argument ( "," keyword_argument )*
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 *
 */
class ArgumentList(private val arguments: List<Expression> = ArrayList()) : CallTrailer(), Container<Expression> {

  override val children get() = this.arguments

  override fun toString(): String {
    return "ArgumentList(" + System.lineSeparator() +
        "\targuments=$arguments" + System.lineSeparator() +
        ")"
  }
}