package com.github.oxisto.reticulated.ast;

import java.util.ArrayList;

class FileInput : Node() {

  val statements = ArrayList<Statement>();

  override fun toString(): String {
    return "FileInput(statements=$statements)"
  }

}
