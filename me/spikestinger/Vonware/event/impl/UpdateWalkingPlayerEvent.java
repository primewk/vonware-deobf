package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;
import me.spikestinger.Vonware.event.Stage;

public class UpdateWalkingPlayerEvent extends Event {
   private final Stage stage;

   public UpdateWalkingPlayerEvent(Stage stage) {
      this.stage = stage;
   }

   public Stage getStage() {
      return this.stage;
   }
}
