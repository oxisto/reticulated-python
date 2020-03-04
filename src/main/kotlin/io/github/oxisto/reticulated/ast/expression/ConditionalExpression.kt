package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import java.lang.IllegalArgumentException

class ConditionalExpression(val orTest: OrTest, val orTestOptional: OrTest?, val expressionOptional: Expression?): Expression() {

  init {
    if(orTestOptional == null){
      if(expressionOptional != null){
        throw IllegalArgumentException()
      }
    }else{
      if(expressionOptional == null){
        throw IllegalArgumentException()
      }
    }
  }

  override fun isCall(): Boolean {
    return false
  }

  override fun toString(): String {
    var result = "ConditionalExpression(orTest=$orTest"
    if(orTestOptional != null){
      result += " if orTest=$orTestOptional else expression=$expressionOptional"
    }
    return result
  }

}