package me.spikestinger.Vonware.features.modules.misc;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.manager.InventoryManager;
import me.spikestinger.Vonware.manager.NetworkManager;
import net.minecraft.class_1268;
import net.minecraft.class_1781;
import net.minecraft.class_1799;
import net.minecraft.class_2886;
import net.minecraft.class_7204;

public class AutoFirework extends Module {
   public final Setting<Integer> rocketDelay = this.register(new Setting("Rocket Delay", 45, 0, 60));
   private static int delayTimer;

   public AutoFirework() {
      super("AutoFirework", "Automatically uses Fireworks", Module.Category.MISC, true, false, true);
   }

   public void onUpdate() {
      assert mc.field_1724 != null;

      int delayTicks = (Integer)this.rocketDelay.getValue() * 20;
      if (!mc.field_1724.method_6128()) {
         delayTimer = delayTicks;
      } else if (delayTimer >= delayTicks) {
         int rocketSlot = -1;

         for(int i = 0; i < 9; ++i) {
            class_1799 stack = mc.field_1724.method_31548().method_5438(i);
            if (stack.method_7909() instanceof class_1781) {
               rocketSlot = i;
               break;
            }
         }

         InventoryManager.setSlot(rocketSlot);
         class_7204 o = (id) -> {
            return new class_2886(class_1268.field_5808, id);
         };
         delayTimer = 0;
         NetworkManager.sendSequencedPacket(o);
         InventoryManager.syncToClient();
      } else {
         ++delayTimer;
      }

   }
}
