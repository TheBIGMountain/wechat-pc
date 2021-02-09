package com.dqpi.client.view.face


class DefaultFaceEvent(private val faceInit: FaceInit) {
  init {
    faceInit.rootPane.setOnMouseExited {
      faceInit.hide()
    }
  }
}