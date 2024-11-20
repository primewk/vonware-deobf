package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;
import net.minecraft.class_1657;

public class TotemPopEvent extends Event {
   private final class_1657 player;

   public TotemPopEvent(class_1657 player) {
      this.player = player;
   }

   public class_1657 getPlayer() {
      return this.player;
   }
}
