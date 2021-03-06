package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class UntilUnsuccessful extends Unit {
    private final Unit childUnit;
    private boolean running = false;

    public UntilUnsuccessful(Unit childUnit){
        this.childUnit = childUnit;

        this.hookBottom(Event.START, () -> {
            this.running = true;
            this.childUnit.start();
        });
        this.childUnit.hookTop(new Event[]{Event.FAIL, Event.INTERRUPT}, () -> {
            this.running = false;
            this.fireDown(Event.INTERRUPT);
            this.fireUp(Event.FAIL);
        });
        this.childUnit.hookTop(Event.SUCCESS, () -> {
            if(this.running) {
                this.childUnit.start();
            }
        });

        this.hookBottom(Event.INTERRUPT, () -> {
            this.running = false;
            this.childUnit.fireDown(Event.INTERRUPT);
        });
    }
}
