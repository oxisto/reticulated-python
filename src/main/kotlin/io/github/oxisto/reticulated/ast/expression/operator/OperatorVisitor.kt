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
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.AwaitExpr
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.Primary
import io.github.oxisto.reticulated.ast.expression.boolean_expr.BaseBooleanExpr
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNodeImpl

/**
 * This class offers visitors for a shift_expr, an a_expr, a m_expr, an u_expr and a power (expression).
 * It´s EBNF representations are:
 *      shift_expr ::=  a_expr | shift_expr ("<<" | ">>") a_expr
 *      a_expr ::=  m_expr | a_expr "+" m_expr | a_expr "-" m_expr
 *      m_expr ::=  u_expr | m_expr "*" u_expr | m_expr "@" m_expr |
 *              m_expr "//" u_expr | m_expr "/" u_expr | m_expr "%" u_expr
 *      u_expr ::=  power | "-" u_expr | "+" u_expr | "~" u_expr
 *      power ::=  (await_expr | primary) ["**" u_expr]
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#unary-arithmetic-and-bitwise-operations}]
 */
class OperatorVisitor(val scope: Scope): Python3BaseVisitor<BaseOperator>() {
    override fun visitShift_expr(ctx: Python3Parser.Shift_exprContext): BaseOperator {
        if(ctx.childCount < 1 || ctx.childCount > 3){
            throw CouldNotParseException()
        }
        val shiftExpr: BaseBooleanExpr?
        val binaryOperator: BinaryOperator?
        val baseOperator: BaseOperator
        val getBaseOperatorByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(this)
        }
        if(ctx.childCount == 1){
            shiftExpr = null
            binaryOperator = null
            baseOperator = getBaseOperatorByPosition(0)
        } else {
            shiftExpr = ctx.getChild(0)
                    .accept(this)
            binaryOperator = BinaryOperator
                    .getBinaryOperatorBySymbol(
                            ctx.getChild(1).text
                    )
            baseOperator = getBaseOperatorByPosition(2)
        }
        return ShiftExpr(shiftExpr, binaryOperator, baseOperator)
    }

    override fun visitArith_expr(ctx: Python3Parser.Arith_exprContext): BaseOperator {
        if(ctx.childCount < 1 || ctx.childCount > 3) {
            throw CouldNotParseException()
        }
        return when (ctx.childCount) {
            1 -> {
                ctx.getChild(0).accept(this) as PowerExpr
            }
            2 -> {
                val unaryOperator = UnaryOperator.valueOf(ctx.getChild(0).text)
                val unaryExpr:UnaryExpr = ctx.getChild(1).accept(this) as UnaryExpr
                UnaryExpr( unaryOperator, unaryExpr)
            }
            3 -> {
                val binaryOperator: BinaryOperator = BinaryOperator
                    .getBinaryOperatorBySymbol(
                        ctx
                        .getChild(1)
                        .text
                    ) ?: throw CouldNotParseException()
                val baseOperatorLeft: BaseOperator = ctx
                        .getChild(0)
                        .accept(this)
                val baseOperatorRight: BaseOperator = ctx
                        .getChild(2)
                        .accept(this)
                when ( binaryOperator ) {
                    BinaryOperator.ADDITION, BinaryOperator.SUBTRACTION -> {
                        AdditiveExpr(baseOperatorLeft, binaryOperator, baseOperatorRight)
                    }
                    BinaryOperator.MULTIPLICATION, BinaryOperator.DIVISION,
                        BinaryOperator.FLOOR_DIVISION, BinaryOperator.MATRIX_MULTIPLICATION,
                        BinaryOperator.MODULO -> {
                        MultiplicativeExpr(baseOperatorLeft, binaryOperator, baseOperatorRight)
                    }
                    else -> throw CouldNotParseException("Could not parse")
                }
            }
            else -> throw CouldNotParseException("Could not parse")
        }
    }

    override fun visitTerm(ctx: Python3Parser.TermContext): BaseOperator {
        val child = ctx.getChild(0)
        return if(child.childCount == 2 && child.getChild(0) is TerminalNodeImpl){
            child.getChild(1).accept(this)
        } else {
            handlePower(ctx)
        }
    }

    override fun visitPower(ctx: Python3Parser.PowerContext): PowerExpr {
        return handlePower(ctx)
    }

    private fun handlePower(ctx: ParserRuleContext): PowerExpr{
        if(ctx.childCount != 1 && ctx.childCount != 3 ){
            throw CouldNotParseException()
        }
        val awaitExpr: AwaitExpr?
        val primary: Primary?
        val child = ctx.getChild(0)
        if(child.childCount == 2){
            awaitExpr = child.accept(
                ExpressionVisitor(
                    this.scope
                )
            ) as AwaitExpr
            primary = null
        }else{
            awaitExpr = null
            primary = child.accept(
                ExpressionVisitor(
                    this.scope
                )
            ) as Primary
        }

        val unaryExpr: UnaryExpr? = if(ctx.childCount == 3){
            ctx
                .getChild(2)
                .accept(this) as UnaryExpr
        }else { null }
        return PowerExpr(awaitExpr, primary, unaryExpr)
    }

}