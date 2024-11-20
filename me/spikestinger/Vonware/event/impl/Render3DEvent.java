package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;
import net.minecraft.class_4587;

public class Render3DEvent extends Event {
   private final float delta;
   private final class_4587 matrix;

   public Render3DEvent(class_4587 matrix, float delta) {
      this.matrix = matrix;
      this.delta = delta;
   }

   public class_4587 getMatrix() {
      return this.matrix;
   }

   public float getDelta() {
      return this.delta;
   }
}
