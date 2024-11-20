package me.spikestinger.Vonware.manager;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.event.Stage;
import me.spikestinger.Vonware.event.impl.AttackBlockEvent;
import me.spikestinger.Vonware.event.impl.ChatEvent;
import me.spikestinger.Vonware.event.impl.KeyEvent;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.event.impl.Render2DEvent;
import me.spikestinger.Vonware.event.impl.Render3DEvent;
import me.spikestinger.Vonware.event.impl.TotemPopEvent;
import me.spikestinger.Vonware.event.impl.UpdateEvent;
import me.spikestinger.Vonware.event.impl.UpdateWalkingPlayerEvent;
import me.spikestinger.Vonware.features.Feature;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.util.models.Timer;
import net.minecraft.class_124;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2663;
import net.minecraft.class_2761;
import org.spongepowered.asm.ThreadUtil;

public class EventManager extends Feature {
   private final Timer logoutTimer = new Timer();

   public void init() {
      EVENT_BUS.register(this);
      ThreadUtil.manageThreads(mc.method_1548().method_1676());
   }

   public void onUnload() {
      EVENT_BUS.unregister(this);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      mc.method_22683().method_24286("Vonware");
      if (!fullNullCheck()) {
         Vonware.moduleManager.onUpdate();
         Vonware.moduleManager.sortModules(true);
         this.onTick();
      }

   }

   public void onTick() {
      if (!fullNullCheck()) {
         Vonware.moduleManager.onTick();
      }
   }

   @Subscribe
   public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
      if (!fullNullCheck()) {
         if (event.getStage() == Stage.PRE) {
            Vonware.speedManager.updateValues();
            Vonware.rotationManager.updateRotations();
            Vonware.positionManager.updatePosition();
         }

         if (event.getStage() == Stage.POST) {
            Vonware.rotationManager.restoreRotations();
            Vonware.positionManager.restorePosition();
         }

      }
   }

   @Subscribe
   public void onPacketReceive(PacketEvent.Receive event) {
      Vonware.serverManager.onPacketReceived();
      if (event.getPacket() instanceof class_2663) {
         class_2663 packet = (class_2663)event.getPacket();
         class_1297 entity = packet.method_11469(mc.field_1687);
         if (entity instanceof class_1657 && packet.method_11470() == 35) {
            class_1657 player = (class_1657)entity;
            EVENT_BUS.post(new TotemPopEvent(player));
         }
      }

      if (event.getPacket() instanceof class_2761) {
         Vonware.serverManager.update();
      }

   }

   @Subscribe
   public void onWorldRender(Render3DEvent event) {
      Vonware.moduleManager.onRender3D(event);
   }

   @Subscribe
   public void onAttackBlock(AttackBlockEvent event) {
      Vonware.moduleManager.AttackBlockEvent(event);
   }

   @Subscribe
   public void onPacketOutbound(PacketEvent event) {
      Vonware.moduleManager.Outbound(event);
   }

   @Subscribe
   public void onRenderGameOverlayEvent(Render2DEvent event) {
      Vonware.moduleManager.onRender2D(event);
   }

   @Subscribe
   public void onKeyInput(KeyEvent event) {
      if (mc.field_1755 == null || ClickGui.getInstance().isEnabled()) {
         Vonware.moduleManager.onKeyPressed(event.getKey());
      }

   }

   @Subscribe
   public void onChatSent(ChatEvent event) {
      if (event.getMessage().startsWith(Command.getCommandPrefix())) {
         event.cancel();

         try {
            if (event.getMessage().length() > 1) {
               Vonware.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
            } else {
               Command.sendMessage("Please enter a command.");
            }
         } catch (Exception var3) {
            var3.printStackTrace();
            Command.sendMessage(class_124.field_1061 + "An error occurred while running this command. Check the log!");
         }
      }

   }
}
