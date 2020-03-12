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

package io.github.oxisto.reticulated.ast.expression.boolean_expr

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.operator.OperatorVisitor
import io.github.oxisto.reticulated.ast.expression.operator.ShiftExpr
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class BooleanExprVisitor(val scope: Scope):  Python3BaseVisitor<BaseBooleanExpr>() {
    /**
     * The visitor for an or_expr.
     * It´s EBNF representation is:
     *      or_expr ::= xor_expr | or_expr "|" xor_expr
     * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#binary-bitwise-operations}]
     */
    override fun visitExpr(ctx: Python3Parser.ExprContext): BaseBooleanExpr {
        if(ctx.childCount < 1 || ctx.childCount > 3){
            throw CouldNotParseException("The ctx=$ctx child count is unexpected.")
        }
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
            orExpr = ctx.getChild(0)
                    .accept(this) as BaseBooleanExpr
            xorExpr = getXorExprByPosition(2)
        }
        return OrExpr(orExpr, xorExpr)
    }

    /**
     * The visitor for an xor_expr.
     * It´s EBNF representation is:
     *      xor_expr ::= and_expr | xor_expr "^" and_expr
     * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#binary-bitwise-operations}]
     */
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
            if(ctx.childCount != 3){
                throw CouldNotParseException("The ctx=$ctx child count is unexpected.")
            }
            xorExpr = ctx.getChild(0)
                    .accept(this) as BaseBooleanExpr
            andExpr = getAndExprByPosition(2)
        }
        return XorExpr(xorExpr, andExpr)
    }

    /**
     * The visitor for an and_expr.
     * It´s EBNF representation is:
     *      and_expr ::= shift_expr | and_expr "&" shift_expr
     * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#binary-bitwise-operations}]
     */
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
            if(ctx.childCount != 3){
                throw CouldNotParseException("The ctx=$ctx child count is unexpected.")
            }
            val child = ctx.getChild(0)
            andExpr = if(child is Python3Parser.And_exprContext) {
                child.accept(this) as BaseBooleanExpr
            } else {
                child.accept(
                        OperatorVisitor(
                                this.scope
                        )
                )
            }
            shiftExpr = getShiftExprByPosition(2)
        }
        return AndExpr(andExpr, shiftExpr)
    }
}