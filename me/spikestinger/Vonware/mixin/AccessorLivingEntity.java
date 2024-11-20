package me.spikestinger.Vonware.mixin;

import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_1309.class})
public interface AccessorLivingEntity {
   @Accessor("lastAttackedTicks")
   int getLastAttackedTicks();

   @Accessor("jumpingCooldown")
   int getLastJumpCooldown();

   @Accessor("jumpingCooldown")
   void setLastJumpCooldown(int var1);
}
