package me.spikestinger.Vonware.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_2350;

public class DirectionUtil {
   public static Boolean isCardinal(DirectionUtil.EightWayDirections dir) {
      return dir.equals(DirectionUtil.EightWayDirections.EAST) || dir.equals(DirectionUtil.EightWayDirections.WEST) || dir.equals(DirectionUtil.EightWayDirections.SOUTH) || dir.equals(DirectionUtil.EightWayDirections.NORTH);
   }

   public static enum EightWayDirections {
      NORTH(new class_2350[]{class_2350.field_11043}),
      SOUTH(new class_2350[]{class_2350.field_11035}),
      EAST(new class_2350[]{class_2350.field_11034}),
      WEST(new class_2350[]{class_2350.field_11039}),
      NORTHEAST(new class_2350[]{class_2350.field_11043, class_2350.field_11034}),
      NORTHWEST(new class_2350[]{class_2350.field_11043, class_2350.field_11039}),
      SOUTHEAST(new class_2350[]{class_2350.field_11035, class_2350.field_11034}),
      SOUTHWEST(new class_2350[]{class_2350.field_11035, class_2350.field_11039});

      private final List<class_2350> directions;

      private EightWayDirections(class_2350... directions) {
         this.directions = Arrays.asList(directions);
      }

      public class_2338 offset(class_2338 pos) {
         class_2338 result = pos;

         class_2350 direction;
         for(Iterator var3 = this.directions.iterator(); var3.hasNext(); result = result.method_10093(direction)) {
            direction = (class_2350)var3.next();
         }

         return result;
      }

      // $FF: synthetic method
      private static DirectionUtil.EightWayDirections[] $values() {
         return new DirectionUtil.EightWayDirections[]{NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST};
      }
   }

   public static enum FourWayDirections {
      NORTH(new class_2350[]{class_2350.field_11043}),
      SOUTH(new class_2350[]{class_2350.field_11035}),
      EAST(new class_2350[]{class_2350.field_11034}),
      WEST(new class_2350[]{class_2350.field_11039});

      private final List<class_2350> directions;

      private FourWayDirections(class_2350... directions) {
         this.directions = Arrays.asList(directions);
      }

      public class_2338 offset(class_2338 pos) {
         class_2338 result = pos;

         class_2350 direction;
         for(Iterator var3 = this.directions.iterator(); var3.hasNext(); result = result.method_10093(direction)) {
            direction = (class_2350)var3.next();
         }

         return result;
      }

      // $FF: synthetic method
      private static DirectionUtil.FourWayDirections[] $values() {
         return new DirectionUtil.FourWayDirections[]{NORTH, SOUTH, EAST, WEST};
      }
   }
}
