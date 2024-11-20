package me.spikestinger.Vonware.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.util.traits.Jsonable;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_1657;

public class FriendManager implements Jsonable {
   private static final Path VONWARE_PATH = FabricLoader.getInstance().getGameDir().resolve("vonware");
   private static final List<String> friends = new ArrayList();

   public static boolean isFriend(String name) {
      return friends.stream().anyMatch((friend) -> {
         return friend.equalsIgnoreCase(name);
      });
   }

   public boolean isFriend(class_1657 player) {
      return isFriend(player.method_5477().getString());
   }

   public static void addFriend(String name) {
      friends.add(name);
      Vonware.configManager.save();
   }

   public static void removeFriend(String name) {
      friends.removeIf((s) -> {
         return s.equalsIgnoreCase(name);
      });
      Vonware.configManager.save();
   }

   public List<String> getFriends() {
      return friends;
   }

   public JsonElement toJson() {
      JsonObject object = new JsonObject();
      JsonArray array = new JsonArray();
      Iterator var3 = friends.iterator();

      while(var3.hasNext()) {
         String friend = (String)var3.next();
         array.add(friend);
      }

      object.add("friends", array);
      return object;
   }

   public void fromJson(JsonElement element) {
      Iterator var2 = element.getAsJsonObject().get("friends").getAsJsonArray().iterator();

      while(var2.hasNext()) {
         JsonElement e = (JsonElement)var2.next();
         friends.add(e.getAsString());
      }

   }

   public static void refreshFriends() {
      String read = "";

      try {
         read = Files.readString(VONWARE_PATH.resolve("friends.json"));
      } catch (Throwable var3) {
         var3.printStackTrace();
      }

      Iterator var1 = JsonParser.parseString(read).getAsJsonObject().get("friends").getAsJsonArray().iterator();

      while(var1.hasNext()) {
         JsonElement e = (JsonElement)var1.next();
         if (!friends.contains(e.getAsString())) {
            friends.add(e.getAsString());
         }
      }

   }

   public String getFileName() {
      return "friends.json";
   }
}
