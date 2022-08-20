import QtQuick 2.0
import Sailfish.Silica 1.0
import GamePanel 1.0

Grid {
    property int playerId

    columns: floodColors.length
    height: Theme.fontSizeExtraLarge * 2
    width: parent.width - Theme.paddingLarge * 2
    x: Theme.paddingLarge

    spacing: 4

    Repeater {
        model: floodColors.length
        Button {
            height: parent.height
            width: parent.width / floodColors.length
            backgroundColor: floodColors[index]
            enabled: !GameState.won && (!multiplayer || lastPlayer != playerId)
                     && lastColor !== index

            onClicked: flood(index, playerId)
        }
    }
}
