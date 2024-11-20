package me.spikestinger.Vonware.util.traits;

import com.google.gson.JsonElement;

public interface Jsonable {
   JsonElement toJson();

   void fromJson(JsonElement var1);

   default String getFileName() {
      return "";
   }
}
