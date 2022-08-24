# NOTICE:
#
# Application name defined in TARGET has a corresponding QML filename.
# If name defined in TARGET is changed, the following needs to be done
# to match new name:
#   - corresponding QML filename must be changed
#   - desktop icon filename must be changed
#   - desktop filename must be changed
#   - icon definition filename in desktop file must be changed
#   - translation filenames have to be changed

# The name of your application
TARGET = flood-java

CONFIG += sailfishapp

SOURCES += \
    src/flood-java.cpp \
    src/gamepanel.cpp \
    src/gamestate.cpp \
    src/jgateway.cpp

DISTFILES += \
    flood-java.desktop \
    lib/libjavafloodjava.so \
    qml/cover/CoverPage.qml \
    qml/flood-java.qml \
    qml/pages/GamePage.qml \
    qml/pages/NewGameDialog.qml \
    qml/pages/PlayerControl.qml \
    qml/pages/PlayerScore.qml \
    src/Game/model/GameState.java \
    src/Game/model/GameStateT.java \
    src/Game/model/Score.java \
    src/Game/model/ScoreT.java \
    translations/*.ts \
    translations/java-qt-host-en.ts

SAILFISHAPP_ICONS = 86x86 108x108 128x128 172x172

# to disable building translations every time, comment out the
# following CONFIG line
CONFIG += sailfishapp_i18n

OTHER_FILES += rpm/flood-java.changes\
rpm/flood-java.spec

TRANSLATIONS += translations/flood-java-de.ts translations/flood-java-en.ts

HEADERS += \
    lib/graal_isolate.h \
    lib/flatbuffers/*.h \
    lib/flatbuffers/pch/*.h \
    lib/javafloodjava.h \
    src/gamestate_generated.h \
    src/gamepanel.h \
    src/gamestate.h \
    src/jgateway.h

javalib.files = $$PWD/lib/libjavafloodjava.so
javalib.path = $$[QT_INSTALL_LIBS]

INSTALLS += javalib
LIBS += -L$$PWD/lib/ -ljavafloodjava

#win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../target/release/ -lnativeimpl
#else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../target/debug/ -lnativeimpl
#else:unix: LIBS += -L$$PWD/../target/ -lnativeimpl

INCLUDEPATH += $$PWD/../target
INCLUDEPATH += $$PWD/lib
DEPENDPATH += $$PWD/../target
DEPENDPATH += $$PWD/lib
