package me.spikestinger.Vonware.features.modules.misc;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import net.minecraft.class_2561;
import net.minecraft.class_310;

public class SmartDisconnect extends Module {
   public Setting<Integer> timeout = this.register(new Setting("Timeout", 3000, 0, 30000));
   private long last = Long.MAX_VALUE;

   public SmartDisconnect() {
      super("SmartDisconnect", "epic auto jqq but with a taste of passcode's ping", Module.Category.MISC, true, false, false);
   }

   public void onTick() {
      if (mc.method_1562() != null && System.currentTimeMillis() - this.last >= (long)(Integer)this.timeout.getValue()) {
         class_2561 text = class_2561.method_43470("[SmartDisconnect]: Welp, sounds like server hasn't replied for " + this.timeout.getValue() + ".");
         class_310 var10000 = mc;
         class_310.method_1551().method_1562().method_48296().method_10747(text);
      }

   }

   @Subscribe
   public void onPacketReceive(PacketEvent.Receive event) {
      this.last = System.currentTimeMillis();
   }
}
