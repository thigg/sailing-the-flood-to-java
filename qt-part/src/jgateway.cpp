#include "jgateway.h"

#ifdef QT_QML_DEBUG
#include <QtQuick>
#endif

#include <QtQuick>
#include "../lib/javafloodjava.h"

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
    char *result;
    int result_len;
    JGateway__invoke(thread, qMethodName.toUtf8().data(), msg.toUtf8().data(), &result, &result_len);

    qDebug() << "java returned";

    auto gamestate = Game::model::UnPackGameState(result);
    qDebug()<< "unpacked gamestate, loading...";


    m_gameState->load( &gamestate->field,
                       gamestate->width,
                       gamestate->height,
                       gamestate->won, gamestate->steps,
                       gamestate->max_steps,
                       &gamestate->scores
                      );
    delete result;
    emit invocationResult();

}





