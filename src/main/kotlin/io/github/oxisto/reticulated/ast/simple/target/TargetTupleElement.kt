package io.github.oxisto.reticulated.ast.simple.target

class TargetTupleElement(targetList: TargetList): TargetCollectionElement(targetList) {
    override fun toString(): String {
        return "TargetTupleElement(targetList=( $targetList ))"
    }
}