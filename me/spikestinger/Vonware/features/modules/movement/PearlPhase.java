package me.spikestinger.Vonware.features.modules.movement;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.manager.InventoryManager;
import me.spikestinger.Vonware.manager.NetworkManager;
import me.spikestinger.Vonware.manager.RotationManager;
import net.minecraft.class_1268;
import net.minecraft.class_1776;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2886;
import net.minecraft.class_7204;
import net.minecraft.class_2828.class_2830;

public class PearlPhase extends Module {
   public final Setting<Float> throwPitch = this.register(new Setting("Pitch", 85.0F, 0.0F, 90.0F));
   public final Setting<Integer> jumpDelay = this.register(new Setting("Jump Delay", 2, 0, 6));
   private static int delayTimer = 0;
   private static float realPitch;

   public PearlPhase() {
      super("PearlPhase", "Phases you into blocks", Module.Category.MOVEMENT, true, false, true);
   }

   public void onEnable() {
      if (!nullCheck()) {
         if (mc.field_1724.method_20232()) {
            delayTimer = 0;
            realPitch = -90.0F;
            mc.field_1724.method_6043();
         } else {
            delayTimer = (Integer)this.jumpDelay.getValue();
            realPitch = (Float)this.throwPitch.getValue();
         }

      }
   }

   public void onUpdate() {
      if (delayTimer < (Integer)this.jumpDelay.getValue()) {
         ++delayTimer;
      } else {
         int pearlSlot = -1;
         int i = 0;

         while(true) {
            if (i < 9) {
               class_1799 stack = mc.field_1724.method_31548().method_5438(i);
               if (!(stack.method_7909() instanceof class_1776)) {
                  ++i;
                  continue;
               }

               pearlSlot = i;
            }

            if (pearlSlot == -1 || mc.field_1724.method_7357().method_7904(class_1802.field_8634)) {
               this.disable();
               return;
            }

            float prevYaw = mc.field_1724.method_36454();
            float prevPitch = mc.field_1724.method_36455();
            float[] rotations = RotationManager.getRotationsTo(mc.field_1724.method_33571(), new class_243(Math.floor(mc.field_1724.method_23317()) + 0.5D, 0.0D, Math.floor(mc.field_1724.method_23321()) + 0.5D));
            RotationManager.setPlayerRotations(rotations[0] + 180.0F, realPitch);
            InventoryManager.setSlot(pearlSlot);
            class_2596<?> p = new class_2830(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321(), rotations[0] + 180.0F, realPitch, mc.field_1724.method_24828());
            NetworkManager.sendPacket(p);
            class_7204 o = (id) -> {
               return new class_2886(class_1268.field_5808, id);
            };
            NetworkManager.sendSequencedPacket(o);
            mc.field_1724.method_6104(class_1268.field_5808);
            InventoryManager.syncToClient();
            RotationManager.setPlayerRotations(prevYaw, prevPitch);
            this.toggle();
            break;
         }
      }

   }
}
