package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Suite

/**
 * A definition is a utility node to distinguish definition-style compound statements from others, such as 'if' and 'for'.
 */
abstract class Definition(suite: Suite) : CompoundStatement(suite) {

  abstract fun isClassDefinition(): Boolean
  abstract fun isFunctionDefinition(): Boolean

  fun asFunctionDefinition(): FunctionDefinition {
    return this as FunctionDefinition
  }

  fun asClassDefinition(): ClassDefinition {
    return this as ClassDefinition
  }

}