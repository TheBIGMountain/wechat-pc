package com.dqpi.client.view.chat.element.group_bar_friend

import com.dqpi.client.utils.createFriendGroupId
import com.dqpi.client.view.chat.data.GroupsData
import javafx.scene.control.Label
import javafx.scene.layout.Pane

/**
 * @author TheBIGMountain
 */
class ElementFriendGroup(
  val groupPane: Pane
)

fun String.toFriendGroup(groupName: String, groupHead: String): ElementFriendGroup {
  return ElementFriendGroup(Pane().also {
    // 群组底板(存储群ID)
    it.id = createFriendGroupId()
    it.userData = GroupsData(this, groupName, groupHead)
    it.setPrefSize(250.0, 70.0)
    it.styleClass.add("elementFriendGroup")

    // 头像区域
    it.children.add(Label().apply {
      setPrefSize(50.0, 50.0)
      layoutX = 15.0
      layoutY = 10.0
      styleClass.add("elementFriendGroup_head")
      style = "-fx-background-image: url('/fxml/chat/img/head/${groupHead}.png')"
    })

    // 名称区域
    it.children.add(Label().apply {
      setPrefSize(200.0, 40.0)
      layoutX = 80.0
      layoutY = 15.0
      text = groupName
      it.styleClass.add("elementFriendGroup_name")
    })
  })
}