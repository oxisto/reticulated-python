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

package io.github.oxisto.reticulated.ast.expression.boolean_ops

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.comparison.ComparisonVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class BooleanOpVisitor(val scope: Scope) : Python3BaseVisitor<BaseBooleanOp>() {
    override fun visitOr_test(ctx: Python3Parser.Or_testContext): BaseBooleanOp {
        val orTest: OrTest?
        val andTest: AndTest
        val getAndTestByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(this) as AndTest
        }
        if(ctx.childCount == 1) {
            orTest = null
            andTest = getAndTestByPosition(0)
        }else{
            assert(ctx.childCount == 3)
            orTest = ctx.getChild(0).accept(this) as OrTest
            andTest = getAndTestByPosition(2)
        }
        return OrTest(orTest, andTest)
    }

    override fun visitAnd_test(ctx: Python3Parser.And_testContext): BaseBooleanOp {
        val andTest: AndTest?
        val notTest: NotTest
        val getNotTestByPosition = {
            position: Int -> ctx
                .getChild(position)
                .accept(this) as NotTest
        }
        if(ctx.childCount == 1){
            andTest = null
            notTest = getNotTestByPosition(0)
        } else {
            assert(ctx.childCount == 3)
            andTest = ctx.getChild(0).accept(this) as AndTest
            notTest = getNotTestByPosition(2)
        }
        return AndTest(andTest, notTest)
    }

    override fun visitNot_test(ctx: Python3Parser.Not_testContext): BaseBooleanOp {
        val comparison: Comparison?
        val notTest: NotTest?
        if(ctx.childCount == 2){
            comparison = null
            notTest = ctx.getChild(1).accept(this) as NotTest
        }else {
            assert(ctx.childCount == 1)
            comparison = ctx.getChild(0).accept(ComparisonVisitor(this.scope))
            notTest = null
        }
        return NotTest(comparison, notTest)
    }
}