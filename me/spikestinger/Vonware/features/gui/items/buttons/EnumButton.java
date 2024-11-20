package me.spikestinger.Vonware.features.gui.items.buttons;

import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.gui.VonwareGui;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.RenderUtil;
import net.minecraft.class_1109;
import net.minecraft.class_124;
import net.minecraft.class_332;
import net.minecraft.class_3417;

public class EnumButton extends Button {
   public Setting<Enum<?>> setting;

   public EnumButton(Setting<Enum<?>> setting) {
      super(setting.getName());
      this.setting = setting;
      this.width = 15;
   }

   public void drawScreen(class_332 context, int mouseX, int mouseY, float partialTicks) {
      RenderUtil.rect(context.method_51448(), this.x, this.y, this.x + (float)this.width + 7.4F, this.y + (float)this.height - 0.5F, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).hoverAlpha.getValue()) : Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue())) : (!this.isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
      String var10001 = this.setting.getName();
      this.drawString(var10001 + " " + class_124.field_1080 + (this.setting.currentEnumName().equalsIgnoreCase("ABC") ? "ABC" : this.setting.currentEnumName()), (double)(this.x + 2.3F), (double)(this.y - 1.7F - (float)VonwareGui.getClickGui().getTextOffset()), this.getState() ? -1 : -5592406);
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

   public int getHeight() {
      return 14;
   }

   public void toggle() {
      this.setting.increaseEnum();
   }

   public boolean getState() {
      return true;
   }
}
