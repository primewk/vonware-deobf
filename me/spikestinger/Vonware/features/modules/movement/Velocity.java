package me.spikestinger.Vonware.features.modules.movement;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.features.modules.Module;
import net.minecraft.class_2664;
import net.minecraft.class_2743;

public class Velocity extends Module {
   public Velocity() {
      super("Velocity", "", Module.Category.MOVEMENT, true, false, false);
   }

   @Subscribe
   private void onPacketReceive(PacketEvent.Receive event) {
      if (event.getPacket() instanceof class_2743 || event.getPacket() instanceof class_2664) {
         event.cancel();
      }

   }
}
