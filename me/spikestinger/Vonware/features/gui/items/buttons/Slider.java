package me.spikestinger.Vonware.features.gui.items.buttons;

import java.util.Iterator;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.gui.Component;
import me.spikestinger.Vonware.features.gui.VonwareGui;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.RenderUtil;
import net.minecraft.class_124;
import net.minecraft.class_332;
import org.lwjgl.glfw.GLFW;

public class Slider extends Button {
   private final Number min;
   private final Number max;
   private final int difference;
   public Setting<Number> setting;

   public Slider(Setting<Number> setting) {
      super(setting.getName());
      this.setting = setting;
      this.min = (Number)setting.getMin();
      this.max = (Number)setting.getMax();
      this.difference = this.max.intValue() - this.min.intValue();
      this.width = 15;
   }

   public void drawScreen(class_332 context, int mouseX, int mouseY, float partialTicks) {
      this.dragSetting(mouseX, mouseY);
      RenderUtil.rect(context.method_51448(), this.x, this.y, this.x + (float)this.width + 7.4F, this.y + (float)this.height - 0.5F, !this.isHovering(mouseX, mouseY) ? 290805077 : -2007673515);
      RenderUtil.rect(context.method_51448(), this.x, this.y, ((Number)this.setting.getValue()).floatValue() <= this.min.floatValue() ? this.x : this.x + ((float)this.width + 7.4F) * this.partialMultiplier(), this.y + (float)this.height - 0.5F, !this.isHovering(mouseX, mouseY) ? Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).hoverAlpha.getValue()) : Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue()));
      this.drawString(this.getName() + " " + class_124.field_1080 + (this.setting.getValue() instanceof Float ? (Number)this.setting.getValue() : ((Number)this.setting.getValue()).doubleValue()), (double)(this.x + 2.3F), (double)(this.y - 1.7F - (float)VonwareGui.getClickGui().getTextOffset()), -1);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (this.isHovering(mouseX, mouseY)) {
         this.setSettingFromX(mouseX);
      }

   }

   public boolean isHovering(int mouseX, int mouseY) {
      Iterator var3 = VonwareGui.getClickGui().getComponents().iterator();

      while(var3.hasNext()) {
         Component component = (Component)var3.next();
         if (component.drag) {
            return false;
         }
      }

      return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() + 8.0F && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
   }

   public void update() {
      this.setHidden(!this.setting.isVisible());
   }

   private void dragSetting(int mouseX, int mouseY) {
      if (this.isHovering(mouseX, mouseY) && GLFW.glfwGetMouseButton(mc.method_22683().method_4490(), 0) == 1) {
         this.setSettingFromX(mouseX);
      }

   }

   public int getHeight() {
      return 14;
   }

   private void setSettingFromX(int mouseX) {
      float percent = ((float)mouseX - this.x) / ((float)this.width + 7.4F);
      if (this.setting.getValue() instanceof Double) {
         double result = (Double)this.setting.getMin() + (double)((float)this.difference * percent);
         this.setting.setValue((double)Math.round(10.0D * result) / 10.0D);
      } else if (this.setting.getValue() instanceof Float) {
         float result = (Float)this.setting.getMin() + (float)this.difference * percent;
         this.setting.setValue((float)Math.round(10.0F * result) / 10.0F);
      } else if (this.setting.getValue() instanceof Integer) {
         this.setting.setValue((Integer)this.setting.getMin() + (int)((float)this.difference * percent));
      }

   }

   private float middle() {
      return this.max.floatValue() - this.min.floatValue();
   }

   private float part() {
      return ((Number)this.setting.getValue()).floatValue() - this.min.floatValue();
   }

   private float partialMultiplier() {
      return this.part() / this.middle();
   }
}
