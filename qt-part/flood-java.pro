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
    src/cbor.cpp \
    src/flood-java.cpp \
    src/gamepanel.cpp \
    src/gamestate.cpp \
    src/jgateway.cpp

DISTFILES += \
    flood-java.desktop \
    lib/libsailfishjava.so \
    qml/cover/CoverPage.qml \
    qml/flood-java.qml \
    qml/pages/GamePage.qml \
    qml/pages/NewGameDialog.qml \
    qml/pages/About.qml \
    translations/*.ts

SAILFISHAPP_ICONS = 86x86 108x108 128x128 172x172

# to disable building translations every time, comment out the
# following CONFIG line
CONFIG += sailfishapp_i18n

# German translation is enabled as an example. If you aren't
# planning to localize your app, remember to comment out the
# following TRANSLATIONS line. And also do not forget to
# modify the localized app name in the the .desktop file.
TRANSLATIONS += translations/java-qt-host-de.ts

HEADERS += \
    lib/graal_isolate.h \
    lib/sailfishjava.h \    
    lib/flatbuffers-2.0.0/include/flatbuffers/*.h \
    lib/flatbuffers-2.0.0/include/flatbuffers/pch/*.h \
    src/cbor.h \
    src/gamestate_generated.h \
    src/gamepanel.h \
    src/gamestate.h \
    src/jgateway.h

javalib.files = $$PWD/lib/libsailfishjava.so
javalib.path = $$[QT_INSTALL_LIBS]

INSTALLS += javalib
LIBS += -L$$PWD/lib/ -lsailfishjava

#win32:CONFIG(release, debug|release): LIBS += -L$$PWD/../target/release/ -lnativeimpl
#else:win32:CONFIG(debug, debug|release): LIBS += -L$$PWD/../target/debug/ -lnativeimpl
#else:unix: LIBS += -L$$PWD/../target/ -lnativeimpl

INCLUDEPATH += $$PWD/../target
INCLUDEPATH += $$PWD/lib
INCLUDEPATH += $$PWD/lib/flatbuffers-2.0.0/include
DEPENDPATH += $$PWD/../target
DEPENDPATH += $$PWD/lib
