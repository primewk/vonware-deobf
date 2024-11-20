package me.spikestinger.Vonware.features.modules.movement;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;

public class ReverseStep extends Module {
   public final Setting<Integer> hight = this.register(new Setting("Hight", 2, 1, 5));

   public ReverseStep() {
      super("ReverseStep", "step but reversed..", Module.Category.MOVEMENT, true, false, false);
   }

   public void onUpdate() {
      if (!nullCheck()) {
         if (!mc.field_1724.method_5771() && !mc.field_1724.method_5799() && mc.field_1724.method_24828()) {
            mc.field_1724.method_5762(0.0D, (double)(-(Integer)this.hight.getValue()), 0.0D);
         }
      }
   }
}
