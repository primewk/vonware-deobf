package me.spikestinger.Vonware.mixin;

import net.minecraft.class_1297;
import net.minecraft.class_2945;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({class_1297.class})
public class MixinEntity {
   @Final
   @Shadow
   protected class_2945 field_6011;
}
