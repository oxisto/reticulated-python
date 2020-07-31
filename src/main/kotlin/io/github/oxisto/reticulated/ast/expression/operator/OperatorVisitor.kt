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
import io.github.oxisto.reticulated.ast.expression.primary.Primary
import io.github.oxisto.reticulated.ast.expression.primary.PrimaryVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ParseTree
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
        val getBaseOperatorByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(this)
        }
        return if(ctx.childCount == 3)
            ShiftExpr(
                ctx.getChild(0)
                    .accept(this),
                BinaryOperator
                    .getBinaryOperator(
                        ctx.getChild(1).text
                    ),
                getBaseOperatorByPosition(2)
            )
        else getBaseOperatorByPosition(0)
    }

    override fun visitArith_expr(ctx: Python3Parser.Arith_exprContext): BaseOperator {
        return when (ctx.childCount) {
            1 -> ctx.getChild(0).accept(this)
            2 -> handleUnaryOperator(ctx)    // It is a UnaryExpr
            3 -> {
                // It is a AdditiveExpr
                val baseOperatorLeft: BaseOperator = ctx
                    .getChild(0)
                    .accept(this)

                val binaryOperator: BinaryOperator = BinaryOperator
                    .getBinaryOperator(
                        ctx
                        .getChild(1)
                        .text
                    )

                val baseOperatorRight: BaseOperator = ctx
                        .getChild(2)
                        .accept(this)

                AdditiveExpr(baseOperatorLeft, binaryOperator, baseOperatorRight)
            }
            else -> throw CouldNotParseException("Could not parse")
        }
    }

    override fun visitTerm(ctx: Python3Parser.TermContext): BaseOperator {
        val child = ctx.getChild(0)
        return if(
            child.childCount == 2 &&
            ctx.childCount == 1 &&
            child.getChild(0) is TerminalNodeImpl
        ){
            // It is a UnaryOperator
            handleUnaryOperator(ctx)
        } else if(ctx.childCount == 3){
            // It is a MultiplicativeExpr
            val baseOperatorLeft: BaseOperator = ctx
                .getChild(0)
                .accept(this)

            val binaryOperator: BinaryOperator = BinaryOperator
                .getBinaryOperator(
                    ctx.getChild(1)
                        .text
                )

            val baseOperatorRight: BaseOperator = ctx
                .getChild(2)
                .accept(this)

            MultiplicativeExpr(baseOperatorLeft, binaryOperator, baseOperatorRight)
        } else ctx.getChild(0).accept(this)
    }

    override fun visitFactor(ctx: Python3Parser.FactorContext): BaseOperator {
        return if(ctx.childCount == 2) getUnaryExpr(ctx)    // It is a UnaryExpr
        else ctx.getChild(0).accept(this)    // It is a PowerExpr
    }

    override fun visitPower(ctx: Python3Parser.PowerContext): BaseOperator {
        var awaitExpr: AwaitExpr? = null
        var primary: Primary? = null
        val child = ctx.getChild(0)
        if(child.childCount == 2 && child.getChild(0).text == "await"){
            // It is an AwaitExpr
            awaitExpr = AwaitExpr(
                child.accept(
                    PrimaryVisitor(
                        this.scope
                    )
                ) as Primary
            )
        } else {
            // It is a PrimaryExpr
            primary =
                child.accept(
                    PrimaryVisitor(
                        this.scope
                    )
                ) as Primary
        }

        val unaryExpr = if (ctx.childCount == 3) {
            // It is a UnaryExpr or a PowerExpr
            ctx
                .getChild(2)
                .accept(this)
        } else null
        return if(unaryExpr == null) {
            primary ?: awaitExpr!!
        } else PowerExpr(awaitExpr, primary, unaryExpr)
    }

    private fun handleUnaryOperator(ctx: ParserRuleContext): UnaryExpr {
        return getUnaryExpr(ctx.getChild(0))
    }

    private fun getUnaryExpr(parseTree: ParseTree): UnaryExpr {
        return UnaryExpr(
            UnaryOperator.getUnaryOperator(
                parseTree
                    .getChild(0)
                    .text.toString()
            ),
            parseTree.getChild(1)
                .accept(this)
        )
    }

}