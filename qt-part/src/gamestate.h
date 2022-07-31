#ifndef GAMESTATE_H
#define GAMESTATE_H

#include <QObject>
#include "cbor.h"

class QGameState: public QObject
{

    Q_OBJECT

    Q_PROPERTY( bool won READ getWon NOTIFY wonChanged);
    Q_PROPERTY( int steps READ getSteps NOTIFY stepsChanged);
    Q_PROPERTY( int maxSteps READ getMaxSteps NOTIFY maxStepsChanged);
public:
    typedef int16_t fieldValue;
    typedef std::vector<fieldValue> fieldType;
    void load(const fieldType *field,int width, int height, bool won, int steps, int maxSteps);

    bool getWon();
    int getSteps();
    int getMaxSteps();

    int m_fieldHeight;
    int m_fieldWidth;
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
