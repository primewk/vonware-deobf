package me.spikestinger.Vonware.features.modules.misc;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;

public class NameHider extends Module {
   public static NameHider INSTANCE;
   public final Setting<String> newName = this.register(new Setting("[REDACTED]", "[REDACTED]"));

   public NameHider() {
      super("NameHider", "hider but name", Module.Category.MISC, true, false, false);
      INSTANCE = this;
   }
}
