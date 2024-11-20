package me.spikestinger.Vonware.mixin;

import me.spikestinger.Vonware.event.impl.AttackBlockEvent;
import me.spikestinger.Vonware.util.Util;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_636.class})
public class MixinClientPlayerInteractionManager {
   @Inject(
      method = {"attackBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void hookAttackBlock(class_2338 pos, class_2350 direction, CallbackInfoReturnable<Boolean> cir) {
      class_2680 state = Util.mc.field_1687.method_8320(pos);
      AttackBlockEvent attackBlockEvent = new AttackBlockEvent(pos, state, direction);
      me.spikestinger.Vonware.util.traits.Util.EVENT_BUS.post(attackBlockEvent);
      if (attackBlockEvent.isCanceled()) {
         cir.cancel();
      }

   }
}
