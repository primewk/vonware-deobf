package me.spikestinger.Vonware.features.commands.impl;

import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.commands.Command;
import net.minecraft.class_124;

public class PrefixCommand extends Command {
   public PrefixCommand() {
      super("prefix", new String[]{"<char>"});
   }

   public void execute(String[] commands) {
      if (commands.length == 1) {
         class_124 var10000 = class_124.field_1060;
         Command.sendMessage(var10000 + "Current prefix is " + Vonware.commandManager.getPrefix());
      } else {
         Vonware.commandManager.setPrefix(commands[0]);
         Command.sendMessage("Prefix changed to " + class_124.field_1080 + commands[0]);
      }
   }
}
