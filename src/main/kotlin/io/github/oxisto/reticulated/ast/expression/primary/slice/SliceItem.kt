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

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.Expression

class SliceItem(val expression: Expression?, val properSlice: ProperSlice?): Slice()  {

  init {
    if (expression == null) {
      if (properSlice == null)
        throw CouldNotParseException(
            "The SliceItem was neither an Expression nor a ProperSlice."
        )
    } else {
      if (properSlice != null)
        throw CouldNotParseException(
            "The SliceItem cannot be an Expression=$expression and a ProperSlice=$properSlice."
        )
    }
  }

  override fun toString(): String {
    var result = "SliceItem(" + System.lineSeparator() + "\t"
    result += if (expression != null)
      "Expression=$expression"
    else
      "ProperSlice=$properSlice"
    return result + System.lineSeparator() + ")"
  }
}