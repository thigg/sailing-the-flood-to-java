#include "jgateway.h"

#ifdef QT_QML_DEBUG
#include <QtQuick>
#endif

#include <QtQuick>
#include "../lib/sailfishjava.h"

#include <cstring>
#include <string>
#include <QPainter>
#include "gamestate_generated.h"
#include "flatbuffers/flatbuffers.h"


JGateway::JGateway(graal_isolatethread_t *threadPtr, QGameState *gameStatePtr) {
    thread = threadPtr;
    m_gameState = gameStatePtr;
}


void JGateway::postMessage(const QString &qMethodName, const QString &msg) {
    qDebug() << "Called the C++ method with" << msg;
    char *result;
    int result_len;
    JGateway__invoke(thread, qMethodName.toUtf8().data(), msg.toUtf8().data(), &result, &result_len);
    auto gamestate = Game::model::UnPackGameState(result);

    m_gameState->load( &gamestate->field,
                       gamestate->width,
                       gamestate->height,
                      gamestate->won, gamestate->steps,
                     gamestate->max_steps);
    delete result;
    emit invocationResult();

}





