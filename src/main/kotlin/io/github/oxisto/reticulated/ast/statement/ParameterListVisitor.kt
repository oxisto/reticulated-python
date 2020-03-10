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

package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.EmptyContextException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class ParameterListVisitor(val scope: Scope) : Python3BaseVisitor<List<Parameter>>() {

  override fun visitTypedargslist(ctx: Python3Parser.TypedargslistContext?): List<Parameter> {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val list = ArrayList<Parameter>()

    // loop through the children
    for (tree in ctx.children) {
      // skip commas, etc.
      if (tree is TerminalNode) {
        continue
      }

      val parameter = tree.accept(ParameterVisitor(this.scope))

      list.add(parameter)
    }

    return list
  }

  override fun visitVarargslist(ctx: Python3Parser.VarargslistContext): List<Parameter> {
    // TODO: handle parameter_list_starrgs
    val list = ArrayList<Parameter>()

    // loop through the children
    for (tree in ctx.children) {
      // skip commas, etc.
      if (tree is TerminalNode) {
        continue
      }

      val parameter = tree.accept(ParameterVisitor(this.scope))

      list.add(parameter)
    }

    return list
  }

}