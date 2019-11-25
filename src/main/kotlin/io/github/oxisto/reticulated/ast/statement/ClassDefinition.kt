package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Suite

class ClassDefinition(suite: Suite) : Definition(suite) {

  override fun isFunctionDefinition(): Boolean {
    return false
  }

  override fun isClassDefinition(): Boolean {
    return true
  }

}