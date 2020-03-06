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

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.operator.OperatorVisitor
import io.github.oxisto.reticulated.ast.expression.operator.ShiftExpr
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class BooleanExprVisitor(val scope: Scope):  Python3BaseVisitor<BaseBooleanExpr>() {

    override fun visitExpr(ctx: Python3Parser.ExprContext): BaseBooleanExpr {
        val orExpr: BaseBooleanExpr?
        val xorExpr: XorExpr
        val getXorExprByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(this) as XorExpr
        }
        if(ctx.childCount == 1){
            orExpr = null
            xorExpr = getXorExprByPosition(0)
        } else {
            assert(ctx.childCount == 3)
            orExpr = ctx.getChild(0)
                    .accept(this) as BaseBooleanExpr
            xorExpr = getXorExprByPosition(2)
        }
        return OrExpr(orExpr, xorExpr)
    }

    override fun visitXor_expr(ctx: Python3Parser.Xor_exprContext): BaseBooleanExpr {
        val xorExpr:  BaseBooleanExpr?
        val andExpr: AndExpr
        val getAndExprByPosition = {
            position:Int -> ctx.
                getChild(position)
                .accept(this) as AndExpr
        }
        if(ctx.childCount == 1){
            xorExpr = null
            andExpr = getAndExprByPosition(0)
        } else {
            assert(ctx.childCount == 3)
            xorExpr = ctx.getChild(0)
                    .accept(this) as BaseBooleanExpr
            andExpr = getAndExprByPosition(2)
        }
        return XorExpr(xorExpr, andExpr)
    }

    override fun visitAnd_expr(ctx: Python3Parser.And_exprContext): BaseBooleanExpr {
        val andExpr: BaseBooleanExpr?
        val shiftExpr: ShiftExpr
        val getShiftExprByPosition = {
            position:Int -> ctx.
                getChild(position)
                .accept(
                        OperatorVisitor(
                                this.scope
                        )
                ) as ShiftExpr
        }
        if(ctx.childCount == 1) {
            andExpr = null
            shiftExpr = getShiftExprByPosition(0)
        } else {
            assert(ctx.childCount == 3)
            val child = ctx.getChild(2)
            andExpr = if(child is Python3Parser.And_exprContext) {
                child.accept(this) as BaseBooleanExpr
            }else {
                child.accept(
                        OperatorVisitor(
                                this.scope
                        )
                )
            }
            shiftExpr = getShiftExprByPosition(0)
        }
        return AndExpr(andExpr, shiftExpr)
    }
}