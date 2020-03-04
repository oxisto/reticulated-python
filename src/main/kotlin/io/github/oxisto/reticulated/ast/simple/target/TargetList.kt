package io.github.oxisto.reticulated.ast.simple.target

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.simple.target.Target

class TargetList(val targets: List<Target>) : Node(), Container<Target> {
  override val children: List<Target>
    get() = targets
  override fun toString(): String {
    return "TargetList(targets=$targets)"
  }
}