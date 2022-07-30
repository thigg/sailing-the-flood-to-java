import QtQuick 2.2
import Sailfish.Silica 1.0

Dialog {
    property int gameSize
    property int numColors

    Column {
        width: parent.width

        DialogHeader {}

        Slider {
            id: gameSizeSlider
            label: qsTr("Game Tile Size")
            width: parent.width
            minimumValue: 4
            maximumValue: 200
            value: 10
            valueText: value + "×" + value
            stepSize: 1
        }

        Slider {
            id: numColorsSlider
            label: qsTr("Amount of Colors")
            width: parent.width
            minimumValue: 2
            maximumValue: 4
            value: 4
            stepSize: 1
            valueText: value
        }
    }

    onDone: {
        if (result == DialogResult.Accepted) {
            gameSize = gameSizeSlider.value
            numColors = numColorsSlider.value
        }
    }
}
