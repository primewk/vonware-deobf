package me.spikestinger.Vonware;

import me.spikestinger.Vonware.manager.ColorManager;
import me.spikestinger.Vonware.manager.CommandManager;
import me.spikestinger.Vonware.manager.ConfigManager;
import me.spikestinger.Vonware.manager.EventManager;
import me.spikestinger.Vonware.manager.FriendManager;
import me.spikestinger.Vonware.manager.HoleManager;
import me.spikestinger.Vonware.manager.InteractionManager;
import me.spikestinger.Vonware.manager.InventoryManager;
import me.spikestinger.Vonware.manager.ModuleManager;
import me.spikestinger.Vonware.manager.PositionManager;
import me.spikestinger.Vonware.manager.RotationManager;
import me.spikestinger.Vonware.manager.ServerManager;
import me.spikestinger.Vonware.manager.SpeedManager;
import me.spikestinger.Vonware.manager.TotemPopManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vonware implements ModInitializer, ClientModInitializer {
   public static final String NAME = "Vonware";
   public static final String VERSION = "v1.2";
   public static float TIMER = 1.0F;
   public static final Logger LOGGER = LogManager.getLogger("Vonware");
   public static ServerManager serverManager;
   public static TotemPopManager totemPopManager;
   public static ColorManager colorManager;
   public static RotationManager rotationManager;
   public static PositionManager positionManager;
   public static HoleManager holeManager;
   public static EventManager eventManager;
   public static SpeedManager speedManager;
   public static CommandManager commandManager;
   public static FriendManager friendManager;
   public static ModuleManager moduleManager;
   public static ConfigManager configManager;
   public static InventoryManager inventoryManager;
   public static InteractionManager interactionManager;

   public void onInitialize() {
      eventManager = new EventManager();
      serverManager = new ServerManager();
      rotationManager = new RotationManager();
      positionManager = new PositionManager();
      friendManager = new FriendManager();
      colorManager = new ColorManager();
      commandManager = new CommandManager();
      moduleManager = new ModuleManager();
      speedManager = new SpeedManager();
      holeManager = new HoleManager();
      inventoryManager = new InventoryManager();
      interactionManager = new InteractionManager();
   }

   public void onInitializeClient() {
      eventManager.init();
      moduleManager.init();
      configManager = new ConfigManager();
      ConfigManager var10000 = configManager;
      ConfigManager.load();
      colorManager.init();
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         configManager.save();
      }));
   }
}
