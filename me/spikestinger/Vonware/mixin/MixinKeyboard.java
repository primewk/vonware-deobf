package me.spikestinger.Vonware.mixin;

import me.spikestinger.Vonware.event.impl.KeyEvent;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_309.class})
public class MixinKeyboard {
   @Inject(
      method = {"onKey"},
      at = {@At("TAIL")},
      cancellable = true
   )
   private void onKey(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
      KeyEvent event = new KeyEvent(key);
      Util.EVENT_BUS.post(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }
}
