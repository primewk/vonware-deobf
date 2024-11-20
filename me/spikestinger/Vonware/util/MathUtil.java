package me.spikestinger.Vonware.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3532;

public class MathUtil implements me.spikestinger.Vonware.util.traits.Util {
   private static final Random random = new Random();

   public static int getRandom(int min, int max) {
      return min + random.nextInt(max - min + 1);
   }

   public static double getRandom(double min, double max) {
      return class_3532.method_15350(min + random.nextDouble() * max, min, max);
   }

   public static float getRandom(float min, float max) {
      return class_3532.method_15363(min + random.nextFloat() * max, min, max);
   }

   public static int clamp(int num, int min, int max) {
      return num < min ? min : Math.min(num, max);
   }

   public static float clamp(float num, float min, float max) {
      return num < min ? min : Math.min(num, max);
   }

   public static double clamp(double num, double min, double max) {
      return num < min ? min : Math.min(num, max);
   }

   public static float sin(float value) {
      return class_3532.method_15374(value);
   }

   public static float cos(float value) {
      return class_3532.method_15362(value);
   }

   public static float wrapDegrees(float value) {
      return class_3532.method_15393(value);
   }

   public static double wrapDegrees(double value) {
      return class_3532.method_15338(value);
   }

   public static class_243 roundVec(class_243 vec3d, int places) {
      return new class_243(round(vec3d.field_1352, places), round(vec3d.field_1351, places), round(vec3d.field_1350, places));
   }

   public static double square(double input) {
      return input * input;
   }

   public static double round(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = BigDecimal.valueOf(value);
         bd = bd.setScale(places, RoundingMode.FLOOR);
         return bd.doubleValue();
      }
   }

   public static float wrap(float valI) {
      float val = valI % 360.0F;
      if (val >= 180.0F) {
         val -= 360.0F;
      }

      if (val < -180.0F) {
         val += 360.0F;
      }

      return val;
   }

   public static class_243 direction(float yaw) {
      return new class_243(Math.cos(degToRad((double)(yaw + 90.0F))), 0.0D, Math.sin(degToRad((double)(yaw + 90.0F))));
   }

   public static float round(float value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = BigDecimal.valueOf((double)value);
         bd = bd.setScale(places, RoundingMode.FLOOR);
         return bd.floatValue();
      }
   }

   public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean descending) {
      LinkedList<Entry<K, V>> list = new LinkedList(map.entrySet());
      if (descending) {
         list.sort(Entry.comparingByValue(Comparator.reverseOrder()));
      } else {
         list.sort(Entry.comparingByValue());
      }

      LinkedHashMap<K, V> result = new LinkedHashMap();
      Iterator var4 = list.iterator();

      while(var4.hasNext()) {
         Entry<K, V> entry = (Entry)var4.next();
         result.put(entry.getKey(), (Comparable)entry.getValue());
      }

      return result;
   }

   public static String getTimeOfDay() {
      Calendar c = Calendar.getInstance();
      int timeOfDay = c.get(11);
      if (timeOfDay < 12) {
         return "Good Morning ";
      } else if (timeOfDay < 16) {
         return "Good Afternoon ";
      } else {
         return timeOfDay < 21 ? "Good Evening " : "Good Night ";
      }
   }

   public static double radToDeg(double rad) {
      return rad * 57.295780181884766D;
   }

   public static double degToRad(double deg) {
      return deg * 0.01745329238474369D;
   }

   public static double getIncremental(double val, double inc) {
      double one = 1.0D / inc;
      return (double)Math.round(val * one) / one;
   }

   public static double[] directionSpeed(double speed) {
      float forward = mc.field_1724.field_3913.field_3905;
      float side = mc.field_1724.field_3913.field_3907;
      float yaw = mc.field_1724.field_5982 + (mc.field_1724.method_36454() - mc.field_1724.field_5982) * mc.method_1488();
      if (forward != 0.0F) {
         if (side > 0.0F) {
            yaw += (float)(forward > 0.0F ? -45 : 45);
         } else if (side < 0.0F) {
            yaw += (float)(forward > 0.0F ? 45 : -45);
         }

         side = 0.0F;
         if (forward > 0.0F) {
            forward = 1.0F;
         } else if (forward < 0.0F) {
            forward = -1.0F;
         }
      }

      double sin = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
      double cos = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      double posX = (double)forward * speed * cos + (double)side * speed * sin;
      double posZ = (double)forward * speed * sin - (double)side * speed * cos;
      return new double[]{posX, posZ};
   }

   public static List<class_243> getBlockBlocks(class_1297 entity) {
      ArrayList<class_243> vec3ds = new ArrayList();
      class_238 bb = entity.method_5829();
      double y = entity.method_23318();
      double minX = round(bb.field_1323, 0);
      double minZ = round(bb.field_1321, 0);
      double maxX = round(bb.field_1320, 0);
      double maxZ = round(bb.field_1324, 0);
      if (minX != maxX) {
         vec3ds.add(new class_243(minX, y, minZ));
         vec3ds.add(new class_243(maxX, y, minZ));
         if (minZ != maxZ) {
            vec3ds.add(new class_243(minX, y, maxZ));
            vec3ds.add(new class_243(maxX, y, maxZ));
            return vec3ds;
         }
      } else if (minZ != maxZ) {
         vec3ds.add(new class_243(minX, y, minZ));
         vec3ds.add(new class_243(minX, y, maxZ));
         return vec3ds;
      }

      vec3ds.add(entity.method_19538());
      return vec3ds;
   }

   public static boolean areVec3dsAligned(class_243 vec3d1, class_243 vec3d2) {
      return areVec3dsAlignedRetarded(vec3d1, vec3d2);
   }

   public static boolean areVec3dsAlignedRetarded(class_243 vec3d1, class_243 vec3d2) {
      class_2338 pos1 = class_2338.method_49638(vec3d1);
      class_2338 pos2 = class_2338.method_49637(vec3d2.field_1352, vec3d1.field_1351, vec3d2.field_1350);
      return pos1.equals(pos2);
   }

   public static float[] calcAngle(class_243 from, class_243 to) {
      double difX = to.field_1352 - from.field_1352;
      double difY = (to.field_1351 - from.field_1351) * -1.0D;
      double difZ = to.field_1350 - from.field_1350;
      double dist = Math.sqrt(difX * difX + difZ * difZ);
      return new float[]{(float)class_3532.method_15338(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)class_3532.method_15338(Math.toDegrees(Math.atan2(difY, dist)))};
   }
}
