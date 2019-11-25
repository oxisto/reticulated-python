package io.github.oxisto.reticulated.ast

interface Container<T> : Iterable<T> {

  val children: List<T>

  val count get() = this.children.size

  operator fun get(index: Int): T {
    return this.children[index]
  }

  override fun iterator(): Iterator<T> {
    return this.children.iterator()
  }

}