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

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.ast.expression.booleanops.BooleanOpVisitor
import io.github.oxisto.reticulated.ast.expression.lambda.LambdaNoCondVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

/**
 * This visitor is called for expression_nocond.
 * It´s EBNF representation is:
 *      expression_nocond ::= or_test | lambda_expr_nocond
 *
 */
class ExpressionNoCondVisitor(val scope: Scope): Python3BaseVisitor<Expression>() {
    override fun visitTest_nocond(ctx: Python3Parser.Test_nocondContext): Expression {
        val child = ctx.getChild(0)
        return if ( child is Python3Parser.Or_testContext)
            child.accept(BooleanOpVisitor(this.scope))
        else child.accept(LambdaNoCondVisitor(this.scope))
    }
}