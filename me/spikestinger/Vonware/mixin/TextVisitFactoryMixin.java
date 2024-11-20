package me.spikestinger.Vonware.mixin;

import me.spikestinger.Vonware.features.modules.misc.NameHider;
import me.spikestinger.Vonware.util.Util;
import net.minecraft.class_5223;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({class_5223.class})
public class TextVisitFactoryMixin implements Util {
   @ModifyArg(
      method = {"visitFormatted(Ljava/lang/String;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"
)
   )
   private static String modifyText(String text) {
      return NameHider.INSTANCE.isEnabled() ? text.replace(mc.method_1548().method_1676(), "cat") : text;
   }
}
