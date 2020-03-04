package io.github.oxisto.reticulated.ast.simple.target

class TargetListElement(targetList:TargetList): TargetCollectionElement(targetList) {
    override fun toString(): String{
        return "TargetListElement(targetList=[ $targetList ])"
    }
}