package me.spikestinger.Vonware.util;

import net.minecraft.class_1293;
import net.minecraft.class_1294;

public class EntityUtil implements Util {
   public static boolean isMoving() {
      return (double)mc.field_1724.field_6250 != 0.0D || (double)mc.field_1724.field_6212 != 0.0D;
   }

   public static double getDefaultMoveSpeed() {
      double baseSpeed = 0.2873D;
      class_1293 speed = mc.field_1724.method_6112(class_1294.field_5904);
      if (speed != null) {
         int amplifier = speed.method_5578();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }
}
