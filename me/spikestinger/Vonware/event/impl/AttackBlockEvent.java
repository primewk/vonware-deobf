package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;

@Cancelable
public class AttackBlockEvent extends Event {
   private final class_2338 pos;
   private final class_2680 state;
   private final class_2350 direction;
   private boolean canceled;

   public AttackBlockEvent(class_2338 pos, class_2680 state, class_2350 direction) {
      this.pos = pos;
      this.state = state;
      this.direction = direction;
   }

   public class_2338 getPos() {
      return this.pos;
   }

   public class_2680 getState() {
      return this.state;
   }

   public class_2350 getDirection() {
      return this.direction;
   }

   public boolean isCanceled() {
      return this.canceled;
   }

   public void setCanceled(boolean cancel) {
      this.canceled = cancel;
   }
}
