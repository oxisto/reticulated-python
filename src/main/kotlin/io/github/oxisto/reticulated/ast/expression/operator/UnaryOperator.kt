/*
 * Copyright (c) 2020, Fraunhofer AISEC. All rights reserved.
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

import io.github.oxisto.reticulated.ast.CouldNotParseException

/**
 * All unary operators of python.
 */
enum class UnaryOperator(val symbol: String) {
  POSITIVE("+"), NEGATIVE("-"), BITWISE_NOT("~");

  companion object {
    fun getUnaryOperator(symbolToFind: String): UnaryOperator {
      var result: UnaryOperator? = null
      for (unaryOperator in values()) {
        if (unaryOperator.symbol == symbolToFind) {
          result = unaryOperator
          break
        }
      }
      return result ?: throw CouldNotParseException(
        "The UnaryOperator=$symbolToFind should be a valid operator."
      )
    }
  }
}
