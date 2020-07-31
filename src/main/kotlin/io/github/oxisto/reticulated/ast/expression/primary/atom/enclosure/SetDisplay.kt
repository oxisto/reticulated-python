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


package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.CouldNotParseException
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.starred.StarredList

/**
 * This class represents a set_display.
 * It´s EBNF representations is:
 *        set_display ::= "{" (starred_list | comprehension) "}"
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#grammar-token-set-display}]
 */
class SetDisplay(val starredList: StarredList?, val comprehension: Comprehension?) : Enclosure() {

  init {
    if (starredList == null) {
      if (comprehension == null)
        throw CouldNotParseException("A SetDisplay should contain a starredList or a comprehension")
    } else {
      if (comprehension != null)
        throw CouldNotParseException(
            "A Set Display should not contain both, a starredList=$starredList and a comprehension=$comprehension"
        )
    }
  }

  override fun toString(): String {
    var result = "SetDisplay(" + System.lineSeparator() + "\t\"{\""
    if (starredList != null)
      result += "starredList=$starredList"
    if (comprehension != null)
      result += "comprehension=$comprehension"
    return "$result \"}\"" + System.lineSeparator() + ")"
  }


}