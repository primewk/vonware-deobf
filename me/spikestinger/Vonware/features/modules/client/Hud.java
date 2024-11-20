package me.spikestinger.Vonware.features.modules.client;

import java.awt.Color;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.event.impl.Render2DEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.modules.misc.NameHider;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.ColorUtil;
import me.spikestinger.Vonware.util.MathUtil;
import net.minecraft.class_332;

public class Hud extends Module {
   private int color;
   public Setting<Boolean> watermark;
   public Setting<String> watermarkText;
   public Setting<Boolean> version;
   public Setting<Integer> waterMarkY;
   public Setting<Hud.Greeter> greeter;
   public Setting<String> spoofGreeter;

   public Hud() {
      super("HUD", "hud", Module.Category.CLIENT, true, false, false);
      this.color = Vonware.colorManager.getColorAsInt();
      this.watermark = this.register(new Setting("Watermark", true));
      this.watermarkText = this.register(new Setting("Text", "Vonware", (v) -> {
         return (Boolean)this.watermark.getValue();
      }));
      this.version = this.register(new Setting("Version", true, (v) -> {
         return (Boolean)this.watermark.getValue();
      }));
      this.waterMarkY = this.register(new Setting("WaterMarkY", 0, 0, 500, (v) -> {
         return (Boolean)this.watermark.getValue();
      }));
      this.greeter = this.register(new Setting("Greeter", Hud.Greeter.TIME));
      this.spoofGreeter = this.register(new Setting("GreeterName", "Meow welcome :3", (v) -> {
         return this.greeter.getValue() == Hud.Greeter.CUSTOM;
      }));
   }

   public void onRender2D(Render2DEvent event) {
      if (this.greeter.getValue() != Hud.Greeter.NONE) {
         this.renderGreeter(event.getContext());
      }

      if ((Boolean)this.watermark.getValue()) {
         String string = (Boolean)this.version.getValue() ? (String)this.watermarkText.getValue() + " v1.2" : (String)this.watermarkText.getValue();
         if (Boolean.valueOf((Boolean)ClickGui.getInstance().rainbow.getValue())) {
            Color color = new Color((Integer)ClickGui.getInstance().red.getValue(), (Integer)ClickGui.getInstance().green.getValue(), (Integer)ClickGui.getInstance().blue.getValue());
            if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
               event.getContext().method_51433(mc.field_1772, string, 2, 2, color.getRGB(), true);
            } else {
               int[] arrayOfInt = new int[]{1};
               char[] stringToCharArray = string.toCharArray();
               float f = 0.0F;
               char[] var7 = stringToCharArray;
               int var8 = stringToCharArray.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  char c = var7[var9];
                  event.getContext().method_51433(mc.field_1772, String.valueOf(c), 2 + (int)f, 2 + (Integer)this.waterMarkY.getValue(), color.getRGB(), true);
                  f += (float)mc.field_1772.method_1727(String.valueOf(c));
                  int var10002 = arrayOfInt[0]++;
               }
            }
         } else {
            event.getContext().method_51433(mc.field_1772, string, 2, 2 + (Integer)this.waterMarkY.getValue(), this.color, true);
         }
      }

   }

   public void renderGreeter(class_332 context) {
      int width = mc.method_22683().method_4486();
      this.color = ColorUtil.toRGBA((Integer)ClickGui.getInstance().red.getValue(), (Integer)ClickGui.getInstance().green.getValue(), (Integer)ClickGui.getInstance().blue.getValue());
      String text = "";
      switch((Hud.Greeter)this.greeter.getValue()) {
      case TIME:
         text = text + MathUtil.getTimeOfDay() + this.getSpoofedName();
         break;
      case CHRISTMAS:
         text = text + "MERRY CHRISTMAS, " + this.getSpoofedName();
         break;
      case LONG:
         text = text + "Welcome to Vonware, " + this.getSpoofedName();
         break;
      case II:
         text = text + "II Welcomes you, " + this.getSpoofedName();
         break;
      case CUSTOM:
         text = text + (String)this.spoofGreeter.getValue();
         break;
      default:
         text = text + "Welcome, " + this.getSpoofedName() + " :^)";
      }

      if ((Boolean)ClickGui.getInstance().rainbow.getValue()) {
         if (ClickGui.getInstance().rainbowModeHud.getValue() == ClickGui.rainbowMode.Static) {
            context.method_51433(mc.field_1772, text, (int)((float)width / 2.0F - (float)mc.field_1772.method_1727(text) / 2.0F + 2.0F), 2, ColorUtil.rainbow((Integer)ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
         } else {
            int[] counter1 = new int[]{1};
            char[] stringToCharArray = text.toCharArray();
            float i = 0.0F;
            char[] var7 = stringToCharArray;
            int var8 = stringToCharArray.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               char c = var7[var9];
               context.method_51433(mc.field_1772, String.valueOf(c), (int)((float)width / 2.0F - (float)mc.field_1772.method_1727(text) / 2.0F + 2.0F + i), 2, ColorUtil.rainbow(counter1[0] * (Integer)ClickGui.getInstance().rainbowHue.getValue()).getRGB(), true);
               i += (float)mc.field_1772.method_1727(String.valueOf(c));
               int var10002 = counter1[0]++;
            }
         }
      } else {
         context.method_51433(mc.field_1772, text, (int)((float)width / 2.0F - (float)mc.field_1772.method_1727(text) / 2.0F + 2.0F), 2, this.color, true);
      }

   }

   public String getSpoofedName() {
      NameHider nameHiderModule = (NameHider)Vonware.moduleManager.getModuleByClass(NameHider.class);
      return nameHiderModule.isEnabled() ? (String)nameHiderModule.newName.getValue() : mc.field_1724.method_5477().getString();
   }

   public static enum Greeter {
      NONE,
      NAME,
      TIME,
      CHRISTMAS,
      LONG,
      II,
      CUSTOM;

      // $FF: synthetic method
      private static Hud.Greeter[] $values() {
         return new Hud.Greeter[]{NONE, NAME, TIME, CHRISTMAS, LONG, II, CUSTOM};
      }
   }
}
