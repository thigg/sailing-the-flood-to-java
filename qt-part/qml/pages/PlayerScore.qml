import QtQuick 2.0
import Sailfish.Silica 1.0
import GamePanel 1.0

Label {
    property int playerId
    text: (GameState.won ? qsTr("You won! ") : "") + qsTr(
              "Steps: %1 ComboScore: %2").arg(GameState.steps[playerId]
                                              || 0).arg(
              GameState.comboScore[playerId] || 0)
}
