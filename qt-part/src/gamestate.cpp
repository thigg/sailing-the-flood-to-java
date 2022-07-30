#include "gamestate.h"
#include <QtQuick>


void GameState::load(const std::vector<std::vector<fieldValue>> * fieldPtr,bool won, int steps, int maxSteps){
    m_won = won;
    m_steps=steps;
    m_maxSteps = maxSteps;
    field = *fieldPtr;
    qDebug()<<"loading new gamestate"<<won<<steps<<maxSteps<<field.size();
    emit wonChanged(won);
    emit stepsChanged(steps);
    emit maxStepsChanged(maxSteps);
}

bool GameState::getWon()
{
    return m_won;
}

int GameState::getSteps()
{
    return m_steps;
}

int GameState::getMaxSteps()
{
    return m_maxSteps;
}

