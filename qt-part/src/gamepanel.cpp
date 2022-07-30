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
    int y=0;
    int tilesize = this->width() / m_gameState->field.size();
    for (const std::vector<GameState::fieldValue> &row: m_gameState->field){
        int x = 0;
        for (const short fieldValue: row){
            painter->fillRect(x*tilesize,y*tilesize,tilesize,tilesize,colors.at(fieldValue));
            x++;
        }
        y++;
    }
}

void GamePanel::setGameState(GameState *state)
{
    m_gameState = state;
}

GameState *GamePanel::getGameState()
{
    return m_gameState;
}

