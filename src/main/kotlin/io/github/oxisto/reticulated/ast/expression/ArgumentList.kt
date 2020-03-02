package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.Node

class ArgumentList(private val arguments: List<Argument> = ArrayList()) : Node(), Container<Argument> {

  override val children get() = this.arguments

}