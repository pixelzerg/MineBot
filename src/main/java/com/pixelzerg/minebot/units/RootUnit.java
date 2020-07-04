package com.pixelzerg.minebot.units;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RootUnit extends Unit {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        Minecraft mc = Minecraft.getInstance();

        if (player != null) {
            String msg = event.getMessage().getString();
            if (msg.contentEquals("<" + player.getDisplayName().getString() + "> start")) {
                new MoveToUnit(player.getPosition().add(new Vec3i(10, 0, 0)))
                    .on(Unit.EVENT_STARTED, new Runnable() {
                        @Override
                        public void run() {
                            LOGGER.debug("Started MoveToUnit!");
                        }
                    })
                    .on(Unit.EVENT_DONE, new Runnable() {
                        @Override
                        public void run() {
                            LOGGER.debug("Finished MoveToUnit!");
                        }
                    })
                    .start();
            }
        }
    }
}
