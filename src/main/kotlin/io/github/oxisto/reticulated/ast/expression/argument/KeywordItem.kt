/*
 * Copyright (c) 2019, Fraunhofer AISEC. All rights reserved.
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

    override fun toString(): String{
        return "KeywordItem(" + System.lineSeparator() +
                "\tkeywordItem=($identifier=$expression)" +System.lineSeparator() +
                ")"
    }
}