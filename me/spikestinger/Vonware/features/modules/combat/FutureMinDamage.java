package me.spikestinger.Vonware.features.modules.combat;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import net.minecraft.class_634;

public class FutureMinDamage extends Module {
   public Setting<String> futurePrefix = this.register(new Setting("FuturePrefix", "."));
   public final Setting<Float> defaultMinDamage = this.register(new Setting("DefaultMinDamage", 7.0F, 0.0F, 20.0F));
   public final Setting<Float> secondMinDamage = this.register(new Setting("SecondMinDamage", 2.4F, 0.0F, 20.0F));

   public FutureMinDamage() {
      super("FutureMinDamage", "Sets future AutoCrystal MinDamage to given values", Module.Category.COMBAT, true, false, false);
   }

   public void onDisable() {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_634 var10000 = mc.field_1724.field_3944;
         String var10001 = (String)this.futurePrefix.getValue();
         var10000.method_45729(var10001 + "autocrystal mindamage " + this.defaultMinDamage.getValue());
      }
   }

   public void onEnable() {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_634 var10000 = mc.field_1724.field_3944;
         String var10001 = (String)this.futurePrefix.getValue();
         var10000.method_45729(var10001 + "autocrystal mindamage " + this.secondMinDamage.getValue());
      }
   }
}
