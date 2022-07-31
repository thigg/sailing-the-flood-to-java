#include "gamepanel.h"
#include "gamestate.h"

GamePanel::GamePanel()
{

}

void GamePanel::paint(QPainter* painter){
    if(m_gameState == nullptr || m_gameState->field.empty()){
        return;
    }

    std::vector<QColor> colors{QColor("#fbe7c6"), QColor("#b4f8c8"), QColor("#a0e7e5"),QColor( "#ffaebc")};

    int tilesize = this->width() / m_gameState->m_fieldHeight;
    for (int y = 0; y< m_gameState->m_fieldHeight;y++){
        for (int x = 0; x <  m_gameState->m_fieldWidth; x++){
            auto colorIndex =m_gameState->field.at(y* m_gameState->m_fieldWidth+x);
           // if(colorIndex < colors.size())
                painter->fillRect(x*tilesize,y*tilesize,tilesize,tilesize,colors.at(colorIndex));
        }
    }
}

void GamePanel::setGameState(QGameState *state)
{
    m_gameState = state;
}

QGameState *GamePanel::getGameState()
{
    return m_gameState;
}

