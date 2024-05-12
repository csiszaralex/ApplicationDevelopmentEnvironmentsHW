import QtQuick
import QtQuick.Controls

Page {
    id: root
    title: "Watchlist"

    background: Rectangle {
        color: root.parent.background.color
    }

    Rectangle {
        height: 20
        width: 30
        color: "green"

        anchors {
            verticalCenter: parent.verticalCenter
            horizontalCenter: parent.horizontalCenter
        }
    }
}
