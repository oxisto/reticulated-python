package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.expression.Identifier

class ImportStatement(val module: Identifier) : SimpleStatement() {

  override fun isExpressionStatement(): Boolean {
    return false
  }

  override fun isAssignmentStatement(): Boolean {
    return false
  }

  override fun isImportStatement(): Boolean {
    return true
  }

}