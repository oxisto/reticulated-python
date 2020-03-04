package io.github.oxisto.reticulated.ast.simple.target

import io.github.oxisto.reticulated.ast.Node

class StaredTarget(val target: Target): Target, Node() {
    override fun toString(): String {
        return "StaredTarget(target=*$target)"
    }
}