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

package io.github.oxisto.reticulated.ast.expression.lambda

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.ExpressionNoCond
import io.github.oxisto.reticulated.ast.expression.ExpressionVisitor
import io.github.oxisto.reticulated.ast.statement.Parameter
import io.github.oxisto.reticulated.ast.statement.ParameterList
import io.github.oxisto.reticulated.ast.statement.ParameterListVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class LambdaNoCondVisitor(val scope: Scope): Python3BaseVisitor<LambdaNoCond>() {
    override fun visitLambdef_nocond(ctx: Python3Parser.Lambdef_nocondContext): LambdaNoCond {
        val parameterList: ParameterList?
        val expressionNoCond: ExpressionNoCond
        val getExpressionNoCondByPosition = {
            position:Int -> ctx
                .getChild(position)
                .accept(
                        ExpressionVisitor(
                                this.scope
                        )
                ) as ExpressionNoCond
        }
        if( ctx.childCount == 3 ) {
            parameterList = null
            expressionNoCond = getExpressionNoCondByPosition(2)
        } else {
            assert(ctx.childCount == 4)
            val listOfParameter = ctx
                    .getChild(1)
                    .accept(
                            ParameterListVisitor(
                                    this.scope
                            )
                    ) as List<Parameter>
            parameterList = ParameterList(listOfParameter)
            expressionNoCond = getExpressionNoCondByPosition(3)
        }
        return LambdaNoCond(parameterList, expressionNoCond)
    }
}