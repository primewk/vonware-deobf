package me.spikestinger.Vonware.features.modules.combat;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.manager.InventoryManager;
import me.spikestinger.Vonware.manager.NetworkManager;
import net.minecraft.class_1268;
import net.minecraft.class_1770;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2886;
import net.minecraft.class_7204;

public class ElytraSwap extends Module {
   private boolean inHotbar;

   public ElytraSwap() {
      super("ElytraSwap", "Swaps Chestplate and Elytra", Module.Category.COMBAT, true, false, true);
   }

   public void onEnable() {
      class_1792 chestPiece;
      if (!(mc.field_1724.method_31548().method_7372(2).method_7909() instanceof class_1770)) {
         chestPiece = class_1802.field_8833;
      } else {
         chestPiece = class_1802.field_22028;
      }

      int elytraSlot = -1;

      for(int i = 0; i <= 44; ++i) {
         assert mc.field_1724 != null;

         class_1792 item = mc.field_1724.method_31548().method_5438(i).method_7909();
         if (item == chestPiece || chestPiece == class_1802.field_22028 && item == class_1802.field_8058) {
            if (i < 9) {
               this.inHotbar = true;
            } else {
               this.inHotbar = false;
            }

            elytraSlot = i;
         }
      }

      if (this.inHotbar) {
         InventoryManager.setSlot(elytraSlot);
         class_7204 o = (id) -> {
            return new class_2886(class_1268.field_5808, id);
         };
         NetworkManager.sendSequencedPacket(o);
         InventoryManager.syncToClient();
      } else {
         class_1799 elytraStack = mc.field_1724.method_31548().method_7372(2);
         InventoryManager.pickupSlot(elytraSlot);
         boolean rt = !elytraStack.method_7960();
         InventoryManager.pickupSlot(6);
         if (rt) {
            InventoryManager.pickupSlot(elytraSlot);
         }
      }

      this.toggle();
   }
}
