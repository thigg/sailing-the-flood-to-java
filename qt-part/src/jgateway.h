#ifndef JGATEWAY_H
#define JGATEWAY_H


#include <QObject>
#include "lib/graal_isolate.h"
#include <QVariant>

#include "gamestate.h"
#include <QImage>

class JGateway: public QObject
{
    graal_isolatethread_t *thread;
    Q_OBJECT
public:
    JGateway(graal_isolatethread_t *thread, QGameState *gameState);
    Q_INVOKABLE void postMessage(const QString &qMethodName, const QString &msg);


signals:
     void invocationResult();
public slots:

private:
     QGameState * m_gameState;

};

#endif // JGATEWAY_H
