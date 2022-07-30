#ifndef GAMESTATE_H
#define GAMESTATE_H

#include <QObject>
#include "cbor.h"

class GameState: public QObject
{

    Q_OBJECT

    Q_PROPERTY( bool won READ getWon NOTIFY wonChanged);
    Q_PROPERTY( int steps READ getSteps NOTIFY stepsChanged);
    Q_PROPERTY( int maxSteps READ getMaxSteps NOTIFY maxStepsChanged);
public:
    typedef short fieldValue;
    typedef std::vector<std::vector<fieldValue>> fieldType;
    void load(const fieldType *field, bool won, int steps, int maxSteps);

    bool getWon();
    int getSteps();
    int getMaxSteps();
    fieldType field = fieldType();

signals:
    void wonChanged(bool);
    void stepsChanged(int);
    void maxStepsChanged(int);
public slots:
private:
    bool m_won;
    int m_steps;
    int m_maxSteps;
};
#endif // GAMESTATE_H
