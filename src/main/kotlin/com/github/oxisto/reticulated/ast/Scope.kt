package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Identifier
import com.github.oxisto.reticulated.ast.statement.ParameterList
import com.github.oxisto.reticulated.solver.ResolvedVariable

enum class ScopeType {
  GLOBAL,
  FUNCTION,
  BLOCK
}

class Scope(val parent: Scope? = null, val type: ScopeType = ScopeType.GLOBAL) {

  val variables = HashMap<Identifier, ResolvedVariable>()

  fun addVariable(variable: ResolvedVariable) {
    val id = variable.definition.id

    this.variables[id] = variable

    println("Added variable %s to %s scope".format(variable, this.type))
  }

  fun resolveByIdentifier(id: Identifier): ResolvedVariable? {
    // look in the scope itself
    val variable = this.variables[id]

    // forward to parent scope, if there is any
    return if (variable == null && this.parent != null) {
      this.parent.resolveByIdentifier(id)
    } else {
      variable
    }
  }

  fun handleParameterList(parameterList: ParameterList) {
    parameterList.parameters.forEach { parameter ->
      val variable = ResolvedVariable(parameter)

      this.addVariable(variable)
    }
  }

}