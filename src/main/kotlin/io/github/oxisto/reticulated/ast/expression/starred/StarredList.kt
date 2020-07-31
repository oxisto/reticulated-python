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


package io.github.oxisto.reticulated.ast.expression.starred

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * This class represents an starred_list.
 * It´s EBNF representation is:
 *        starred_list ::= starred_item ("," starred_item)* [","]
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#grammar-token-starred-list}]
 */
class StarredList(private val starredItems: List<Starred> = ArrayList()): Expression(), Container<Starred> {

  init {
    if (starredItems.isEmpty())
      throw CouldNotParseException("The starredItems of the StarredList should not be null.")
  }

  override val children get() = starredItems

  override fun toString(): String {
    return "StarredList(" + System.lineSeparator() +
        "\tstarredItems=$starredItems" + System.lineSeparator() +
        ")"
  }
}