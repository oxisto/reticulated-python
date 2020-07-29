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

package io.github.oxisto.reticulated.ast.expression.operator

import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.primary.Primary

/**
 * This class represents an await_expr
 * It´s EBNF representation is:
 *      await_expr ::="await" primary
 * [see: https://docs.python.org/3/reference/expressions.html#await-expression]
 */
class AwaitExpr(val primary: Primary): BaseOperator(){

    // TODO: Write AwaitExpr tests

    override fun toString(): String {
        return "AwaitExpr(" + System.lineSeparator() +
                "\t\"await\" primary=$primary" + System.lineSeparator() +
                ")"
    }
}