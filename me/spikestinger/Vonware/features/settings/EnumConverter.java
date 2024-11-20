package me.spikestinger.Vonware.features.settings;

import com.google.common.base.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class EnumConverter extends Converter<Enum, JsonElement> {
   private final Class<? extends Enum> clazz;

   public EnumConverter(Class<? extends Enum> clazz) {
      this.clazz = clazz;
   }

   public static int currentEnum(Enum clazz) {
      for(int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ++i) {
         Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
         if (e.name().equalsIgnoreCase(clazz.name())) {
            return i;
         }
      }

      return -1;
   }

   public static Enum increaseEnum(Enum clazz) {
      int index = currentEnum(clazz);

      for(int i = 0; i < ((Enum[])clazz.getClass().getEnumConstants()).length; ++i) {
         Enum e = ((Enum[])clazz.getClass().getEnumConstants())[i];
         if (i == index + 1) {
            return e;
         }
      }

      return ((Enum[])clazz.getClass().getEnumConstants())[0];
   }

   public static String getProperName(Enum clazz) {
      char var10000 = Character.toUpperCase(clazz.name().charAt(0));
      return var10000 + clazz.name().toLowerCase().substring(1);
   }

   public JsonElement doForward(Enum anEnum) {
      return new JsonPrimitive(anEnum.toString());
   }

   public Enum doBackward(JsonElement jsonElement) {
      try {
         return Enum.valueOf(this.clazz, jsonElement.getAsString());
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }
}
