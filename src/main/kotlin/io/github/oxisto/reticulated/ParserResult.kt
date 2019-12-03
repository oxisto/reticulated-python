package io.github.oxisto.reticulated

import io.github.oxisto.reticulated.ast.FileInput
import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.Scope

data class ParserResult(val root: FileInput, val scope: Scope) {
}