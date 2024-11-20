package me.spikestinger.Vonware.features.modules.client;

import com.google.common.eventbus.Subscribe;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.event.impl.ClientEvent;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.gui.VonwareGui;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import net.minecraft.class_124;

public class ClickGui extends Module {
   private static ClickGui INSTANCE = new ClickGui();
   public Setting<String> prefix = this.register(new Setting("Prefix", "."));
   public Setting<Integer> red = this.register(new Setting("Red", 83, 0, 255));
   public Setting<Integer> green = this.register(new Setting("Green", 155, 0, 255));
   public Setting<Integer> blue = this.register(new Setting("Blue", 255, 0, 255));
   public Setting<Integer> hoverAlpha = this.register(new Setting("Alpha", 180, 0, 255));
   public Setting<Integer> alpha = this.register(new Setting("HoverAlpha", 240, 0, 255));
   public Setting<Boolean> rainbow = this.register(new Setting("Rainbow", false));
   public Setting<ClickGui.rainbowMode> rainbowModeHud;
   public Setting<ClickGui.rainbowModeArray> rainbowModeA;
   public Setting<Integer> rainbowHue;
   public Setting<Float> rainbowBrightness;
   public Setting<Float> rainbowSaturation;
   private VonwareGui click;

   public ClickGui() {
      super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
      this.rainbowModeHud = this.register(new Setting("HRainbowMode", ClickGui.rainbowMode.Static, (v) -> {
         return (Boolean)this.rainbow.getValue();
      }));
      this.rainbowModeA = this.register(new Setting("ARainbowMode", ClickGui.rainbowModeArray.Static, (v) -> {
         return (Boolean)this.rainbow.getValue();
      }));
      this.rainbowHue = this.register(new Setting("Delay", 240, 0, 600, (v) -> {
         return (Boolean)this.rainbow.getValue();
      }));
      this.rainbowBrightness = this.register(new Setting("Brightness ", 150.0F, 1.0F, 255.0F, (v) -> {
         return (Boolean)this.rainbow.getValue();
      }));
      this.rainbowSaturation = this.register(new Setting("Saturation", 150.0F, 1.0F, 255.0F, (v) -> {
         return (Boolean)this.rainbow.getValue();
      }));
      this.setBind(257);
      this.setInstance();
   }

   public static ClickGui getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new ClickGui();
      }

      return INSTANCE;
   }

   private void setInstance() {
      INSTANCE = this;
   }

   @Subscribe
   public void onSettingChange(ClientEvent event) {
      if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
         if (event.getSetting().equals(this.prefix)) {
            Vonware.commandManager.setPrefix((String)this.prefix.getPlannedValue());
            class_124 var10000 = class_124.field_1063;
            Command.sendMessage("Prefix set to " + var10000 + Vonware.commandManager.getPrefix());
         }

         Vonware.colorManager.setColor((Integer)this.red.getPlannedValue(), (Integer)this.green.getPlannedValue(), (Integer)this.blue.getPlannedValue(), (Integer)this.hoverAlpha.getPlannedValue());
      }

   }

   public void onEnable() {
      if (mc.field_1687 != null) {
         mc.method_1507(new VonwareGui());
      }

   }

   public void onLoad() {
      Vonware.colorManager.setColor((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue(), (Integer)this.hoverAlpha.getValue());
      Vonware.commandManager.setPrefix((String)this.prefix.getValue());
   }

   public void onTick() {
      if (!(mc.field_1755 instanceof VonwareGui)) {
         this.disable();
      }

   }

   public static enum rainbowMode {
      Static,
      Sideway;

      // $FF: synthetic method
      private static ClickGui.rainbowMode[] $values() {
         return new ClickGui.rainbowMode[]{Static, Sideway};
      }
   }

   public static enum rainbowModeArray {
      Static,
      Up;

      // $FF: synthetic method
      private static ClickGui.rainbowModeArray[] $values() {
         return new ClickGui.rainbowModeArray[]{Static, Up};
      }
   }
}
