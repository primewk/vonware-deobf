package me.spikestinger.Vonware.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.spikestinger.Vonware.event.impl.Render3DEvent;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_761;
import net.minecraft.class_765;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_761.class})
public class MixinWorldRenderer {
   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   private void render(class_4587 matrices, float tickDelta, long limitTime, boolean renderBlockOutline, class_4184 camera, class_757 gameRenderer, class_765 lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
      class_310.method_1551().method_16011().method_15396("vonware-render-3d");
      RenderSystem.clear(256, class_310.field_1703);
      Render3DEvent event = new Render3DEvent(matrices, tickDelta);
      Util.EVENT_BUS.post(event);
      class_310.method_1551().method_16011().method_15407();
   }
}
