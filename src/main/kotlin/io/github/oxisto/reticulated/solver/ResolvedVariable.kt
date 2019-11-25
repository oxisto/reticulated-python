package io.github.oxisto.reticulated.solver

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.Identifier

class ResolvedVariable(val definition: Identifiable, val type: ResolvedType? = null) {

}