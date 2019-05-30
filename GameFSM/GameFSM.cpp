#include <functional>
#include <iostream>
#include <cmath>
#include <thread>
#include <chrono>
#include <stack>
#include <cstdlib>
#include <time.h>

using namespace std;

int los(int x, int y){
    srand(time(NULL));

    return rand()%y+x;
}

template<typename T, typename... U>
long getAddress(std::function<T(U...)> f) {
    return *(long *)(char *)&f;
}

class FSM{
private:
    stack<function<void ()>> statesStack;
public:
    FSM(){

    }

    function<void ()> popState(){
        function<void ()> result = this->statesStack.top();
        this->statesStack.pop();
        return result;
    }

    void pushState(function<void ()> state){
        function<void ()> activeState = getCurrentState();
        if(activeState == NULL)
            this->statesStack.push(state);
        else if(getAddress(activeState) == getAddress(state))
            this->statesStack.push(state);
    }

    function<void ()> getCurrentState(){
        return this->statesStack.size() > 0 ? this->statesStack.top() : NULL;
    }

    void update(){
        function<void ()> activeState = getCurrentState();
        if(activeState != NULL) activeState();
    }
};

class Gladiator{
public:
    int Lvl;
    int MaxHP;
    int HPnow;
    int Strengh;
    int Agility;
    int Healing;

    Gladiator(int lvl, int HP, int S, int A, int H){
        this->Lvl = lvl;
        this->MaxHP = HP;
        this->HPnow = HP;
        this->Strengh = S;
        this->Agility = A;
        this->Healing = H;
    }
};

class Arena{
public:
    Gladiator *player;
    Gladiator *enemy;
    FSM *King;

    Arena(){
        this->King = new FSM();
        this->player = new Gladiator(1, 200, 3, 5, 2);
        this->enemy = new Gladiator(1, 20, 2, 1, 0);
        this->King->pushState(bind(&PlayerMove, this));
    }

    void HP(){
        cout << "LEVEL: " << player->Lvl << "   HP Gladiatora: " << player->HPnow << "/" << player->MaxHP << "  Strengh: " << player->Strengh << "  Agility: " << player->Agility << "  Healing: " << player->Healing << endl;
        cout << "LEVEL: " << enemy->Lvl << "   HP Przeciwnika: " << enemy->HPnow << "/" << enemy->MaxHP << endl;
    }

    void PlayerMove(){
        system( "cls" );
        HP();
        cout << "Wybierz opcje:\n 1. Atak\n 2. Leczenie\n 3. Poddanie sie" <<endl;
        int choose;
        cin >> choose;

        if(choose == 1){
            if(los(1, 100) > enemy->Agility){
                int x = los(player->Strengh, player->Strengh + 3);
                enemy->HPnow = enemy->HPnow - x;

                cout << "ZADAJESZ: " << x << " OBRAZEN" << endl;
                this_thread::sleep_for(chrono::milliseconds(400));

                if(enemy->HPnow <= 0){
                    this->King->popState();
                    this->King->pushState(bind(&LevelUp, this));
                    return;
                }
            }
            else{
                cout << "\n\n\n\n\nUNIK!!!" << endl;
                this_thread::sleep_for(chrono::milliseconds(400));
            }

            this->King->popState();
            this->King->pushState(bind(&EnemyMove, this));
        }

        else if(choose == 2){
            int x = los(player->Healing, player->Healing + 5);
            player->HPnow += x;

            if(player->HPnow > player->MaxHP){
                player->HPnow = player->MaxHP;
            }

            system( "cls" );
            HP();
            cout << "\n\n\n\n\nLECZYSZ SIE ZA: " << x << endl;
            this_thread::sleep_for(chrono::milliseconds(400));

            this->King->popState();
            this->King->pushState(bind(&EnemyMove, this));
        }

        else if(choose == 3){
            this->King->popState();
            this->King->pushState(bind(&End, this));
        }
    }

