package me.spikestinger.Vonware.features.modules.misc;

import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.manager.FriendManager;
import net.minecraft.class_124;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import org.lwjgl.glfw.GLFW;

public class MiddleClick extends Module {
   private boolean pressed;
   public final Setting<Boolean> friend = this.register(new Setting("Friend", true));

   public MiddleClick() {
      super("MiddleClick", "", Module.Category.MISC, true, false, false);
   }

   public void onTick() {
      if ((Boolean)this.friend.getValue()) {
         if (GLFW.glfwGetMouseButton(mc.method_22683().method_4490(), 2) == 1) {
            if (!this.pressed) {
               class_1297 targetedEntity = mc.field_1692;
               if (!(targetedEntity instanceof class_1657)) {
                  return;
               }

               String name = ((class_1657)targetedEntity).method_7334().getName();
               FriendManager var10000 = Vonware.friendManager;
               if (FriendManager.isFriend(name)) {
                  var10000 = Vonware.friendManager;
                  FriendManager.removeFriend(name);
                  Command.sendMessage(class_124.field_1061 + name + class_124.field_1061 + " has been unfriended.");
               } else {
                  var10000 = Vonware.friendManager;
                  FriendManager.addFriend(name);
                  Command.sendMessage(class_124.field_1075 + name + class_124.field_1075 + " has been friended.");
               }

               this.pressed = true;
            }
         } else {
            this.pressed = false;
         }
      }

   }
}
