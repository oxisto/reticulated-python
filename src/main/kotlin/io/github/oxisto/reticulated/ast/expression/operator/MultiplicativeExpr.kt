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
 * This class represents a m_expr.
 * It´s EBNF representations are:
 *      m_expr ::=  u_expr | m_expr "*" u_expr | m_expr "@" m_expr |
 *              m_expr "//" u_expr | m_expr "/" u_expr | m_expr "%" u_expr
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#unary-arithmetic-and-bitwise-operations}]
 */
class MultiplicativeExpr(
        val multiplicativeExpr: BaseOperator,
        val binaryOperator: BinaryOperator,
        val unaryExpr: BaseOperator
): BaseOperator() {


    override fun toString(): String {
        return "MultiplicativeExpr(" +
            System.lineSeparator() +
            "\tmultiplicativeExpr=$multiplicativeExpr " +
            "binaryOperator=$binaryOperator " +
            "unaryExpr=$unaryExpr" +
            System.lineSeparator() +
            ")"
    }
}