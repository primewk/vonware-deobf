package me.spikestinger.Vonware.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_2535;
import net.minecraft.class_2596;
import net.minecraft.class_2598;
import net.minecraft.class_7648;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_2535.class})
public class MixinClientConnection {
   @Shadow
   private Channel field_11651;
   @Shadow
   @Final
   private class_2598 field_11643;

   @Inject(
      method = {"channelRead0"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void channelRead0(ChannelHandlerContext chc, class_2596<?> packet, CallbackInfo ci) {
      if (this.field_11651.isOpen() && packet != null) {
         try {
            PacketEvent.Receive event = new PacketEvent.Receive(packet);
            Util.EVENT_BUS.post(event);
            if (event.isCancelled()) {
               ci.cancel();
            }
         } catch (Exception var5) {
         }
      }

   }

   @Inject(
      method = {"sendImmediately"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void hookSendImmediately(class_2596<?> packet, @Nullable class_7648 callbacks, boolean flush, CallbackInfo ci) {
      PacketEvent.Outbound packetOutboundEvent = new PacketEvent.Outbound(packet);
      Util.EVENT_BUS.post(packetOutboundEvent);
      if (packetOutboundEvent.isCancelled()) {
         ci.cancel();
      }

   }
}
