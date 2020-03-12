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
 * This class represents an or_test.
 * It´s EBNF definition is:
 *      or_test ::= and_test | or_test "or" and_test
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#boolean-operations}]
 */
class OrTest(val orTest: BaseBooleanOp?, val andTest:AndTest): BaseBooleanOp() {

    init {
        if(orTest != null && orTest !is OrTest && orTest !is AndTest){
            throw CouldNotParseException()
        }
    }

    override fun toString():String {
        var result  = "OrTest(" + System.lineSeparator() + "\t"
        if(orTest !== null) {
            result += "orTest=$orTest or "
        }
        result += "andTest=$andTest" + System.lineSeparator() +
                ")"
        return result
    }
}