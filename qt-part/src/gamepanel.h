#ifndef GAMEPANEL_HGa
#define GAMEPANEL_H


#include <QQuickPaintedItem>
#include "gamestate.h"
#include <QPainter>
#include <QObject>

class GamePanel : public QQuickPaintedItem
{
    Q_OBJECT

    Q_PROPERTY(QGameState* gameState READ getGameState WRITE setGameState);
    //Q_PROPERTY(QVector<QColor> colors MEMBER m_colors);
public:
    GamePanel();
    void paint(QPainter* painter);
    void setGameState(QGameState* state);
    QGameState* getGameState();

private:
    QGameState* m_gameState = nullptr;
    QVector<QColor> m_colors;
};

#endif // GAMEPANEL_H
