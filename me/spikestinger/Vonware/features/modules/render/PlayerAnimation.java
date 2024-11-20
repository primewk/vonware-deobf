package me.spikestinger.Vonware.features.modules.render;

import java.util.Iterator;
import me.spikestinger.Vonware.event.impl.Render3DEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import net.minecraft.class_1657;

public class PlayerAnimation extends Module {
   public Setting<Boolean> shit = this.register(new Setting("Crouch", true));
   public Setting<Boolean> stiff = this.register(new Setting("Still", true));

   public PlayerAnimation() {
      super("PlayerTweaks", "Stiffy people", Module.Category.RENDER, true, false, false);
   }

   public void onUpdate() {
      Iterator var1 = mc.field_1687.method_18456().iterator();

      while(var1.hasNext()) {
         class_1657 player = (class_1657)var1.next();
         if ((Boolean)this.shit.getValue() && player != mc.field_1724) {
            player.method_5660(true);
         }
      }

   }

   public void onDisable() {
      Iterator var1 = mc.field_1687.method_18456().iterator();

      while(var1.hasNext()) {
         class_1657 player = (class_1657)var1.next();
         if ((Boolean)this.shit.getValue() && player != mc.field_1724) {
            player.method_5660(true);
         }
      }

   }

   public void onRender3D(Render3DEvent e) {
      Iterator var2 = mc.field_1687.method_18456().iterator();

      while(var2.hasNext()) {
         class_1657 player = (class_1657)var2.next();
         if ((Boolean)this.stiff.getValue() && player != mc.field_1724) {
         }
      }

   }
}
