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

package io.github.oxisto.reticulated.ast.expression.lambda

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.ExpressionNoCond
import io.github.oxisto.reticulated.ast.statement.ParameterList

/**
 * This class represents a lambda_expr_nocond
 * The EBNF representation is:
 *      lambda_expr_nocond ::= "lambda" [parameter_list] ":" expression_nocond
 *  [see: {@linktourl https://docs.python.org/3/reference/expressions.html#lambda}]
 */
class LambdaNoCond(val parameterList: ParameterList?, val exprNoCond: ExpressionNoCond): Node() {
    override fun toString(): String {
        var result = "lambda "
        if ( parameterList != null ) {
            result += "parameterList=$parameterList "
        }
        result += ": exprNoCond=$exprNoCond"
        return "LambdaNoCond(" + System.lineSeparator() +
                "\tlambdaNoCond=$result" + System.lineSeparator() +
                ")"
    }
}