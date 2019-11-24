package com.github.oxisto.reticulated.solver

import com.github.oxisto.reticulated.ast.Node
import com.github.oxisto.reticulated.ast.expression.Identifier

class ResolvedVariable(val definition: Identifiable, val type: ResolvedType? = null) {

}