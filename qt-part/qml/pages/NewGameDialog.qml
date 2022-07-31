import QtQuick 2.2
import Sailfish.Silica 1.0

Dialog {
    property int gameSize
    property int numColors

    Column {
        width: parent.width

        DialogHeader {}

        // log-scaled
        Slider {
            id: gameSizeSlider
            label: qsTr("Game Tile Size")
            width: parent.width
            minimumValue: Math.log(4 * scale)
            maximumValue: Math.log(352 * scale)
            value: Math.log(10 * scale)
            property int unLoggedValue: Math.round(Math.pow(Math.E,
                                                            value) / scale)
            property real scale: 1

            valueText: unLoggedValue + "×" + unLoggedValue
            stepSize: 0.01
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
            gameSize = gameSizeSlider.unLoggedValue
            numColors = numColorsSlider.value
        }
    }
}
