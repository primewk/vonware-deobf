package me.spikestinger.Vonware.util;

import java.util.Iterator;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1292;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1303;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1683;
import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_259;
import net.minecraft.class_2680;
import net.minecraft.class_2846;
import net.minecraft.class_2879;
import net.minecraft.class_3486;
import net.minecraft.class_3965;
import net.minecraft.class_2338.class_2339;
import net.minecraft.class_2846.class_2847;

public class InteractionUtil implements me.spikestinger.Vonware.util.traits.Util {
   public static boolean canBreak(class_2338 blockPos, class_2680 state) {
      if (!mc.field_1724.method_7337() && state.method_26214(mc.field_1687, blockPos) < 0.0F) {
         return false;
      } else {
         return state.method_26218(mc.field_1687, blockPos) != class_259.method_1073();
      }
   }

   public static boolean isPlaceable(class_2338 pos, boolean entityCheck) {
      if (!mc.field_1687.method_8320(pos).method_45474()) {
         return false;
      } else {
         Iterator var2 = mc.field_1687.method_8390(class_1297.class, new class_238(pos), (ex) -> {
            return !(ex instanceof class_1683) && !(ex instanceof class_1542) && !(ex instanceof class_1303);
         }).iterator();
         if (var2.hasNext()) {
            class_1297 e = (class_1297)var2.next();
            if (e instanceof class_1657) {
               return false;
            } else {
               return !entityCheck;
            }
         } else {
            return true;
         }
      }
   }

   public static boolean breakBlock(class_2338 pos) {
      if (!canBreak(pos, mc.field_1687.method_8320(pos))) {
         return false;
      } else {
         class_2338 bp = pos instanceof class_2339 ? new class_2338(pos) : pos;
         mc.method_1562().method_52787(new class_2846(class_2847.field_12968, bp, class_2350.field_11036));
         mc.field_1724.method_6104(class_1268.field_5808);
         mc.method_1562().method_52787(new class_2846(class_2847.field_12973, bp, class_2350.field_11036));
         mc.method_1562().method_52787(new class_2879(class_1268.field_5808));
         return true;
      }
   }

   public static void useItem(class_2338 pos) {
      useItem(pos, class_1268.field_5808);
   }

   public static void useItem(class_2338 pos, class_1268 hand) {
      if (mc.field_1687 != null && mc.field_1724 != null && mc.field_1761 != null) {
         class_2350 direction = mc.field_1765 != null ? ((class_3965)mc.field_1765).method_17780() : class_2350.field_11033;
         class_1269 result = mc.field_1761.method_2896(mc.field_1724, hand, new class_3965(class_243.method_24953(pos), direction, pos, false));
         if (result.method_23666()) {
            mc.field_1724.field_3944.method_52787(new class_2879(hand));
         }

      }
   }

   public static boolean place(class_2338 pos, boolean airPlace) {
      return place(pos, airPlace, class_1268.field_5808);
   }

   public static boolean place(class_2338 pos, boolean airPlace, class_1268 hand) {
      if (mc.field_1687 != null && mc.field_1724 != null && mc.field_1761 != null) {
         if (!isPlaceable(pos, false)) {
            return false;
         } else {
            class_2350 direction = calcSide(pos);
            if (direction == null) {
               if (!airPlace) {
                  return false;
               }

               direction = mc.field_1765 != null ? ((class_3965)mc.field_1765).method_17780() : class_2350.field_11033;
            }

            class_2338 bp = airPlace ? pos : pos.method_10093(direction);
            class_1269 result = mc.field_1761.method_2896(mc.field_1724, hand, new class_3965(airPlace ? class_243.method_24953(pos) : class_243.method_24953(pos).method_43206(direction.method_10153(), 0.5D), airPlace ? direction : direction.method_10153(), bp, false));
            if (result.method_23666()) {
               mc.field_1724.field_3944.method_52787(new class_2879(hand));
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static class_2350 calcSide(class_2338 pos) {
      class_2350[] var1 = class_2350.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         class_2350 d = var1[var3];
         if (!mc.field_1687.method_8320(pos.method_10081(d.method_10163())).method_45474()) {
            return d;
         }
      }

      return null;
   }

   public static double getBlockBreakingSpeed(int slot, class_2338 pos) {
      return getBlockBreakingSpeed(slot, mc.field_1687.method_8320(pos));
   }

   public static double getBlockBreakingSpeed(int slot, class_2680 block) {
      double speed = (double)((class_1799)mc.field_1724.method_31548().field_7547.get(slot)).method_7924(block);
      if (speed > 1.0D) {
         class_1799 tool = mc.field_1724.method_31548().method_5438(slot);
         int efficiency = class_1890.method_8225(class_1893.field_9131, tool);
         if (efficiency > 0 && !tool.method_7960()) {
            speed += (double)(efficiency * efficiency + 1);
         }
      }

      if (class_1292.method_5576(mc.field_1724)) {
         speed *= (double)(1.0F + (float)(class_1292.method_5575(mc.field_1724) + 1) * 0.2F);
      }

      float hardness;
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

         hardness = var10000;
         speed *= (double)hardness;
      }

      if (mc.field_1724.method_5777(class_3486.field_15517) && !class_1890.method_8200(mc.field_1724)) {
         speed /= 5.0D;
      }

      if (!mc.field_1724.method_24828()) {
         speed /= 5.0D;
      }

      hardness = block.method_26214((class_1922)null, (class_2338)null);
      if (hardness == -1.0F) {
         return 0.0D;
      } else {
         speed /= (double)(hardness / (float)(block.method_29291() && !((class_1799)mc.field_1724.method_31548().field_7547.get(slot)).method_7951(block) ? 100 : 30));
         float ticks = (float)(Math.floor(1.0D / speed) + 1.0D);
         return (double)((long)(ticks / 20.0F * 1000.0F));
      }
   }

   public static class_2350 right(class_2350 direction) {
      class_2350 var10000;
      switch(direction) {
      case field_11034:
         var10000 = class_2350.field_11035;
         break;
      case field_11035:
         var10000 = class_2350.field_11039;
         break;
      case field_11039:
         var10000 = class_2350.field_11043;
         break;
      case field_11043:
         var10000 = class_2350.field_11034;
         break;
      default:
         throw new IllegalStateException("Unexpected value: " + direction);
      }

      return var10000;
   }

   public static class_2350 left(class_2350 direction) {
      class_2350 var10000;
      switch(direction) {
      case field_11034:
         var10000 = class_2350.field_11043;
         break;
      case field_11035:
         var10000 = class_2350.field_11034;
         break;
      case field_11039:
         var10000 = class_2350.field_11035;
         break;
      case field_11043:
         var10000 = class_2350.field_11039;
         break;
      default:
         throw new IllegalStateException("Unexpected value: " + direction);
      }

      return var10000;
   }
}
