package me.spikestinger.Vonware.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import me.spikestinger.Vonware.util.Util;
import net.minecraft.class_1292;
import net.minecraft.class_1294;
import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_3486;

public class InteractionManager implements Util {
   public static class_2350 getPlaceDirectionGrim(class_2338 blockPos) {
      Set<class_2350> directions = getPlaceDirectionsGrim(mc.field_1724.method_19538(), blockPos);
      return (class_2350)directions.stream().findAny().orElse(class_2350.field_11036);
   }

   public static Set<class_2350> getPlaceDirectionsGrim(class_243 eyePos, class_2338 blockPos) {
      return getPlaceDirectionsGrim(eyePos.field_1352, eyePos.field_1351, eyePos.field_1350, blockPos);
   }

   public static Set<class_2350> getPlaceDirectionsGrim(double x, double y, double z, class_2338 pos) {
      Set<class_2350> dirs = new HashSet(6);
      class_238 combined = getCombinedBox(pos);
      class_238 eyePositions = (new class_238(x, y + 0.4D, z, x, y + 1.62D, z)).method_1014(2.0E-4D);
      if (eyePositions.field_1321 <= combined.field_1321) {
         dirs.add(class_2350.field_11043);
      }

      if (eyePositions.field_1324 >= combined.field_1324) {
         dirs.add(class_2350.field_11035);
      }

      if (eyePositions.field_1320 >= combined.field_1320) {
         dirs.add(class_2350.field_11034);
      }

      if (eyePositions.field_1323 <= combined.field_1323) {
         dirs.add(class_2350.field_11039);
      }

      if (eyePositions.field_1325 >= combined.field_1325) {
         dirs.add(class_2350.field_11036);
      }

      if (eyePositions.field_1322 <= combined.field_1322) {
         dirs.add(class_2350.field_11033);
      }

      return dirs;
   }

   private static class_238 getCombinedBox(class_2338 pos) {
      class_265 shape = mc.field_1687.method_8320(pos).method_26220(mc.field_1687, pos).method_1096((double)pos.method_10263(), (double)pos.method_10264(), (double)pos.method_10260());
      class_238 combined = new class_238(pos);

      double maxZ;
      double minX;
      double minY;
      double minZ;
      double maxX;
      double maxY;
      for(Iterator var4 = shape.method_1090().iterator(); var4.hasNext(); combined = new class_238(minX, minY, minZ, maxX, maxY, maxZ)) {
         class_238 box = (class_238)var4.next();
         minX = Math.max(box.field_1323, combined.field_1323);
         minY = Math.max(box.field_1322, combined.field_1322);
         minZ = Math.max(box.field_1321, combined.field_1321);
         maxX = Math.min(box.field_1320, combined.field_1320);
         maxY = Math.min(box.field_1325, combined.field_1325);
         maxZ = Math.min(box.field_1324, combined.field_1324);
      }

      return combined;
   }

   public static float calcBlockBreakingDelta(class_2680 state, class_1922 world, class_2338 pos) {
      float f = state.method_26214(world, pos);
      if (f == -1.0F) {
         return 0.0F;
      } else {
         int i = canHarvest(state) ? 30 : 100;
         return getBlockBreakingSpeed(state) / f / (float)i;
      }
   }

   private static boolean canHarvest(class_2680 state) {
      if (state.method_29291()) {
         int tool = InventoryManager.getBestTool(state);
         return mc.field_1724.method_31548().method_5438(tool).method_7951(state);
      } else {
         return true;
      }
   }

   private static float getBlockBreakingSpeed(class_2680 block) {
      int tool = InventoryManager.getBestTool(block);
      float f = mc.field_1724.method_31548().method_5438(tool).method_7924(block);
      if (f > 1.0F) {
         class_1799 stack = mc.field_1724.method_31548().method_5438(tool);
         int i = class_1890.method_8225(class_1893.field_9131, stack);
         if (i > 0 && !stack.method_7960()) {
            f += (float)(i * i + 1);
         }
      }

      if (class_1292.method_5576(mc.field_1724)) {
         f *= 1.0F + (float)(class_1292.method_5575(mc.field_1724) + 1) * 0.2F;
      }

      if (mc.field_1724.method_6059(class_1294.field_5901)) {
         float var10000;
         switch(mc.field_1724.method_6112(class_1294.field_5901).method_5578()) {
         case 0:
            var10000 = 0.3F;
            break;
         case 1:
            var10000 = 0.09F;
            break;
         case 2:
            var10000 = 0.0027F;
            break;
         default:
            var10000 = 8.1E-4F;
         }

         f *= var10000;
      }

      if (mc.field_1724.method_5777(class_3486.field_15517) && !class_1890.method_8200(mc.field_1724)) {
         f /= 5.0F;
      }

      if (!mc.field_1724.method_24828()) {
         f /= 5.0F;
      }

      return f;
   }
}
