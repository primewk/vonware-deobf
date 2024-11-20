package me.spikestinger.Vonware.mixin;

import me.spikestinger.Vonware.event.impl.ChatEvent;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_634;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_634.class})
public class MixinClientPlayNetworkHandler {
   @Inject(
      method = {"sendChatMessage"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void sendChatMessageHook(String content, CallbackInfo ci) {
      ChatEvent event = new ChatEvent(content);
      Util.EVENT_BUS.post(event);
      if (event.isCancelled()) {
         ci.cancel();
      }

   }
}
