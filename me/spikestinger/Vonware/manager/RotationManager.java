package me.spikestinger.Vonware.manager;

import java.util.ArrayList;
import java.util.List;
import me.spikestinger.Vonware.util.MathUtil;
import me.spikestinger.Vonware.util.Rotation;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_2828.class_2830;
import net.minecraft.class_2828.class_2831;

public class RotationManager implements Util {
   private float yaw;
   private float pitch;
   private Rotation rotation;
   private static final List<Rotation> requests = new ArrayList();

   public void updateRotations() {
      this.yaw = mc.field_1724.method_36454();
      this.pitch = mc.field_1724.method_36455();
   }

   public void restoreRotations() {
      mc.field_1724.method_36456(this.yaw);
      mc.field_1724.field_6241 = this.yaw;
      mc.field_1724.method_36457(this.pitch);
   }

   public static void setPlayerRotations(float yaw, float pitch) {
      mc.field_1724.method_36456(yaw);
      mc.field_1724.field_6241 = yaw;
      mc.field_1724.method_36457(pitch);
   }

   public void setPlayerYaw(float yaw) {
      mc.field_1724.method_36456(yaw);
      mc.field_1724.field_6241 = yaw;
   }

   public void lookAtPos(class_2338 pos) {
      float[] angle = MathUtil.calcAngle(mc.field_1724.method_33571(), new class_243((double)((float)pos.method_10263() + 0.5F), (double)((float)pos.method_10264() + 0.5F), (double)((float)pos.method_10260() + 0.5F)));
      setPlayerRotations(angle[0], angle[1]);
   }

   public void lookAtVec3d(class_243 vec3d) {
      float[] angle = MathUtil.calcAngle(mc.field_1724.method_33571(), new class_243(vec3d.field_1352, vec3d.field_1351, vec3d.field_1350));
      setPlayerRotations(angle[0], angle[1]);
   }

   public static float[] getRotationsTo(class_243 src, class_243 dest) {
      float yaw = (float)(Math.toDegrees(Math.atan2(dest.method_1020(src).field_1350, dest.method_1020(src).field_1352)) - 90.0D);
      float pitch = (float)Math.toDegrees(-Math.atan2(dest.method_1020(src).field_1351, Math.hypot(dest.method_1020(src).field_1352, dest.method_1020(src).field_1350)));
      return new float[]{class_3532.method_15393(yaw), class_3532.method_15393(pitch)};
   }

   public void lookAtVec3d(double x, double y, double z) {
      class_243 vec3d = new class_243(x, y, z);
      this.lookAtVec3d(vec3d);
   }

   public void lookAtEntity(class_1297 entity) {
      float[] angle = MathUtil.calcAngle(mc.field_1724.method_33571(), entity.method_33571());
      setPlayerRotations(angle[0], angle[1]);
   }

   public void setPlayerPitch(float pitch) {
      mc.field_1724.method_36457(pitch);
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public static void setRotationSilentSync(boolean grim) {
      float yaw = mc.field_1724.method_36454();
      float pitch = mc.field_1724.method_36455();
      if (grim) {
         setRotation(new Rotation(Integer.MAX_VALUE, yaw, pitch, true));
         NetworkManager.sendPacket(new class_2830(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321(), yaw, pitch, mc.field_1724.method_24828()));
      } else {
         NetworkManager.sendPacket(new class_2831(yaw, pitch, mc.field_1724.method_24828()));
      }

   }

   public static void setRotation(Rotation rotation) {
      if (rotation.getPriority() == Integer.MAX_VALUE) {
         rotation = rotation;
      }

      Rotation request = (Rotation)requests.stream().filter((r) -> {
         return rotation.getPriority() == r.getPriority();
      }).findFirst().orElse((Object)null);
      if (request == null) {
         requests.add(rotation);
      } else {
         request.setYaw(rotation.getYaw());
         request.setPitch(rotation.getPitch());
      }

   }

   public static void setRotationSilent(float yaw, float pitch, boolean grim) {
      if (grim) {
         setRotation(new Rotation(Integer.MAX_VALUE, yaw, pitch, true));
         NetworkManager.sendPacket(new class_2830(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321(), yaw, pitch, mc.field_1724.method_24828()));
      } else {
         NetworkManager.sendPacket(new class_2831(yaw, pitch, mc.field_1724.method_24828()));
      }

   }
}
