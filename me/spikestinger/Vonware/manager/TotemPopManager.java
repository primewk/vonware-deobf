package me.spikestinger.Vonware.manager;

import com.google.common.eventbus.Subscribe;
import java.util.HashMap;
import java.util.Map;
import me.spikestinger.Vonware.event.impl.DeathEvent;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.event.impl.TotemPopEvent;
import me.spikestinger.Vonware.features.Feature;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2596;
import net.minecraft.class_2663;

public class TotemPopManager extends Feature {
   private final Map<String, Integer> popMap = new HashMap();

   public TotemPopManager() {
      EVENT_BUS.register(this);
   }

   @Subscribe
   public void onTotemPop(PacketEvent.Receive event) {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_2596 var3 = event.getPacket();
         if (var3 instanceof class_2663) {
            class_2663 packet = (class_2663)var3;
            if (packet.method_11470() == 35) {
               class_1297 entity = packet.method_11469(mc.field_1687);
               if (entity != null) {
                  String name = entity.method_5477().getString();
                  if (entity instanceof class_1657) {
                     boolean contains = this.popMap.containsKey(name);
                     this.popMap.put(name, contains ? (Integer)this.popMap.get(name) + 1 : 1);
                     TotemPopEvent popEvent = new TotemPopEvent((class_1657)entity);
                     EVENT_BUS.post(popEvent);
                  }
               }
            }
         }

      }
   }

   public final Map<String, Integer> getPopMap() {
      return this.popMap;
   }

   @Subscribe
   public void onDeath(DeathEvent event) {
      class_1297 entity = event.getEntity();
      if (entity instanceof class_1657) {
         String name = entity.method_5477().getString();
         if (this.popMap.containsKey(name)) {
            this.popMap.remove(name, this.popMap.get(name));
         }
      }

   }
}
