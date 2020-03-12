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

package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.boolean_ops.BooleanOpVisitor
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.lambda.LambdaNoCondVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This visitor is called for expression_nocond.
 * It´s EBNF representation is:
 *      expression_nocond ::= or_test | lambda_expr_nocond
 *
 */
class ExpressionNoCondVisitor(val scope: Scope): Python3BaseVisitor<ExpressionNoCond>() {
    override fun visitTest_nocond(ctx: Python3Parser.Test_nocondContext): ExpressionNoCond {
        if(ctx.childCount != 1){
            throw CouldNotParseException()
        }
        val child = ctx.getChild(0)

        return if ( child is Python3Parser.Or_testContext){
            ExpressionNoCond(
                    child.accept(BooleanOpVisitor(this.scope)) as OrTest,
                    null
            )
        } else {
            if(child !is Python3Parser.Lambdef_nocondContext){
                throw CouldNotParseException(
                        "The second child of the ctx=$ctx was neither a Test_nocondContext nor a Lambdef_nocondContext."
                )
            }
            ExpressionNoCond(
                    null,
                    child.accept(
                            LambdaNoCondVisitor(
                                    this.scope
                            )
                    )
            )
        }
    }
}