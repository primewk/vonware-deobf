package me.spikestinger.Vonware.features.commands;

import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.Feature;
import net.minecraft.class_124;
import net.minecraft.class_2561;

public abstract class Command extends Feature {
   protected String name;
   protected String[] commands;

   public Command(String name) {
      super(name);
      this.name = name;
      this.commands = new String[]{""};
   }

   public Command(String name, String[] commands) {
      super(name);
      this.name = name;
      this.commands = commands;
   }

   public static void sendMessage(String message) {
      String var10000 = Vonware.commandManager.getClientMessage();
      sendSilentMessage(var10000 + " " + class_124.field_1080 + message);
   }

   public static void sendSilentMessage(String message) {
      if (!nullCheck()) {
         mc.field_1705.method_1743().method_1812(class_2561.method_43470(message));
      }
   }

   public static String getCommandPrefix() {
      return Vonware.commandManager.getPrefix();
   }

   public abstract void execute(String[] var1);

   public String getName() {
      return this.name;
   }

   public String[] getCommands() {
      return this.commands;
   }
}
