import QtQuick 2.2
import Sailfish.Silica 1.0

Dialog {

    Column {
        width: parent.width

        DialogHeader {}

                     
            Text {
                id: aboutText
                text: qsTr("text")
            }
        
    }

    onDone: {
       
    }
}
