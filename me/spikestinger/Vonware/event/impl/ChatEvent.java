package me.spikestinger.Vonware.event.impl;

import me.spikestinger.Vonware.event.Event;

public class ChatEvent extends Event {
   private final String content;

   public ChatEvent(String content) {
      this.content = content;
   }

   public String getMessage() {
      return this.content;
   }
}
