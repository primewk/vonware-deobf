package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;
import net.minecraft.class_1313;
import net.minecraft.class_243;

public class MoveEvent extends Event {
   private class_1313 type;
   private class_243 vec;

   public MoveEvent(class_1313 type, class_243 vec) {
      this.type = type;
      this.vec = vec;
   }

   public class_1313 getType() {
      return this.type;
   }

   public void setType(class_1313 type) {
      this.type = type;
   }

   public class_243 getVec() {
      return this.vec;
   }

   public void setVec(class_243 vec) {
      this.vec = vec;
   }

   public void setVec(double x, double y, double z) {
      this.vec = new class_243(x, y, z);
   }

   public void setY(double y) {
      this.vec = new class_243(this.getVec().method_10216(), y, this.getVec().method_10215());
   }

   public void setXZ(double x, double z) {
      this.vec = new class_243(x, this.getVec().method_10214(), z);
   }

   public double getX() {
      return this.getVec().method_10216();
   }

   public double getY() {
      return this.getVec().method_10214();
   }

   public double getZ() {
      return this.getVec().method_10215();
   }
}
