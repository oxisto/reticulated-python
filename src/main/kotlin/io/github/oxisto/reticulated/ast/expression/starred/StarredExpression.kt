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


class StarredExpression(
    val starredItems: List<StarredItem> = ArrayList()
): Starred(), Container<StarredItem> {

  // TODO: Write StarredExpression Tests

  override val children get() = this.starredItems

  override fun toString(): String {
    return "StarredExpression(" + System.lineSeparator() +
        "\tStarredItems=$starredItems" + System.lineSeparator() +
        ")"
  }

}