package me.spikestinger.Vonware.features.modules.combat;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.util.models.Timer;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1511;
import net.minecraft.class_2596;
import net.minecraft.class_2824;
import net.minecraft.class_2824.class_5907;
import net.minecraft.class_2828.class_2829;

public class Criticals extends Module {
   private final Timer timer = new Timer();

   public Criticals() {
      super("Criticals", "", Module.Category.COMBAT, true, false, false);
   }

   @Subscribe
   private void onPacketSend(PacketEvent.Send event) {
      class_2596 var3 = event.getPacket();
      if (var3 instanceof class_2824) {
         class_2824 packet = (class_2824)var3;
         if (packet.field_12871.method_34211() == class_5907.field_29172) {
            class_1297 entity = mc.field_1687.method_8469(packet.field_12870);
            if (entity == null || entity instanceof class_1511 || !mc.field_1724.method_24828() || !(entity instanceof class_1309) || !this.timer.passedMs(0L)) {
               return;
            }

            mc.field_1724.field_3944.method_52787(new class_2829(mc.field_1724.method_23317(), mc.field_1724.method_23318() + 0.10000000149011612D, mc.field_1724.method_23321(), false));
            mc.field_1724.field_3944.method_52787(new class_2829(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321(), false));
            mc.field_1724.method_7277(entity);
            this.timer.reset();
         }
      }

   }

   public String getDisplayInfo() {
      return "Packet";
   }
}
