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

package io.github.oxisto.reticulated.ast.expression.primary.call

import io.github.oxisto.reticulated.ast.expression.argument.Arguments
import io.github.oxisto.reticulated.ast.expression.argument.KeywordItem
import io.github.oxisto.reticulated.ast.expression.primary.Primary

/**
 * This class represents a call
 * It´s EBNF representation is:
 *        call ::= primary "(" [argument_list [","] | comprehension] ")"
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#calls}]
 */
class Call(val primary: Primary, val arguments: Arguments, val keywords: List<KeywordItem> = listOf()) : Primary() {

  init {
    primary.parent = this
    arguments.parent = this
  }

  override fun toString(): String {
    return "Call(primary=$primary, arguments=$arguments, keywords=$keywords)"
  }
}
