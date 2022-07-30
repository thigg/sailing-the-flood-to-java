#include "jgateway.h"

#ifdef QT_QML_DEBUG
#include <QtQuick>
#endif

#include <QtQuick>
#include "../lib/sailfishjava.h"

#include <cstring>
#include <string>
#include "cbor.h"
#include <QPainter>


JGateway::JGateway(graal_isolatethread_t *threadPtr, GameState *gameStatePtr) {
    thread = threadPtr;
    m_gameState = gameStatePtr;
}

GameState::fieldType cborToVec(cbor::array fieldVec){
    if(fieldVec.empty()){
          throw std::invalid_argument( "fieldVector rows was empty" );
    }
    cbor firstRow = fieldVec.at(0);
    if(!firstRow.is_array() || firstRow.to_array().empty()){
        throw std::invalid_argument( "fieldVector cols was empty" );
    }
    auto firstArray = firstRow.to_array();
    int fieldSize = firstArray.size();
    GameState::fieldType field(fieldSize,std::vector<GameState::fieldValue>(fieldSize));
    for(int y = 0; y < fieldSize;y++){
        auto row = fieldVec[y].to_array();
        for (int x  = 0; x < fieldSize;x++){
            field[x][y] = row[x].to_signed();
        }
    }
    return field;
}

void JGateway::postMessage(const QString &qMethodName, const QString &msg) {
    qDebug() << "Called the C++ method with" << msg;
    char *result;
    int result_len;
    JGateway__invoke(thread, qMethodName.toUtf8().data(), msg.toUtf8().data(), &result, &result_len);
    std::vector<unsigned char> vecResult(result, result + result_len);
    std::map<cbor, cbor> resultMap = cbor::decode(vecResult).to_map();

    if (resultMap.count("field") == 0 || 0 == resultMap.count("won") || 0 == resultMap.count("steps") ||
        0 == resultMap.count("maxSteps") || !resultMap.at("field").is_array()) {
        throw std::invalid_argument( "gamestate is missing keys or field is not an array");
    }
    GameState::fieldType field = cborToVec(resultMap.at("field").to_array());
    m_gameState->load( &field,
                      resultMap.at("won").to_bool(), resultMap.at("steps").to_signed(),
                      resultMap.at("maxSteps").to_signed());
    delete result;
    emit invocationResult();

}





