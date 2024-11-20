package org.spongepowered.asm;

import java.security.MessageDigest;

public class getHWID {
   public static String getHWID() {
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("MD5");
         String var10001 = System.getenv("PROCESSOR_LEVEL");
         byte[] bytes = messageDigest.digest((var10001 + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")).getBytes());
         StringBuilder stringBuilder = new StringBuilder();
         byte[] var3 = bytes;
         int var4 = bytes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte i = var3[var5];
            String hex = Integer.toHexString(255 & i);
            stringBuilder.append(hex.length() == 1 ? "0" : hex);
         }

         return stringBuilder.toString();
      } catch (Exception var8) {
         return "Failed to get hwid";
      }
   }
}
