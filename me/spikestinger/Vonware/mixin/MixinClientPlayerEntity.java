package me.spikestinger.Vonware.mixin;

import me.spikestinger.Vonware.event.Stage;
import me.spikestinger.Vonware.event.impl.UpdateEvent;
import me.spikestinger.Vonware.event.impl.UpdateWalkingPlayerEvent;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_1313;
import net.minecraft.class_243;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_746.class})
public abstract class MixinClientPlayerEntity {
   @Shadow
   public abstract void method_5784(class_1313 var1, class_243 var2);

   @Shadow
   protected abstract void method_3148(float var1, float var2);

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   private void tickHook(CallbackInfo ci) {
      Util.EVENT_BUS.post(new UpdateEvent());
   }

   @Inject(
      method = {"tick"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
   shift = Shift.AFTER
)}
   )
   private void tickHook2(CallbackInfo ci) {
      Util.EVENT_BUS.post(new UpdateWalkingPlayerEvent(Stage.PRE));
   }

   @Inject(
      method = {"tick"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendMovementPackets()V",
   shift = Shift.AFTER
)}
   )
   private void tickHook3(CallbackInfo ci) {
      Util.EVENT_BUS.post(new UpdateWalkingPlayerEvent(Stage.POST));
   }
}
