package com.dqpi.client.view

import javafx.scene.Cursor
import javafx.scene.Parent
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import javafx.stage.Stage

/**
 * @author TheBIGMountain
 */
abstract class UIObject : Stage() {
  protected abstract val root: Parent
  private var xOffset = 0.0
  private var yOffset = 0.0

  inline fun <reified T> get(id: String): T {
    return T::class.java.cast(`access$root`.lookup("#$id"))
  }

  fun clearViewListSelectedAll(vararg listViews: ListView<Pane>) {
    listViews.forEach { it.selectionModel.clearSelection() }
  }

  // 移动窗体
  fun move() {
    root.setOnMousePressed {
      xOffset = x - it.screenX
      yOffset = y - it.screenY
      root.cursor = Cursor.CLOSED_HAND
    }
    root.setOnMouseDragged {
      x = it.screenX + xOffset
      y = it.screenY + yOffset
    }
    root.setOnMouseReleased {
      root.cursor = Cursor.DEFAULT
    }
  }

  @PublishedApi
  internal val `access$root`: Parent
    get() = root
}