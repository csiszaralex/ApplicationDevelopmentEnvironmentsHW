import QtQuick
import QtQuick.Controls

Page {
    id: root
    title: "Own Goods"

    background: Rectangle {
        color: root.parent.background.color
    }

    Rectangle {
        height: 20
        width: 30
        color: "blue"

        anchors {
            verticalCenter: parent.verticalCenter
            horizontalCenter: parent.horizontalCenter
        }
    }
}
