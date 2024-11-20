package me.spikestinger.Vonware.manager;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import me.spikestinger.Vonware.mixin.AccessorClientWorld;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_2596;
import net.minecraft.class_2792;
import net.minecraft.class_634;
import net.minecraft.class_7202;
import net.minecraft.class_7204;

public class NetworkManager implements Util {
   private static final Set<class_2596<?>> PACKET_CACHE = new HashSet();

   public static void sendPacket(class_2596<?> p) {
      if (mc.method_1562() != null) {
         PACKET_CACHE.add(p);
         mc.method_1562().method_52787(p);
      }

   }

   public static void sendSequencedPacket(class_7204 o) {
      if (mc.field_1687 != null) {
         class_7202 updater = ((AccessorClientWorld)mc.field_1687).hookGetPendingUpdateManager().method_41937();

         try {
            int i = updater.method_41942();
            class_2596<class_2792> packet = o.predict(i);
            ((class_634)Objects.requireNonNull(mc.method_1562())).method_52787(packet);
         } catch (Throwable var5) {
            var5.printStackTrace();
            if (updater != null) {
               try {
                  updater.close();
               } catch (Throwable var4) {
                  var4.printStackTrace();
                  var5.addSuppressed(var4);
               }
            }

            throw var5;
         }

         if (updater != null) {
            updater.close();
         }
      }

   }

   public static boolean isCached(class_2596<?> p) {
      return PACKET_CACHE.contains(p);
   }
}
