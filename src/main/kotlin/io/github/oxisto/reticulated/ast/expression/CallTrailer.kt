package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Node

abstract class CallTrailer: Node() {
    abstract override fun toString(): String
}