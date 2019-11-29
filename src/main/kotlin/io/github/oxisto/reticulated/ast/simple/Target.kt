package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.Name

// TODO: Target could be more than just an identifier. Convert it to an interface?
class Target(val name: Name) : Node() {
}