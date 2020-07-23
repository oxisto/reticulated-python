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

package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionNoCond
import io.github.oxisto.reticulated.ast.expression.ExpressionNoCondVisitor
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.expression.booleanops.BooleanOpVisitor
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.simple.target.TargetList
import io.github.oxisto.reticulated.ast.simple.target.TargetListVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This class offers visitors for comp_for, conp_iter and comp_if
 * The EBNF representation is:
 *      comprehension ::= expression comp_for
 *      comp_for ::= ["async"] "for" target_list "in" or_test [comp_iter]
 *      comp_iter ::= comp_for | comp_if
 *      comp_if ::= "if" expression_nocond [comp_iter]
 *  [see: {@linktourl https://docs.python.org/3/reference/expressions.html#displays-for-lists-sets-and-dictionaries}]
 */
class ComprehensionVisitor(val scope: Scope) : Python3BaseVisitor<BaseComprehension>() {

    override fun visitComp_for(ctx: Python3Parser.Comp_forContext): BaseComprehension {
        if(ctx.childCount < 4 || ctx.childCount > 6){
            throw CouldNotParseException("The ctx=$ctx child count is unexpected.")
        }
        val isAsync: Boolean
        val targetList: TargetList
        val orTest: OrTest
        val compIter: CompIter?

        val getTargetListByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(
                        TargetListVisitor(
                                this.scope
                        )
                ) as TargetList
        }
        val getOrTestByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(
                        BooleanOpVisitor(
                                this.scope
                        )
                ) as OrTest
        }
        val getCompIterByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(
                        this
                ) as CompIter
        }
        if ( ctx.childCount == 4 ) {
            isAsync = false
            targetList = getTargetListByPosition(1)
            orTest = getOrTestByPosition(3)
            compIter = null
        } else if ( ctx. childCount == 5) {
            if(ctx.getChild(0).toString() == "for") {
                isAsync = false
                targetList = getTargetListByPosition(1)
                orTest = getOrTestByPosition(3)
                compIter = getCompIterByPosition(4)
            } else {
                if(ctx.getChild(0).toString() != "async"){
                    throw CouldNotParseException("The first child of the ctx=$ctx was neither 'for' nor 'async'!")
                }
                isAsync = true
                targetList = getTargetListByPosition(2)
                orTest = getOrTestByPosition(4)
                compIter = null
            }
        } else {
            isAsync = true
            targetList = getTargetListByPosition(2)
            orTest = getOrTestByPosition(4)
            compIter = getCompIterByPosition(5)
        }

        return CompFor(isAsync, targetList, orTest, compIter)
    }

    override fun visitComp_iter(ctx: Python3Parser.Comp_iterContext): CompIter {
        if(ctx.childCount != 1){
            throw CouldNotParseException("The childCount of the ctx=$ctx was unexpected.")
        }
        val child = ctx.getChild(0)
        if(child !is Python3Parser.Comp_forContext && child !is Python3Parser.Comp_ifContext) {
            throw CouldNotParseException("")
        }
        return if(ctx.getChild(0).getChild(0).text == "if"){
            CompIter(null, child.accept(this) as CompIf)
        }else {
            CompIter(child.accept(this) as CompFor, null)
        }
    }

    override fun visitComp_if(ctx: Python3Parser.Comp_ifContext): CompIf {
        if(ctx.childCount < 2 || ctx.childCount > 3){
            throw CouldNotParseException("The childCount of the ctx=$ctx was unexpected.")
        }

        val expressionNoCond: ExpressionNoCond = ctx
                .getChild(1)
                .accept(
                        ExpressionNoCondVisitor(
                                this.scope
                        )
                )

        val compIter: CompIter? = if ( ctx.childCount == 3 ) {
            ctx.getChild(2).accept(this) as CompIter
        } else { null }

        return CompIf(expressionNoCond, compIter)
    }
}