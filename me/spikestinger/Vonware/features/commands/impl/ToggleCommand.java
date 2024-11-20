package me.spikestinger.Vonware.features.commands.impl;

import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.modules.Module;

public class ToggleCommand extends Command {
   public ToggleCommand() {
      super("toggle", new String[]{"<module>"});
   }

   public void execute(String[] var1) {
      if (var1.length >= 1 && var1[0] != null) {
         Module mod = Vonware.moduleManager.getModuleByName(var1[0]);
         if (mod == null) {
            this.notFound();
         } else {
            mod.toggle();
         }
      } else {
         this.notFound();
      }
   }

   private void notFound() {
      sendMessage("Module is not found.");
   }
}
