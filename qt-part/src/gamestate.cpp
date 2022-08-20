#include "gamestate.h"
#include <QtQuick>


QVariantList toVarList(std::vector<int>* arg){
    QVariantList result;
    for(int s: *arg)
        result.push_back(QVariant(s));
    return result;
}



void QGameState::load(const fieldType * fieldPtr,int width, int height,bool won, std::vector<int> steps, int maxSteps, std::vector<std::unique_ptr<Game::model::ScoreT>>* scores){
    m_won = won;
    m_steps=toVarList(&steps);
    m_maxSteps = maxSteps;
    field = *fieldPtr;
    m_fieldWidth = width;
    m_fieldHeight = height;

    std::vector<int> combo_scores(scores->size());
    qDebug() << "Sizes" << combo_scores.size()<<scores->size();
    for(auto &s: *scores)
        combo_scores[s->player] =s->combo_score;
    m_comboScore = toVarList(&combo_scores);
    qDebug()<<"loading new gamestate"<<won<<maxSteps<<field.size();
    emit wonChanged(won);
    emit stepsChanged(getSteps());
    emit maxStepsChanged(maxSteps);
    emit comboScoreChanged(getComboScore());
}

bool QGameState::getWon()
{
    return m_won;
}


QVariantList QGameState::getSteps()
{
    return m_steps;
}

int QGameState::getMaxSteps()
{
    return m_maxSteps;
}


QVariantList QGameState::getComboScore()
{
    return m_comboScore;
}

