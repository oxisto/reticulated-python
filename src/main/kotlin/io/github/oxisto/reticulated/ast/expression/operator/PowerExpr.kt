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

package io.github.oxisto.reticulated.ast.expression.operator

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.primary.Primary

/**
 * This class represents power (expression).
 * It´s EBNF representations are:
 *      power ::=  (await_expr | primary) ["**" u_expr]
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#unary-arithmetic-and-bitwise-operations}]
 */
class PowerExpr(
    val awaitExpr: AwaitExpr?,
    val primary: Primary?,
    val baseOperator: BaseOperator?
): BaseOperator() {
    init {
        if(awaitExpr == null) {
            if(primary == null){
                throw CouldNotParseException()
            }
        }else {
            if(primary != null) {
                throw CouldNotParseException()
            }
        }
    }

    override fun toString(): String {
        var result = "PowerExpr(" + System.lineSeparator() + "\t"
        result += if(awaitExpr == null){
            "primary=$primary"
        } else {
            "awaitExpr=$awaitExpr"
        }
        if(baseOperator != null){
            result += " power=${BinaryOperator.POWER.symbol} unaryExpr=$baseOperator"
        }
        result += System.lineSeparator() + ")"
        return result
    }
}
