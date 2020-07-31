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

package io.github.oxisto.reticulated.ast.expression.booleanops

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.comparison.ComparisonVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This visitor is called for all boolean Operators.
 *
 */
class BooleanOpVisitor(val scope: Scope) : Python3BaseVisitor<BaseBooleanOp>() {

    /**
     * This visitor is called for an or_test.
     * It´s EBNF representation is:
     *      or_test ::= and_test | or_test "or" and_test
     * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#boolean-operations}]
     */
    override fun visitOr_test(ctx: Python3Parser.Or_testContext): BaseBooleanOp {
        val getAndTestByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(this)
        }
        return if(ctx.childCount == 3)
            OrTest(
                ctx.getChild(0)
                    .accept(this),
                getAndTestByPosition(2)
            )
        else getAndTestByPosition(0)
    }

    /**
     * This visitor is called for an and_test.
     * It´s EBNF representation is:
     *      and_test ::= not_test | and_test "and" not_test
     * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#boolean-operations}]
     */
    override fun visitAnd_test(ctx: Python3Parser.And_testContext): BaseBooleanOp {
        val getNotTestByPosition = {
            position: Int -> ctx
                .getChild(position)
                .accept(this)
        }
        return if (ctx.childCount == 3)
            AndTest(
                ctx.getChild(0)
                    .accept(this),
                getNotTestByPosition(2)
            )
        else getNotTestByPosition(0)
    }

    /**
     * This visitor is called for a not_test.
     * It´s EBNF representation is:
     *      not_test ::= comparison | "not" not_test
     * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#boolean-operations}]
     */
    override fun visitNot_test(ctx: Python3Parser.Not_testContext): BaseBooleanOp {
        return if (ctx.childCount == 2)
            NotTest( ctx.getChild(1).accept(this) )
        else ctx.getChild(0).accept(ComparisonVisitor(this.scope))
    }
}