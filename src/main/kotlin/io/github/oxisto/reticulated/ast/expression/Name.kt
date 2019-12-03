package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.solver.Identifiable
import io.github.oxisto.reticulated.solver.ResolvedType
import io.github.oxisto.reticulated.solver.ResolvedVariable

/**
 * According to the reference, a name is an identifier occurring as an atom.
 *
 * References:
 * - https://docs.python.org/3/reference/expressions.html#atom-identifiers
 * - https://docs.python.org/3/reference/executionmodel.html
 */
class Name(name: String) : Identifier(name), Identifiable {

  companion object {
    fun fromIdentifier(id: Identifier): Name {
      return Name(id.name)
    }
  }

  /**
   * Establishes a binding of this name to the scope. (Actually names are bound to 'blocks' and a 'scope' is only referring to the visibility of a name.
   */
  fun bind(scope: Scope) {
    println("Binding %s to scope %s".format(this.name, scope))

    // not sure what to do here, just add the name for now
    val variable = ResolvedVariable(this, ResolvedType())
    scope.addVariable(variable)
  }

  override val id: Identifier
    get() {
      return this
    }

  /**
   * Tries to resolve the name to an object (or rather a variable holding the object) in the specified scope.
   */
  fun resolve(scope: Scope): ResolvedVariable? {
    return scope.resolveByIdentifier(this)
  }

}