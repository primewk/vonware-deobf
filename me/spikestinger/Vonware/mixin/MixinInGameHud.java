package me.spikestinger.Vonware.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.spikestinger.Vonware.event.impl.Render2DEvent;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_310;
import net.minecraft.class_329;
import net.minecraft.class_332;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_329.class})
public class MixinInGameHud {
   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   public void render(class_332 context, float tickDelta, CallbackInfo ci) {
      if (!class_310.method_1551().field_1705.method_53531().method_53536()) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 771);
         RenderSystem.disableCull();
         GL11.glEnable(2848);
         Render2DEvent event = new Render2DEvent(context, tickDelta);
         Util.EVENT_BUS.post(event);
         RenderSystem.enableDepthTest();
         GL11.glDisable(2848);
      }
   }
}
