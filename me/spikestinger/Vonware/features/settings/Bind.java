package me.spikestinger.Vonware.features.settings;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_3675;
import org.lwjgl.glfw.GLFW;

public class Bind implements Util {
   private int key;

   public Bind(int key) {
      this.key = key;
   }

   public static Bind none() {
      return new Bind(-1);
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int key) {
      this.key = key;
   }

   public boolean isEmpty() {
      return this.key < 0;
   }

   public String toString() {
      return this.isEmpty() ? "None" : (this.key < 0 ? "None" : this.capitalise(class_3675.method_15985(this.key, 0).method_1441()));
   }

   public boolean isDown() {
      return !this.isEmpty() && GLFW.glfwGetKey(mc.method_22683().method_4490(), this.getKey()) == 1;
   }

   private String capitalise(String str) {
      if (str.isEmpty()) {
         return "";
      } else {
         char var10000 = Character.toUpperCase(str.charAt(0));
         return var10000 + (str.length() != 1 ? str.substring(1).toLowerCase() : "");
      }
   }

   public static class BindConverter extends Converter<Bind, JsonElement> {
      public JsonElement doForward(Bind bind) {
         return new JsonPrimitive(bind.toString());
      }

      public Bind doBackward(JsonElement jsonElement) {
         String s = jsonElement.getAsString();
         if (s.equalsIgnoreCase("None")) {
            return Bind.none();
         } else {
            int key = -1;

            try {
               key = class_3675.method_15981(s.toUpperCase()).method_1444();
            } catch (Exception var5) {
            }

            return key == 0 ? Bind.none() : new Bind(key);
         }
      }
   }
}
