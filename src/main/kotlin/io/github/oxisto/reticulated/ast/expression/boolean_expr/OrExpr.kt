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

package io.github.oxisto.reticulated.ast.expression.boolean_expr

import java.lang.IllegalArgumentException

/**
 * The class represents an or_expr.
 * It´s EBNF representation is:
 *      or_expr ::= xor_expr | or_expr "|" xor_expr
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#binary-bitwise-operations}]
 */
class OrExpr(val orExpr: BaseBooleanExpr?, val xorExpr: XorExpr): BaseBooleanExpr() {

    init {
        if(orExpr != null && orExpr !is OrExpr && orExpr !is XorExpr) {
            throw IllegalArgumentException()
        }
    }

    override fun toString(): String {
        var result = "OrExpr(" + System.lineSeparator() + "\t"
        if (orExpr != null) {
            result += "orExpr=$orExpr | xorExpr=$xorExpr"
        }
        result += "xorExpr=$xorExpr" + System.lineSeparator() +
                ")"
        return result
    }
}