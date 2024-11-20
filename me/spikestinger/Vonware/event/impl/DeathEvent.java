package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;
import net.minecraft.class_1309;

public class DeathEvent extends Event {
   private final class_1309 entity;

   public DeathEvent(class_1309 entity) {
      this.entity = entity;
   }

   public class_1309 getEntity() {
      return this.entity;
   }
}
