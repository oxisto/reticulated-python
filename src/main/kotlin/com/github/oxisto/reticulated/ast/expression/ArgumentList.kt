package com.github.oxisto.reticulated.ast.expression

import com.github.oxisto.reticulated.ast.Container
import com.github.oxisto.reticulated.ast.Node
import com.github.oxisto.reticulated.ast.simple.SimpleStatement

class ArgumentList(val arguments: List<Argument> = ArrayList()) : Node(), Container<Argument> {

  override val children get() = this.arguments

}
