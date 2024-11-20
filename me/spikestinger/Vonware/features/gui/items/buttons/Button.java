package me.spikestinger.Vonware.features.gui.items.buttons;

import java.util.Iterator;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.gui.Component;
import me.spikestinger.Vonware.features.gui.VonwareGui;
import me.spikestinger.Vonware.features.gui.items.Item;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.util.RenderUtil;
import net.minecraft.class_1109;
import net.minecraft.class_332;
import net.minecraft.class_3417;

public class Button extends Item {
   private boolean state;

   public Button(String name) {
      super(name);
      this.height = 15;
   }

   public void drawScreen(class_332 context, int mouseX, int mouseY, float partialTicks) {
      RenderUtil.rect(context.method_51448(), this.x, this.y, this.x + (float)this.width, this.y + (float)this.height - 0.5F, this.getState() ? (!this.isHovering(mouseX, mouseY) ? Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).hoverAlpha.getValue()) : Vonware.colorManager.getColorWithAlpha((Integer)((ClickGui)Vonware.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue())) : (!this.isHovering(mouseX, mouseY) ? 290805077 : -2007673515));
      this.drawString(this.getName(), (double)(this.x + 2.3F), (double)(this.y - 2.0F - (float)VonwareGui.getClickGui().getTextOffset()), this.getState() ? -1 : -5592406);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
         this.onMouseClick();
      }

   }

   public void onMouseClick() {
      this.state = !this.state;
      this.toggle();
      mc.method_1483().method_4873(class_1109.method_47978(class_3417.field_15015, 1.0F));
   }

   public void toggle() {
   }

   public boolean getState() {
      return this.state;
   }

   public int getHeight() {
      return 14;
   }

   public boolean isHovering(int mouseX, int mouseY) {
      Iterator var3 = VonwareGui.getClickGui().getComponents().iterator();

      while(var3.hasNext()) {
         Component component = (Component)var3.next();
         if (component.drag) {
            return false;
         }
      }

      return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
   }
}
