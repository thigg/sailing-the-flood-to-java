#ifndef GAMESTATE_H
#define GAMESTATE_H

#include "gamestate_generated.h"

#include <QObject>
#include <QVariantList>

#include <bits/unique_ptr.h>

class QGameState: public QObject
{

    Q_OBJECT

    Q_PROPERTY( bool won READ getWon NOTIFY wonChanged);
    Q_PROPERTY( QVariantList steps READ getSteps NOTIFY stepsChanged);
    Q_PROPERTY( int maxSteps READ getMaxSteps NOTIFY maxStepsChanged);
    Q_PROPERTY( QVariantList comboScore READ getComboScore NOTIFY comboScoreChanged);

public:
    typedef int16_t fieldValue;
    typedef std::vector<fieldValue> fieldType;
    void load(const fieldType *field,int width, int height,
              bool won, std::vector<int> steps,
              int maxSteps,
              std::vector<std::unique_ptr<Game::model::ScoreT>> *comboScore);

    bool getWon();
    QVariantList getSteps();
    int getMaxSteps();
    QVariantList getComboScore();

    int m_fieldHeight;
    int m_fieldWidth;
    fieldType field = fieldType();

signals:
    void wonChanged(bool);
    void stepsChanged(QVariantList);
    void maxStepsChanged(int);
    void comboScoreChanged(QVariantList);
public slots:
private:
    bool m_won;
    QVariantList m_steps;
    int m_maxSteps;
    QVariantList m_comboScore;
};
Q_DECLARE_METATYPE(std::vector<int>)
#endif // GAMESTATE_H
