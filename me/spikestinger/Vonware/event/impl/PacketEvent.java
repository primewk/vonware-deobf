package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;
import me.spikestinger.Vonware.manager.NetworkManager;
import net.minecraft.class_2596;

@Cancelable
public class PacketEvent extends Event {
   private final class_2596<?> packet;

   public PacketEvent(class_2596<?> packet) {
      this.packet = packet;
   }

   public class_2596<?> getPacket() {
      return this.packet;
   }

   @Cancelable
   public static class Outbound extends PacketEvent {
      private final boolean cached;

      public Outbound(class_2596<?> packet) {
         super(packet);
         this.cached = NetworkManager.isCached(packet);
      }

      public boolean isClientPacket() {
         return this.cached;
      }
   }

   public static class Send extends PacketEvent {
      public Send(class_2596<?> packet) {
         super(packet);
      }
   }

   public static class Receive extends PacketEvent {
      public Receive(class_2596<?> packet) {
         super(packet);
      }
   }
}
