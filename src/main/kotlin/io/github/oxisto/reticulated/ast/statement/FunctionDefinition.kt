package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Suite
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.Identifier

/**
 * A function definition
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-funcdef
 */
class FunctionDefinition(
  val id: Identifier,
  val parameterList: ParameterList = ParameterList(), suite: Suite,
  val expression: Expression?
) : Definition(suite) {
  // TODO: decorators

  init {
    id.parent = this
    parameterList.parent = this
  }

  override fun isFunctionDefinition(): Boolean {
    return true
  }

  override fun isClassDefinition(): Boolean {
    return false
  }

  override fun toString(): String {
    return "FunctionDefinition(id=$id, parameters=$parameterList)"
  }

}