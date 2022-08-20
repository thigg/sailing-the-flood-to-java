import QtQuick 2.0
import Sailfish.Silica 1.0
import GamePanel 1.0

Page {
    id: page

    // The effective value will be restricted by ApplicationWindow.allowedOrientations
    allowedOrientations: Orientation.Portrait

    property var floodColorsCollection: ["#fbe7c6", "#b4f8c8", "#a0e7e5", "#ffaebc"]
    property var floodColors: floodColorsCollection
    property int gameSize: 5
    property bool multiplayer: false

    property int lastColor: -1
    property int lastPlayer: -1

    Connections {
        target: GameState
    }

    function startNewGame() {
        lastColor = -1
        lastPlayer = -1
        JGateway.postMessage("startGame", JSON.stringify({
                                                             "size": gameSize,
                                                             "numColors": floodColors.length
                                                         }))
    }

    function flood(index, player) {
        lastColor = index
        lastPlayer = player
        JGateway.postMessage("flood", JSON.stringify({
                                                         "color": index,
                                                         "player": player
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
                        multiplayer = dialog.multiplayer
                        startNewGame()
                    })
                }
            }
        }

        PushUpMenu {
            MenuItem {
                text: qsTr("About")
                onClicked: {
                    Qt.openUrlExternally(
                                "https://github.com/thigg/sailing-the-flood-to-java")
                }
            }
        }

        Item {
            anchors.fill: parent

            PlayerControl {
                id: secondPlayerControl
                playerId: 1
                y: ((parent.height - gamePanel.height) / 4) - (height / 2)
                visible: multiplayer
            }
            PlayerScore {
                anchors.top: secondPlayerControl.bottom
                rotation: 180
                playerId: 1
                visible: multiplayer
            }

            GamePanel {
                id: gamePanel
                x: Theme.paddingLarge
                anchors.centerIn: parent
                width: parent.width - Theme.paddingLarge * 2
                height: width
            }

            PlayerScore {
                anchors.bottom: firstPlayerControl.top
                playerId: 0
            }

            PlayerControl {
                id: firstPlayerControl
                playerId: 0
                y: parent.height - ((parent.height - gamePanel.height) / 4) - (height / 2)
            }
        }
    }
}
