package me.spikestinger.Vonware.mixin;

import me.spikestinger.Vonware.Vonware;
import net.minecraft.class_317;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_317.class})
public class MixinRenderTickCounter {
   @Shadow
   public float field_1969;

   @Inject(
      method = {"beginRenderTick"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J"
)}
   )
   public void beginRenderTick(long timeMillis, CallbackInfoReturnable<Integer> cir) {
      this.field_1969 *= Vonware.TIMER;
   }
}