    void EnemyMove(){
        system( "cls" );
        HP();

        if((enemy->MaxHP / (los(10, 40))) < enemy->HPnow){
            if(los(1, 100) > player->Agility){
                int x = los(enemy->Strengh, enemy->Strengh + 3);
                player->HPnow = player->HPnow - x;

                cout << "\n\n\n\n\nPPRZECIWNIK ZADAJE: " << x << " OBRAZEN" << endl;
                this_thread::sleep_for(chrono::milliseconds(400));
            }
            else{
                cout << "\n\n\n\n\nUNIK!!!" << endl;
                this_thread::sleep_for(chrono::milliseconds(400));
            }
            if(player->HPnow <= 0){
                this->King->popState();
                this->King->pushState(bind(&End, this));
                return;
            }
        }
        else{
            int x = los(enemy->Healing, enemy->Healing + 2);
            enemy->HPnow += x;

            if(enemy->HPnow > enemy->MaxHP){
                enemy->HPnow = enemy->MaxHP;
            }

            system( "cls" );
            HP();
            cout << "\n\n\n\n\nLECZY SIE ZA: " << x << endl;
            this_thread::sleep_for(chrono::milliseconds(400));
        }

        this->King->popState();
        this->King->pushState(bind(&PlayerMove, this));
    }

    void End(){
        this->King->popState();
        system( "cls" );
        cout << "KONIEC GRY" << endl;
        cout << "OSIAGNALES " << player->Lvl << " LEVEL" << endl;
        cout << "HP " << player->MaxHP << "  Strengh: " << player->Strengh << "  Agility: " << player->Agility << "  Healing: " << player->Healing << endl;
        exit(0);
    }

    void LevelUp(){
        system( "cls" );
        cout << "LEVEL: " << player->Lvl << "HP Gladiatora: " << player->HPnow << "/" << player->MaxHP << "  Strengh: " << player->Strengh << "  Agility: " << player->Agility << "  Healing: " << player->Healing << endl;
        cout << "Zwiekszenie umiejetnosci:\n 1. HP (+10)\n 2. Strengh\n 3. Agility\n 4. Healing" <<endl;

        int choose;
        cin >> choose;

        if(choose == 1){
            player->MaxHP += 10;
            player->HPnow += 10;
        }
        else if(choose == 2){
            player->Strengh++;
        }
        else if(choose == 3){
            if(player->Agility > 50){
            	cout << "MAKSYMALNA ILOSC AGILITY!!! ROZDAJ PUNKTY PONOWNIE" << endl;
            	this_thread::sleep_for(chrono::milliseconds(1000));
            	this->King->pushState(bind(&LevelUp, this));
            	return;
			}
			player->Agility++;
        }
        else if(choose == 4){
            player->Healing++;
        }

        player->HPnow = player->HPnow + player->MaxHP * 0.25;

        if(player->HPnow > player->MaxHP){
            player->HPnow = player->MaxHP;
        }

        player->Lvl++;
        system( "cls" );
        cout << "LEVEL: " << player->Lvl << "HP Gladiatora: " << player->HPnow << "/" << player->MaxHP << "  Strengh: " << player->Strengh << "  Agility: " << player->Agility << "  Healing: " << player->Healing << endl;
        system("PAUSE");
        this->King->popState();
        this->King->pushState(bind(&RandomEnemy, this));
    }

    void RandomEnemy(){
        int lvl = los(player->Lvl, player->Lvl + 4);
        int pu = lvl + 4;
        int hp = los(1, pu);
        int S = los(100, pu * 60);
        S = S/100;
        pu -= S;
        int H = los(100, pu * 80);
        H = H/100;
        pu -= H;

        if(pu < 0){
            pu = 0;
        }
        
        if(pu > 50){
            pu = 50;
        }
        
        this->enemy = new Gladiator(lvl, hp * 10, S, pu, H);
        system( "cls" );
        cout << "NOWY PRZECIWNIK" << endl;
        this_thread::sleep_for(chrono::milliseconds(400));

        this->King->popState();
        this->King->pushState(bind(&PlayerMove, this));
    }

    void update(){
        King->update();
    }
};

int main(){
    Arena *game = new Arena();

    while(true){
        //this_thread::sleep_for(chrono::milliseconds(200));
        game->update();
    }

    return 0;
}
