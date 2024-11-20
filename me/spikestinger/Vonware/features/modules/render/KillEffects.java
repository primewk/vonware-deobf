package me.spikestinger.Vonware.features.modules.render;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.event.impl.DeathEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1538;
import net.minecraft.class_1657;
import net.minecraft.class_3417;

public class KillEffects extends Module {
   public final Setting<Boolean> sound = this.register(new Setting("Sound", true));

   public KillEffects() {
      super("KillEffects", "Effects when you kill.", Module.Category.RENDER, true, false, false);
   }

   @Subscribe
   public void onDeath(DeathEvent event) {
      class_1309 var3 = event.getEntity();
      if (var3 instanceof class_1657) {
         class_1657 player = (class_1657)var3;
         int i = 0;
         if (i == 0) {
            class_1538 bolt = new class_1538(class_1299.field_6112, mc.field_1687);
            bolt.method_5641(player.method_23317(), player.method_23318(), player.method_23321(), player.method_36454(), player.method_36455());
            mc.field_1687.method_53875(bolt);
            int var6 = i + 1;
         }

         if ((Boolean)this.sound.getValue()) {
            mc.field_1724.method_5783(class_3417.field_14865, 0.5F, 1.0F);
         }
      }

   }
}
