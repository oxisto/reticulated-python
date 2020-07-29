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

package io.github.oxisto.reticulated.ast.expression.comparison

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.booleanexpr.OrExpr
import io.github.oxisto.reticulated.Pair
import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.booleanexpr.BaseBooleanExpr
import io.github.oxisto.reticulated.ast.expression.booleanops.BaseBooleanOp

/**
 * This class represents a comparison.
 * It´s EBNF definition is:
 *      comparison ::= or_expr ( comp_operator or_expr )*
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#comparisons}]
 */
class Comparison(val orExpr: BaseBooleanExpr, val comparisons: List<Pair<CompOperator, BaseBooleanExpr>>): BaseComparison() {

    init {
        if (comparisons.isEmpty())
            throw CouldNotParseException("A Comparison should contain a comparison not only a OrExpr=$orExpr.")
    }

    override fun toString(): String {
        var result = "Comparison(" + System.lineSeparator() +
                "\torExpr=$orExpr"
        for(elem:Pair<CompOperator, BaseBooleanExpr> in comparisons){
            val compOperatorOfElem = elem.getFirst()
            val orExprOfElem = elem.getSecond()
            result += " compOperator=$compOperatorOfElem orEpr=$orExprOfElem"
        }
        result += System.lineSeparator() + ")"
        return result
    }
}