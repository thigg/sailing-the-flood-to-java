#include "gamestate.h"
#include <QtQuick>


void QGameState::load(const fieldType * fieldPtr,int width, int height,bool won, int steps, int maxSteps){
    m_won = won;
    m_steps=steps;
    m_maxSteps = maxSteps;
    field = *fieldPtr;
    m_fieldWidth = width;
    m_fieldHeight = height;
    qDebug()<<"loading new gamestate"<<won<<steps<<maxSteps<<field.size();
    emit wonChanged(won);
    emit stepsChanged(steps);
    emit maxStepsChanged(maxSteps);
}

bool QGameState::getWon()
{
    return m_won;
}

int QGameState::getSteps()
{
    return m_steps;
}

int QGameState::getMaxSteps()
{
    return m_maxSteps;
}

