package me.spikestinger.Vonware.features.commands.impl;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.event.impl.KeyEvent;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Bind;
import me.spikestinger.Vonware.util.KeyboardUtil;
import net.minecraft.class_124;

public class BindCommand extends Command {
   private boolean listening;
   private Module module;

   public BindCommand() {
      super("bind", new String[]{"<module>"});
      EVENT_BUS.register(this);
   }

   public void execute(String[] commands) {
      if (commands.length == 1) {
         sendMessage("Please specify a module.");
      } else {
         String moduleName = commands[0];
         Module module = Vonware.moduleManager.getModuleByName(moduleName);
         if (module == null) {
            sendMessage("Unknown module '" + module + "'!");
         } else {
            sendMessage(class_124.field_1080 + "Press a key.");
            this.listening = true;
            this.module = module;
         }
      }
   }

   @Subscribe
   private void onKey(KeyEvent event) {
      if (!nullCheck() && this.listening) {
         this.listening = false;
         if (event.getKey() == 256) {
            sendMessage(class_124.field_1080 + "Operation cancelled.");
         } else {
            class_124 var10000 = class_124.field_1060;
            sendMessage("Bind for " + var10000 + this.module.getName() + class_124.field_1068 + " set to " + class_124.field_1080 + KeyboardUtil.getKeyName(event.getKey()));
            this.module.bind.setValue(new Bind(event.getKey()));
         }
      }
   }
}
