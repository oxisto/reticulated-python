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

}