package com.bomberman.common.engine;


import java.util.concurrent.atomic.AtomicInteger;

import static com.bomberman.common.utils.ClientServerCommunicationUtils.BOMB_TICK_DELAY;
import static com.bomberman.common.utils.EngineUtils.*;
import static com.bomberman.common.utils.EngineUtils.Direction.*;

import com.bomberman.common.events.BombDetonateEvent;
import com.bomberman.common.model.Bomb;

public class BombHandler {

    private final Bomb bomb;

    private final AtomicInteger counter;

    private final EventListener listener;

    /*
        Create handler for specific bomb
     */
    public BombHandler(Bomb bomb, EventListener listener) {
        this.bomb = bomb;
        this.listener = listener;
        counter = new AtomicInteger(DETONATION_TIME);
    }

    public void serviceBomb() {
        Thread thread = new Thread(() -> {
            while (counter.get() > 0) {
                try {
                    Thread.sleep(BOMB_TICK_DELAY);
                    counter.decrementAndGet();
                    bomb.bombTick();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            detonateBomb();
        });
        thread.start();
    }

    public void detonateBomb() {
        listener.notify(new BombDetonateEvent(
                this.bomb.getPositionX(),
                this.bomb.getPositionY(),
                this.bomb.getBombRadius()
        ));
    }

    public boolean positionMatch(int x, int y) {
        return bomb.positionMatch(x, y);
    }

    public void moveBomb(Direction direction) {
        if (direction == TOP) {
            this.bomb.setPosition(this.bomb.getPositionX(), this.bomb.getPositionY() + 1);
        } else if (direction == BOT) {
            this.bomb.setPosition(this.bomb.getPositionX(), this.bomb.getPositionY() - 1);
        } else if (direction == RIGHT) {
            this.bomb.setPosition(this.bomb.getPositionX() + 1, this.bomb.getPositionY());
        } else if (direction == LEFT) {
            this.bomb.setPosition(this.bomb.getPositionX() - 1, this.bomb.getPositionY());
        }
    }
}
