package me.spikestinger.Vonware.mixin;

import me.spikestinger.Vonware.event.impl.DeathEvent;
import me.spikestinger.Vonware.util.Util;
import net.minecraft.class_1309;
import net.minecraft.class_2940;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_1309.class})
public class MixinLivingEntity extends MixinEntity {
   @Shadow
   @Final
   private static class_2940<Float> field_6247;

   @Inject(
      method = {"onTrackedDataSet"},
      at = {@At("RETURN")}
   )
   public void onTrackedDataSet(class_2940<?> key, CallbackInfo info) {
      if (key.equals(field_6247) && (double)(Float)this.field_6011.method_12789(field_6247) <= 0.0D && Util.mc.field_1687 != null && Util.mc.field_1687.method_8608()) {
         DeathEvent deathEvent = new DeathEvent((class_1309)class_1309.class.cast(this));
         me.spikestinger.Vonware.util.traits.Util.EVENT_BUS.post(deathEvent);
      }

   }
}
