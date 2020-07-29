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

/**
 * This class represents an a_expr.
 * It´s EBNF representations are:
 *      a_expr ::=  m_expr | a_expr "+" m_expr | a_expr "-" m_expr
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#unary-arithmetic-and-bitwise-operations}]
 */
class AdditiveExpr(
        val additiveExpr: BaseOperator,
        val binaryOperator: BinaryOperator,
        val multiplicativeExpr: BaseOperator
): BaseOperator(){

    override fun toString(): String {
        return "AdditiveExpr(" + System.lineSeparator() +
            "\tadditiveExpr=$additiveExpr " +
            "binaryOperator=$binaryOperator " +
            "multiplicativeExpr=$multiplicativeExpr" +
            System.lineSeparator() +
            ")"
    }

}