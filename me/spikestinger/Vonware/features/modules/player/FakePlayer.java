package me.spikestinger.Vonware.features.modules.player;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.event.impl.DisconnectEvent;
import me.spikestinger.Vonware.event.impl.PushEntityEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.util.FakePlayerEntity;

public class FakePlayer extends Module {
   private FakePlayerEntity fakePlayer;

   public FakePlayer() {
      super("FakePlayer", "", Module.Category.PLAYER, true, false, false);
   }

   public void onEnable() {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         this.fakePlayer = new FakePlayerEntity(mc.field_1724, "FakePlayer");
         this.fakePlayer.spawnPlayer();
      }

   }

   public void onDisable() {
      if (this.fakePlayer != null) {
         this.fakePlayer.despawnPlayer();
         this.fakePlayer = null;
      }

   }

   @Subscribe
   public void onDisconnect(DisconnectEvent event) {
      this.fakePlayer = null;
      this.disable();
   }

   @Subscribe
   public void onPushEntity(PushEntityEvent event) {
      if (event.getPushed().equals(mc.field_1724) && event.getPusher().equals(this.fakePlayer)) {
      }

   }
}
