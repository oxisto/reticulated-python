package com.github.oxisto.reticulated.ast.statement

import com.github.oxisto.reticulated.ast.Suite

/**
 * A compound statement contains a group of other statements.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html
 */
abstract class CompoundStatement(val suite: Suite) : Statement() {

  init {
    suite.parent = this
  }

  fun asFunctionDefinition(): FunctionDefinition {
    return this as FunctionDefinition
  }

  override fun isCompoundStatement(): Boolean {
    return true
  }

  override fun isStatementList(): Boolean {
    return false
  }

  abstract fun isFunctionDefinition(): Boolean

}