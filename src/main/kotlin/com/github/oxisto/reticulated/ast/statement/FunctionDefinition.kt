package com.github.oxisto.reticulated.ast.statement

import com.github.oxisto.reticulated.ast.expression.Identifier
import com.github.oxisto.reticulated.ast.Suite

/**
 * A function definition
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-funcdef
 */
class FunctionDefinition(val id: Identifier,
                         val parameterList: ParameterList = ParameterList(), suite: Suite) : CompoundStatement(suite) {

  // TODO: decorators

  init {
    id.parent = this
    parameterList.parent = this
  }

  override fun isFunctionDefinition(): Boolean {
    return true
  }

  override fun toString(): String {
    return "FunctionDefinition(id=$id, parameters=$parameterList)"
  }

}