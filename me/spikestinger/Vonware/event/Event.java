package me.spikestinger.Vonware.event;

public class Event {
   private boolean cancelled;

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void cancel() {
      this.cancelled = true;
   }
}
