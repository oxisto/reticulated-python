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

package io.github.oxisto.reticulated.ast.expression.comparison

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.boolean_expr.OrExpr

/**
 * This class represents a comparison.
 * It´s EBNF definition is:
 *      comparison ::= or_expr ( comp_operator or_expr )*
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#comparisons}]
 */
class Comparison(val orExpr: OrExpr, val comparisons: List<Pair<CompOperator, OrExpr>>): Node() {

    override fun toString(): String {
        var result = "Comparison(" + System.lineSeparator() +
                "\torExpr=$orExpr"
        for(elem:Pair<CompOperator, OrExpr> in comparisons){
            val compOperatorOfElem = elem.first
            val orExprOfElem = elem.second
            result += " compOperator=$compOperatorOfElem orEpr=$orExprOfElem"
        }
        result += System.lineSeparator() + ")"
        return result
    }
}