package me.spikestinger.Vonware.features.modules.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.manager.FriendManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_2583;

public class FutureFriendSync extends Module {
   public final Setting<Boolean> announce = this.register(new Setting("Announce", false));
   public final Setting<Boolean> deleteNonFriends = this.register(new Setting("DeleteMismatch", false));
   private static final String FUTURE_FOLDER_STRING;
   private static final String VONWARE_FOLDER_STRING;
   private static long futureFriendsSize;
   private static long vonwareFriendsSize;
   class_2561 moduleText;

   public FutureFriendSync() {
      super("FriendSync", "syncs friend list with future", Module.Category.CLIENT, true, false, true);
      this.moduleText = class_2561.method_43470("[FriendSync] ").method_10862(class_2583.field_24360.method_10977(class_124.field_1076).method_10982(true));
   }

   public void onEnable() {
      this.syncFriends();
   }

   private void syncFriends() {
      String futureFriends;
      try {
         futureFriends = new String(Files.readAllBytes(Paths.get(FUTURE_FOLDER_STRING + "/friends.json")));
      } catch (IOException var14) {
         throw new RuntimeException(var14);
      }

      Gson gson = new Gson();
      JsonArray futureArray = (JsonArray)gson.fromJson(futureFriends, JsonArray.class);
      List<String> futureFriendsList = new ArrayList();
      Iterator var5 = futureArray.iterator();

      while(var5.hasNext()) {
         JsonElement element = (JsonElement)var5.next();
         JsonObject futureObject = element.getAsJsonObject();
         String friendLabel = futureObject.get("friend-label").getAsString();
         if ((Boolean)this.deleteNonFriends.getValue()) {
            futureFriendsList.add(friendLabel);
         }

         if (!FriendManager.isFriend(friendLabel)) {
            FriendManager.addFriend(friendLabel);
            if ((Boolean)this.announce.getValue()) {
               class_2561 text = class_2561.method_43470("Added " + friendLabel + " as a friend.").method_10862(class_2583.field_24360.method_10977(class_124.field_1068).method_10982(false));
               class_2561 message = this.moduleText.method_27661().method_10852(text);
               mc.field_1705.method_1743().method_1812(message);
            }
         }
      }

      if ((Boolean)this.deleteNonFriends.getValue()) {
         String vonwareFriends;
         try {
            vonwareFriends = new String(Files.readAllBytes(Paths.get(VONWARE_FOLDER_STRING + "/friends.json")));
         } catch (IOException var13) {
            throw new RuntimeException(var13);
         }

         JsonObject vonwareObject = (JsonObject)gson.fromJson(vonwareFriends, JsonObject.class);
         JsonArray vonwareArray = vonwareObject.getAsJsonArray("friends");
         List<String> friendsList = new ArrayList();
         vonwareArray.forEach((jsonElement) -> {
            friendsList.add(jsonElement.getAsString());
         });
         Iterator var19 = friendsList.iterator();

         while(var19.hasNext()) {
            String name = (String)var19.next();
            if (!futureFriendsList.contains(name)) {
               FriendManager.removeFriend(name);
               if ((Boolean)this.announce.getValue()) {
                  class_2561 text = class_2561.method_43470("Deleted friend " + name).method_10862(class_2583.field_24360.method_10977(class_124.field_1061).method_10982(false));
                  class_2561 message = this.moduleText.method_27661().method_10852(text);
                  mc.field_1705.method_1743().method_1812(message);
               }
            }
         }
      }

   }

   static {
      String var10000 = System.getProperty("user.home");
      FUTURE_FOLDER_STRING = Paths.get(var10000).toString() + "/Future";
      VONWARE_FOLDER_STRING = FabricLoader.getInstance().getGameDir().resolve("vonware").toString();
   }
}
