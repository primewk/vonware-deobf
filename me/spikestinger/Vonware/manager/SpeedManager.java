package me.spikestinger.Vonware.manager;

import java.util.HashMap;
import java.util.Iterator;
import me.spikestinger.Vonware.features.Feature;
import net.minecraft.class_1657;

public class SpeedManager extends Feature {
   public static final double LAST_JUMP_INFO_DURATION_DEFAULT = 3.0D;
   public static boolean didJumpThisTick = false;
   public static boolean isJumping = false;
   private final int distancer = 20;
   public double firstJumpSpeed = 0.0D;
   public double lastJumpSpeed = 0.0D;
   public double percentJumpSpeedChanged = 0.0D;
   public double jumpSpeedChanged = 0.0D;
   public boolean didJumpLastTick = false;
   public long jumpInfoStartTime = 0L;
   public boolean wasFirstJump = true;
   public double speedometerCurrentSpeed = 0.0D;
   public HashMap<class_1657, Double> playerSpeeds = new HashMap();

   public static void setDidJumpThisTick(boolean val) {
      didJumpThisTick = val;
   }

   public static void setIsJumping(boolean val) {
      isJumping = val;
   }

   public float lastJumpInfoTimeRemaining() {
      return (float)(System.currentTimeMillis() - this.jumpInfoStartTime) / 1000.0F;
   }

   public void updateValues() {
      double distTraveledLastTickX = mc.field_1724.method_23317() - mc.field_1724.field_6014;
      double distTraveledLastTickZ = mc.field_1724.method_23321() - mc.field_1724.field_5969;
      this.speedometerCurrentSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
      if (didJumpThisTick && (!mc.field_1724.method_24828() || isJumping)) {
         if (didJumpThisTick && !this.didJumpLastTick) {
            this.wasFirstJump = this.lastJumpSpeed == 0.0D;
            this.percentJumpSpeedChanged = this.speedometerCurrentSpeed != 0.0D ? this.speedometerCurrentSpeed / this.lastJumpSpeed - 1.0D : -1.0D;
            this.jumpSpeedChanged = this.speedometerCurrentSpeed - this.lastJumpSpeed;
            this.jumpInfoStartTime = System.currentTimeMillis();
            this.lastJumpSpeed = this.speedometerCurrentSpeed;
            this.firstJumpSpeed = this.wasFirstJump ? this.lastJumpSpeed : 0.0D;
         }

         this.didJumpLastTick = didJumpThisTick;
      } else {
         this.didJumpLastTick = false;
         this.lastJumpSpeed = 0.0D;
      }

      this.updatePlayers();
   }

   public void updatePlayers() {
      Iterator var1 = mc.field_1687.method_18456().iterator();

      while(var1.hasNext()) {
         class_1657 player = (class_1657)var1.next();
         if (mc.field_1724.method_5739(player) < 20.0F) {
            double distTraveledLastTickX = player.method_23317() - player.field_6014;
            double distTraveledLastTickZ = player.method_23321() - player.field_5969;
            double playerSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
            this.playerSpeeds.put(player, playerSpeed);
         }
      }

   }

   public double getPlayerSpeed(class_1657 player) {
      return this.playerSpeeds.get(player) == null ? 0.0D : this.turnIntoKpH((Double)this.playerSpeeds.get(player));
   }

   public double turnIntoKpH(double input) {
      return Math.sqrt(input) * 71.2729367892D;
   }

   public double getSpeedKpH() {
      double speedometerkphdouble = this.turnIntoKpH(this.speedometerCurrentSpeed);
      speedometerkphdouble = (double)Math.round(10.0D * speedometerkphdouble) / 10.0D;
      return speedometerkphdouble;
   }

   public double getSpeedMpS() {
      double speedometerMpsdouble = this.turnIntoKpH(this.speedometerCurrentSpeed) / 3.6D;
      speedometerMpsdouble = (double)Math.round(10.0D * speedometerMpsdouble) / 10.0D;
      return speedometerMpsdouble;
   }
}
