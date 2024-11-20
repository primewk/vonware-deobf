package me.spikestinger.Vonware.features.modules.movement;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;

public class Step extends Module {
   private final Setting<Float> height = this.register(new Setting("Height", 2.0F, 1.0F, 3.0F, (v) -> {
      return true;
   }));

   public Step() {
      super("Step", "step..", Module.Category.MOVEMENT, true, false, false);
   }

   public void onDisable() {
      if (!nullCheck()) {
         mc.field_1724.method_49477(0.6F);
      }
   }

   public void onUpdate() {
      if (!nullCheck()) {
         mc.field_1724.method_49477((Float)this.height.getValue());
      }
   }
}
