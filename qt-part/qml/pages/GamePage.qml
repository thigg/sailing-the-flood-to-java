import QtQuick 2.0
import Sailfish.Silica 1.0
import GamePanel 1.0

Page {
    id: page

    // The effective value will be restricted by ApplicationWindow.allowedOrientations
    allowedOrientations: Orientation.All

    property var floodColorsCollection: ["#fbe7c6", "#b4f8c8", "#a0e7e5", "#ffaebc"]
    property var floodColors: floodColorsCollection
    property int gameSize: 5

    Connections {
        target: GameState
    }

    function startNewGame() {
        JGateway.postMessage("startGame", JSON.stringify({
                                                             "size": gameSize,
                                                             "numColors": floodColors.length
                                                         }))
    }

    Component.onCompleted: {
        gamePanel.gameState = GameState
        startNewGame()
    }

    Connections {
        target: JGateway
        onInvocationResult: {
            gamePanel.update()
        }
    }

    SilicaFlickable {
        anchors.fill: parent

        PullDownMenu {
            MenuItem {
                text: qsTr("Start New Game")
                onClicked: {
                    var dialog = pageStack.push(Qt.resolvedUrl(
                                                    "NewGameDialog.qml"))
                    dialog.accepted.connect(function () {
                        gameSize = dialog.gameSize
                        floodColors = floodColorsCollection.slice(
                                    0, dialog.numColors)
                        startNewGame()
                    })
                }
            }
        }

        contentHeight: column.height

        Column {
            id: column

            width: page.width
            anchors.verticalCenter: parent.verticalCenter
            spacing: Theme.paddingLarge
            PageHeader {
                title: qsTr("Open Flood")
            }
            Label {
                text: GameState.won ? qsTr("You won in %1 moves").arg(
                                          GameState.steps) : qsTr(
                                          "Steps: %1/%2").arg(
                                          GameState.steps).arg(
                                          GameState.maxSteps)
            }

            GamePanel {
                id: gamePanel
                x: Theme.paddingLarge
                width: parent.width - Theme.paddingLarge * 2
                height: width
            }

            Grid {
                columns: 4
                height: Theme.fontSizeExtraLarge * 2
                width: page.width - Theme.paddingLarge * 2
                x: Theme.paddingLarge
                spacing: 4
                Repeater {
                    model: floodColors.length
                    Button {
                        height: parent.height
                        width: parent.width / floodColors.length
                        backgroundColor: floodColors[index]

                        onClicked: JGateway.postMessage("flood",
                                                        JSON.stringify({
                                                                           "color": index
                                                                       }))
                    }
                }
            }
        }
    }
}
