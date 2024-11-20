package me.spikestinger.Vonware.features.commands.impl;

import java.util.Iterator;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.manager.FriendManager;
import net.minecraft.class_124;

public class FriendCommand extends Command {
   public FriendCommand() {
      super("friend", new String[]{"<add/del/name/clear>", "<name>"});
   }

   public void execute(String[] commands) {
      if (commands.length != 1) {
         if (commands.length == 2) {
            if (commands[0].equals("reset")) {
               Vonware.friendManager.getFriends().clear();
               sendMessage("Friends got reset.");
            } else {
               String var9 = commands[0];
               FriendManager var10001 = Vonware.friendManager;
               sendMessage(var9 + (FriendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            }
         } else {
            if (commands.length >= 2) {
               String var7 = commands[0];
               byte var8 = -1;
               switch(var7.hashCode()) {
               case -934610812:
                  if (var7.equals("remove")) {
                     var8 = 2;
                  }
                  break;
               case 96417:
                  if (var7.equals("add")) {
                     var8 = 0;
                  }
                  break;
               case 99339:
                  if (var7.equals("del")) {
                     var8 = 1;
                  }
               }

               FriendManager var10000;
               switch(var8) {
               case 0:
                  var10000 = Vonware.friendManager;
                  FriendManager.addFriend(commands[1]);
                  sendMessage(class_124.field_1060 + commands[1] + " has been friended");
                  return;
               case 1:
               case 2:
                  var10000 = Vonware.friendManager;
                  FriendManager.removeFriend(commands[1]);
                  sendMessage(class_124.field_1061 + commands[1] + " has been unfriended");
                  return;
               default:
                  sendMessage("Unknown Command, try friend add/del (name)");
               }
            }

         }
      } else {
         if (Vonware.friendManager.getFriends().isEmpty()) {
            sendMessage("Friend list empty D:.");
         } else {
            StringBuilder f = new StringBuilder("Friends: ");
            Iterator var3 = Vonware.friendManager.getFriends().iterator();

            while(var3.hasNext()) {
               String friend = (String)var3.next();

               try {
                  f.append(friend).append(", ");
               } catch (Exception var6) {
               }
            }

            sendMessage(f.toString());
         }

      }
   }
}
