package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.Node

class TargetList(val targets: List<Target>) : Node(), Container<Target> {
  override val children: List<Target>
    get() = targets
}