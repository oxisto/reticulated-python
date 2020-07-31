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

import io.github.oxisto.reticulated.ast.expression.Expression

/**
 * This class represents a proper_slice.
 * It´s EBNF representation is:
 *        proper_slice ::=  [lower_bound] ":" [upper_bound] [ ":" stride ]
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#grammar-token-proper-slice}]
 */
class ProperSlice(
    val lowerBound: Expression?,
    val upperBound: Expression?,
    val stride: Expression?
): Slice() {


  override fun toString(): String {
    var result = "ProperSlice(" + System.lineSeparator() + "\t"
    if (lowerBound != null)
      result += "LowerBound=$lowerBound"
    result += " \":\" "
    if (upperBound != null)
      result += "UpperBound=$upperBound"
    if (stride != null)
      result += " $stride"
    return result + System.lineSeparator() + ")"
  }

}