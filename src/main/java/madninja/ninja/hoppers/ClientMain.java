package madninja.ninja.hoppers;

import madninja.ninja.hoppers.screen.NetheriteHopperScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(Main.NETHERITE_HOPPER_SCREEN_HANDLER_TYPE, NetheriteHopperScreen::new);
    }
}
