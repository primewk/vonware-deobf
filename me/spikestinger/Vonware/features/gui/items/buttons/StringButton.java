package me.spikestinger.Vonware.features.gui.items.buttons;

import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.gui.VonwareGui;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.RenderUtil;
import me.spikestinger.Vonware.util.models.Timer;
import net.minecraft.class_1109;
import net.minecraft.class_124;
import net.minecraft.class_155;
import net.minecraft.class_332;
import net.minecraft.class_3417;

public class StringButton extends Button {
   private static final Timer idleTimer = new Timer();
   private static boolean idle;
   private final Setting<String> setting;
   public boolean isListening;
   private StringButton.CurrentString currentString = new StringButton.CurrentString("");

   public StringButton(Setting<String> setting) {
      super(setting.getName());
      this.setting = setting;
      this.width = 15;
   }

   public static String removeLastChar(String str) {
      String output = "";
      if (str != null && str.length() > 0) {
         output = str.substring(0, str.length() - 1);
      }

      return output;
   }

   public void drawScreen(class_332 context, int mouseX, int mouseY, float partialTicks) {
      RenderUtil.rect(context.method_51448(), this.x, this.y, this.x + (float)this.width + 7.4F, this.y + (float)this.height - 0.5F, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).hoverAlpha.getValue()) : Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue())) : (!this.isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
      String var10001;
      if (this.isListening) {
         var10001 = this.currentString.string();
         this.drawString(var10001 + "_", (double)(this.x + 2.3F), (double)(this.y - 1.7F - (float)VonwareGui.getClickGui().getTextOffset()), this.getState() ? -1 : -5592406);
      } else {
         var10001 = this.setting.getName().equals("Buttons") ? "Buttons " : (this.setting.getName().equals("Prefix") ? "Prefix  " + class_124.field_1080 : "");
         this.drawString(var10001 + (String)this.setting.getValue(), (double)(this.x + 2.3F), (double)(this.y - 1.7F - (float)VonwareGui.getClickGui().getTextOffset()), this.getState() ? -1 : -5592406);
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (this.isHovering(mouseX, mouseY)) {
         mc.method_1483().method_4873(class_1109.method_47978(class_3417.field_15015, 1.0F));
      }

   }

   public void onKeyTyped(char typedChar, int keyCode) {
      if (this.isListening && class_155.method_643(typedChar)) {
         String var10001 = this.currentString.string();
         this.setString(var10001 + typedChar);
      }

   }

   public void onKeyPressed(int key) {
      if (this.isListening) {
         switch(key) {
         case 1:
            return;
         case 257:
            this.enterString();
         case 259:
            this.setString(removeLastChar(this.currentString.string()));
         }
      }

   }

   public void update() {
      this.setHidden(!this.setting.isVisible());
   }

   private void enterString() {
      if (this.currentString.string().isEmpty()) {
         this.setting.setValue((String)this.setting.getDefaultValue());
      } else {
         this.setting.setValue(this.currentString.string());
      }

      this.setString("");
      this.onMouseClick();
   }

   public int getHeight() {
      return 14;
   }

   public void toggle() {
      this.isListening = !this.isListening;
   }

   public boolean getState() {
      return !this.isListening;
   }

   public void setString(String newString) {
      this.currentString = new StringButton.CurrentString(newString);
   }

   public static String getIdleSign() {
      if (idleTimer.passedMs(500L)) {
         idle = !idle;
         idleTimer.reset();
      }

      return idle ? "_" : "";
   }

   public static record CurrentString(String string) {
      public CurrentString(String string) {
         this.string = string;
      }

      public String string() {
         return this.string;
      }
   }
}
