package me.spikestinger.Vonware.manager;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1831;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_2371;
import net.minecraft.class_2596;
import net.minecraft.class_2680;
import net.minecraft.class_2813;
import net.minecraft.class_2868;

public class InventoryManager implements Util {
   private static int slot;
   private static final Set<class_2596<?>> PACKET_CACHE = new HashSet();

   public static void setSlot(int barSlot) {
      if (slot != barSlot && class_1661.method_7380(barSlot)) {
         setSlotForced(barSlot);
      }

   }

   public static void syncToClient() {
      if (isDesynced()) {
         setSlotForced(mc.field_1724.method_31548().field_7545);
      }

   }

   public static boolean isDesynced() {
      return mc.field_1724.method_31548().field_7545 != slot;
   }

   public static void setSlotForced(int barSlot) {
      class_2596<?> p = new class_2868(barSlot);
      if (mc.method_1562() != null) {
         PACKET_CACHE.add(p);
         mc.method_1562().method_52787(p);
      }

   }

   private static void click(int slot, int button, class_1713 type) {
      class_1703 screenHandler = mc.field_1724.field_7512;
      class_2371<class_1735> defaultedList = screenHandler.field_7761;
      int i = defaultedList.size();
      ArrayList<class_1799> list = Lists.newArrayListWithCapacity(i);
      Iterator var7 = defaultedList.iterator();

      while(var7.hasNext()) {
         class_1735 slot1 = (class_1735)var7.next();
         list.add(slot1.method_7677().method_7972());
      }

      screenHandler.method_7593(slot, button, type, mc.field_1724);
      Int2ObjectOpenHashMap<class_1799> int2ObjectMap = new Int2ObjectOpenHashMap();

      for(int j = 0; j < i; ++j) {
         class_1799 itemStack = (class_1799)list.get(j);
         class_1799 itemStack2;
         if (!class_1799.method_7973(itemStack, itemStack2 = ((class_1735)defaultedList.get(j)).method_7677())) {
            int2ObjectMap.put(j, itemStack2.method_7972());
         }
      }

      mc.field_1724.field_3944.method_52787(new class_2813(screenHandler.field_7763, screenHandler.method_37421(), slot, button, type, screenHandler.method_34255().method_7972(), int2ObjectMap));
   }

   public static void pickupSlot(int slot) {
      click(slot, 0, class_1713.field_7790);
   }

   public static int getBestTool(class_2680 state) {
      int slot = getBestToolNoFallback(state);
      return slot != -1 ? slot : mc.field_1724.method_31548().field_7545;
   }

   public static int getBestToolNoFallback(class_2680 state) {
      int slot = -1;
      float bestTool = 0.0F;

      for(int i = 0; i < 9; ++i) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(i);
         if (!stack.method_7960() && stack.method_7909() instanceof class_1831) {
            float speed = stack.method_7924(state);
            int efficiency = class_1890.method_8225(class_1893.field_9131, stack);
            if (efficiency > 0) {
               speed += (float)(efficiency * efficiency) + 1.0F;
            }

            if (speed > bestTool) {
               bestTool = speed;
               slot = i;
            }
         }
      }

      return slot;
   }
}
