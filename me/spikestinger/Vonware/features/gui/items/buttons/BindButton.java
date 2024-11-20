package me.spikestinger.Vonware.features.gui.items.buttons;

import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.gui.VonwareGui;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.features.settings.Bind;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.ColorUtil;
import me.spikestinger.Vonware.util.RenderUtil;
import net.minecraft.class_1109;
import net.minecraft.class_124;
import net.minecraft.class_332;
import net.minecraft.class_3417;

public class BindButton extends Button {
   private final Setting<Bind> setting;
   public boolean isListening;

   public BindButton(Setting<Bind> setting) {
      super(setting.getName());
      this.setting = setting;
      this.width = 15;
   }

   public void drawScreen(class_332 context, int mouseX, int mouseY, float partialTicks) {
      int color = ColorUtil.toARGB((Integer)ClickGui.getInstance().red.getValue(), (Integer)ClickGui.getInstance().green.getValue(), (Integer)ClickGui.getInstance().blue.getValue(), 255);
      RenderUtil.rect(context.method_51448(), this.x, this.y, this.x + (float)this.width + 7.4F, this.y + (float)this.height - 0.5F, this.getState() ? (!this.isHovering(mouseX, mouseY) ? 290805077 : -2007673515) : (!this.isHovering(mouseX, mouseY) ? Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).hoverAlpha.getValue()) : Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue())));
      if (this.isListening) {
         this.drawString("Press a Key...", (double)(this.x + 2.3F), (double)(this.y - 1.7F - (float)VonwareGui.getClickGui().getTextOffset()), -1);
      } else {
         String str = ((Bind)this.setting.getValue()).toString().toUpperCase();
         str = str.replace("KEY.KEYBOARD", "").replace(".", " ");
         String var10001 = this.setting.getName();
         this.drawString(var10001 + " " + class_124.field_1080 + str, (double)(this.x + 2.3F), (double)(this.y - 1.7F - (float)VonwareGui.getClickGui().getTextOffset()), this.getState() ? -1 : -5592406);
      }

   }

   public void update() {
      this.setHidden(!this.setting.isVisible());
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (this.isHovering(mouseX, mouseY)) {
         mc.method_1483().method_4873(class_1109.method_47978(class_3417.field_15015, 1.0F));
      }

   }

   public void onKeyPressed(int key) {
      if (this.isListening) {
         Bind bind = new Bind(key);
         if (key == 261 || key == 259 || key == 256) {
            bind = new Bind(-1);
         }

         this.setting.setValue(bind);
         this.onMouseClick();
      }

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
}
