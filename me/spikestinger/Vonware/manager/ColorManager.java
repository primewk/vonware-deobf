package me.spikestinger.Vonware.manager;

import java.awt.Color;
import me.spikestinger.Vonware.features.gui.Component;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.util.ColorUtil;

public class ColorManager {
   private float red = 1.0F;
   private float green = 1.0F;
   private float blue = 1.0F;
   private float alpha = 1.0F;
   private Color color;

   public ColorManager() {
      this.color = new Color(this.red, this.green, this.blue, this.alpha);
   }

   public void init() {
      ClickGui ui = ClickGui.getInstance();
      this.setColor((Integer)ui.red.getValue(), (Integer)ui.green.getValue(), (Integer)ui.blue.getValue(), (Integer)ui.hoverAlpha.getValue());
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   public int getColorAsInt() {
      return ColorUtil.toRGBA(this.color);
   }

   public int getColorAsIntFullAlpha() {
      return ColorUtil.toRGBA(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255));
   }

   public int getColorWithAlpha(int alpha) {
      return (Boolean)ClickGui.getInstance().rainbow.getValue() ? ColorUtil.rainbow(Component.counter1[0] * (Integer)ClickGui.getInstance().rainbowHue.getValue()).getRGB() : ColorUtil.toRGBA(new Color(this.red, this.green, this.blue, (float)alpha / 255.0F));
   }

   public void setColor(float red, float green, float blue, float alpha) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.alpha = alpha;
      this.updateColor();
   }

   public void updateColor() {
      this.setColor(new Color(this.red, this.green, this.blue, this.alpha));
   }

   public void setColor(int red, int green, int blue, int alpha) {
      this.red = (float)red / 255.0F;
      this.green = (float)green / 255.0F;
      this.blue = (float)blue / 255.0F;
      this.alpha = (float)alpha / 255.0F;
      this.updateColor();
   }

   public void setRed(float red) {
      this.red = red;
      this.updateColor();
   }

   public void setGreen(float green) {
      this.green = green;
      this.updateColor();
   }

   public void setBlue(float blue) {
      this.blue = blue;
      this.updateColor();
   }

   public void setAlpha(float alpha) {
      this.alpha = alpha;
      this.updateColor();
   }
}
