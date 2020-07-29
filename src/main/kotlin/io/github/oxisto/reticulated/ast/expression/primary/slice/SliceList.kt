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

package io.github.oxisto.reticulated.ast.expression.primary.slice

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.primary.Primary

class SliceList(private val items: List<Primary> = ArrayList()) : Slice(), Container<Primary> {

  // TODO: WriteSliceList tests

  init {
    if (items.isEmpty())
      throw CouldNotParseException("Empty slice item list was provided.")

  }

  override val children get() = this.items

  override fun toString(): String {
    var result =  "SliceList(" + System.lineSeparator() +
        "\t" + items.first()
    items.subList(1, items.size).forEach { result += ", $it" }
    return result + System.lineSeparator() + ")"
  }
}