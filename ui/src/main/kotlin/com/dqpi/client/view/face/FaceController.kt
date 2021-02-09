package com.dqpi.client.view.face

import com.dqpi.client.view.UIObject
import com.dqpi.client.view.chat.ChatEvent
import com.dqpi.client.view.chat.ChatInit
import com.dqpi.client.view.chat.ChatMethod


class FaceController(
  uiObject: UIObject,
  override val chatInit: ChatInit,
  override val chatEvent: ChatEvent,
  override val chatMethod: ChatMethod
): FaceInit(uiObject), FaceMethod {
  private val faceView = toFaceView()

  init {
    DefaultFaceEvent(this)
  }

  override fun doShowFace(x: Double, y: Double) {
    this.x = (x + 230 * (1 - 0.618))
    this.y = y - 160
    show()
  }
}