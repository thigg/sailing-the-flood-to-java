#include <QtQuick>

#include <sailfishapp.h>
#include <cstdio>
#include "../lib/graal_isolate.h"
#include "../lib/javafloodjava.h"
#include "gamepanel.h"
#include "src/jgateway.h"

int main(int argc, char *argv[])
{
    graal_isolatethread_t *thread = nullptr;
    if (graal_create_isolate(nullptr, nullptr, &thread) != 0) {
        fprintf(stderr, "error on isolate creation or attach\n");
        return 1;
    }
    QScopedPointer<QGuiApplication> app(SailfishApp::application(argc, argv));
    QScopedPointer<QQuickView> view(SailfishApp::createView());

    QQmlContext *context = view.data()->rootContext();
    QGameState gameState;
    JGateway javaGateway(thread, &gameState);


    //Init JGateway
    JGateway__init(thread);


    qmlRegisterType<GamePanel>("GamePanel", 1, 0 , "GamePanel");
    context->setContextProperty("JGateway", &javaGateway);    
    context->setContextProperty("GameState", &gameState);


    view->setSource(SailfishApp::pathTo("qml/harbour-flood-java.qml"));
    view->show();

    return app->exec();
}
