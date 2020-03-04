package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.simple.target.TargetList


class CompFor(val isAsync: Boolean, val targetList: TargetList, val orTest: OrTest, val compIter: CompIter?) : BaseComprehension() {

    override fun toString(): String {
        var result = String()
        if ( isAsync ) {
            result += "async "
        }
        result += "for targetList=$targetList in orTest=$orTest"
        if ( compIter != null ) {
            result += " compIter=$compIter"
        }
        return "CompFor(compFor=$result)"
    }

}