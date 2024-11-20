package me.spikestinger.Vonware.features.commands.impl;

import com.google.gson.JsonParser;
import java.util.Iterator;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.manager.ConfigManager;
import net.minecraft.class_124;

public class ModuleCommand extends Command {
   public ModuleCommand() {
      super("module", new String[]{"<module>", "<set/reset>", "<setting>", "<value>"});
   }

   public void execute(String[] commands) {
      if (commands.length == 1) {
         sendMessage("Modules: ");
         Iterator var9 = Vonware.moduleManager.getCategories().iterator();

         while(var9.hasNext()) {
            Module.Category category = (Module.Category)var9.next();
            String modules = category.getName() + ": ";

            Module module1;
            for(Iterator var6 = Vonware.moduleManager.getModulesByCategory(category).iterator(); var6.hasNext(); modules = modules + (module1.isEnabled() ? class_124.field_1060 : class_124.field_1061) + module1.getName() + class_124.field_1068 + ", ") {
               module1 = (Module)var6.next();
            }

            sendMessage(modules);
         }

      } else {
         Module module = Vonware.moduleManager.getModuleByDisplayName(commands[0]);
         if (module == null) {
            module = Vonware.moduleManager.getModuleByName(commands[0]);
            if (module == null) {
               sendMessage("This module doesnt exist.");
            } else {
               sendMessage(" This is the original name of the module. Its current name is: " + module.getDisplayName());
            }
         } else {
            Setting setting3;
            Iterator var10;
            if (commands.length == 2) {
               String var13 = module.getDisplayName();
               sendMessage(var13 + " : " + module.getDescription());
               var10 = module.getSettings().iterator();

               while(var10.hasNext()) {
                  setting3 = (Setting)var10.next();
                  var13 = setting3.getName();
                  sendMessage(var13 + " : " + setting3.getValue() + ", " + setting3.getDescription());
               }

            } else if (commands.length == 3) {
               if (commands[1].equalsIgnoreCase("set")) {
                  sendMessage("Please specify a setting.");
               } else if (commands[1].equalsIgnoreCase("reset")) {
                  var10 = module.getSettings().iterator();

                  while(var10.hasNext()) {
                     setting3 = (Setting)var10.next();
                     setting3.setValue(setting3.getDefaultValue());
                  }
               } else {
                  sendMessage("This command doesnt exist.");
               }

            } else if (commands.length == 4) {
               sendMessage("Please specify a value.");
            } else {
               Setting setting;
               if (commands.length == 5 && (setting = module.getSettingByName(commands[2])) != null) {
                  JsonParser jp = new JsonParser();
                  class_124 var10000;
                  if (setting.getType().equalsIgnoreCase("String")) {
                     setting.setValue(commands[3]);
                     var10000 = class_124.field_1063;
                     sendMessage(var10000 + module.getName() + " " + setting.getName() + " has been set to " + commands[3] + ".");
                     return;
                  }

                  try {
                     if (setting.getName().equalsIgnoreCase("Enabled")) {
                        if (commands[3].equalsIgnoreCase("true")) {
                           module.enable();
                        }

                        if (commands[3].equalsIgnoreCase("false")) {
                           module.disable();
                        }
                     }

                     ConfigManager.setValueFromJson(module, setting, jp.parse(commands[3]));
                  } catch (Exception var8) {
                     sendMessage("Bad Value! This setting requires a: " + setting.getType() + " value.");
                     return;
                  }

                  var10000 = class_124.field_1080;
                  sendMessage(var10000 + module.getName() + " " + setting.getName() + " has been set to " + commands[3] + ".");
               }

            }
         }
      }
   }
}
