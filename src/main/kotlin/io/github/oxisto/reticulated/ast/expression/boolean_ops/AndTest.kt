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

package io.github.oxisto.reticulated.ast.expression.boolean_ops

import io.github.oxisto.reticulated.ast.CouldNotParseException

/**
 * This class represents an and_test.
 * It´s EBNF definition is:
 *      and_test ::= not_test | and_test "and" not_test
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#boolean-operations}]
 */
class AndTest(val andTest:BaseBooleanOp?, val notTest:NotTest): BaseBooleanOp() {

    init {
        if ( andTest != null && andTest !is AndTest && andTest !is NotTest ) {
            throw CouldNotParseException()
        }
    }

    override fun toString(): String {
        var result = "AndTest(" + System.lineSeparator() + "\t"
        if(andTest != null) {
            result += "andTest=$andTest and "
        }
        result += "notTest=$notTest" + System.lineSeparator() +
                ")"
        return result
    }
}