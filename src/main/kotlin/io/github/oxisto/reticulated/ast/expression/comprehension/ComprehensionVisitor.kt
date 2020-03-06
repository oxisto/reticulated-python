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

package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionNoCond
import io.github.oxisto.reticulated.ast.expression.boolean_ops.BooleanOpVisitor
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.lambda.LambdaNoCondVisitor
import io.github.oxisto.reticulated.ast.simple.target.TargetList
import io.github.oxisto.reticulated.ast.simple.target.TargetListVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import java.lang.IllegalArgumentException

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
        val isAsync: Boolean
        val targetList: TargetList
        val orTest: OrTest
        val compIter: BaseComprehension?

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
                ) as BaseComprehension
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
                isAsync = true
                targetList = getTargetListByPosition(2)
                orTest = getOrTestByPosition(4)
                compIter = null
            }
        } else {
            assert(ctx.childCount == 6)
            isAsync = true
            targetList = getTargetListByPosition(2)
            orTest = getOrTestByPosition(4)
            compIter = getCompIterByPosition(5)
        }
        return CompFor(isAsync, targetList, orTest, compIter)
    }

    override fun visitComp_iter(ctx: Python3Parser.Comp_iterContext): BaseComprehension {
        if(ctx.childCount != 1){
            throw IllegalArgumentException()
        }
        val child = ctx.getChild(0)
        assert(child is Python3Parser.Comp_forContext || child is Python3Parser.Comp_ifContext)
        return child.accept(this)
    }

    override fun visitComp_if(ctx: Python3Parser.Comp_ifContext): BaseComprehension {
        val secondChild = ctx.getChild(1)
        val expressionNoCond : ExpressionNoCond = if ( secondChild is Python3Parser.Test_nocondContext ){
            ExpressionNoCond(
                    secondChild.accept(BooleanOpVisitor(this.scope)) as OrTest,
                    null
            )
        } else {
            assert(secondChild is Python3Parser.Lambdef_nocondContext)
            ExpressionNoCond(
                    null,
                    secondChild.accept(
                            LambdaNoCondVisitor(
                                    this.scope
                            )
                    )
            )
        }
        val compIter: BaseComprehension? = if ( ctx.childCount == 3 ) {
            ctx.getChild(2).accept(this) as BaseComprehension
        } else { null }

        return CompIf(expressionNoCond, compIter)

    }
}