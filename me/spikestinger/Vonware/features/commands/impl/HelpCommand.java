package me.spikestinger.Vonware.features.commands.impl;

import java.util.Iterator;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.commands.Command;
import net.minecraft.class_124;

public class HelpCommand extends Command {
   public HelpCommand() {
      super("help");
   }

   public void execute(String[] commands) {
      sendMessage("Commands: ");
      Iterator var2 = Vonware.commandManager.getCommands().iterator();

      while(var2.hasNext()) {
         Command command = (Command)var2.next();
         StringBuilder builder = new StringBuilder(class_124.field_1080.toString());
         builder.append(Vonware.commandManager.getPrefix());
         builder.append(command.getName());
         builder.append(" ");
         String[] var5 = command.getCommands();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String cmd = var5[var7];
            builder.append(cmd);
            builder.append(" ");
         }

         sendMessage(builder.toString());
      }

   }
}
