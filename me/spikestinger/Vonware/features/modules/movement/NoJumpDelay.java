package me.spikestinger.Vonware.features.modules.movement;

import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.mixin.AccessorLivingEntity;

public final class NoJumpDelay extends Module {
   public NoJumpDelay() {
      super("NoJumpDelay", "Removes delay from jumping", Module.Category.MOVEMENT, true, false, true);
   }

   public void onUpdate() {
      assert mc.field_1724 != null;

      ((AccessorLivingEntity)mc.field_1724).setLastJumpCooldown(0);
   }
}
