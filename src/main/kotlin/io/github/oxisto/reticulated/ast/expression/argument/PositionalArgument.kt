/*
 * Copyright (c) 2020, Fraunhofer AISEC. All rights reserved.
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
    override fun toString(): String{
        return "PositionalArgument(" + System.lineSeparator() +
                "positionalArgument=*$expression" + System.lineSeparator() +
                ")"
    }
}
