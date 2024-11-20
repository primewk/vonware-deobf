package me.spikestinger.Vonware.features.modules.render;

import java.awt.Color;
import me.spikestinger.Vonware.event.EventListener;
import me.spikestinger.Vonware.event.impl.Render3DEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.DirectionUtil;
import me.spikestinger.Vonware.util.RenderUtil;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_259;
import net.minecraft.class_2680;

public class PhaseESP extends Module {
   public final Setting<Integer> boxAlpha = this.register(new Setting("BoxAlpha", 15, 0, 255));
   public final Setting<Integer> lineAlpha = this.register(new Setting("LineAlpha", 50, 0, 255));
   public final Setting<Double> lineWidth = this.register(new Setting("LineWidth", 2.0D, 0.1D, 4.0D));
   public final Setting<Double> fadeDistance = this.register(new Setting("FadeDist", 0.5D, 0.0D, 1.0D));
   public final Setting<Boolean> diagonal = this.register(new Setting("Diagonal", true));

   public PhaseESP() {
      super("PhaseESP", "Shows safe places to Phase", Module.Category.RENDER, true, false, false);
   }

   @EventListener
   public void onRender3D(Render3DEvent event) {
      if (mc.field_1724 != null && mc.field_1687 != null && mc.field_1724.method_24828()) {
         class_2338 playerPos = mc.field_1724.method_24515();
         DirectionUtil.EightWayDirections[] var3 = DirectionUtil.EightWayDirections.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            DirectionUtil.EightWayDirections direction = var3[var5];
            if ((Boolean)this.diagonal.getValue() || DirectionUtil.isCardinal(direction)) {
               class_2338 blockPos = direction.offset(playerPos);
               this.phaseESPRender(blockPos, event, direction);
            }
         }
      }

   }

   private void phaseESPRender(class_2338 blockPos, Render3DEvent event, DirectionUtil.EightWayDirections direction) {
      if (!mc.field_1687.method_8320(blockPos).method_45474()) {
         class_2680 state = mc.field_1687.method_8320(blockPos.method_10074());
         Color color;
         if (state.method_45474()) {
            color = new Color(255, 0, 0, (Integer)this.boxAlpha.getValue());
         } else if (state.method_26214(mc.field_1687, blockPos.method_10074()) < 0.0F) {
            color = new Color(0, 255, 0, (Integer)this.boxAlpha.getValue());
         } else {
            color = new Color(255, 255, 0, (Integer)this.boxAlpha.getValue());
         }

         class_2338 playerPos = mc.field_1724.method_24515();
         class_243 pos = mc.field_1724.method_19538();
         double dx = pos.method_10216() - (double)playerPos.method_10263();
         double dz = pos.method_10215() - (double)playerPos.method_10260();
         double far = (Double)this.fadeDistance.getValue();
         double near = 1.0D - (Double)this.fadeDistance.getValue();
         if (direction == DirectionUtil.EightWayDirections.EAST && dx >= far) {
            this.BoxRender(blockPos, event, color);
         } else if (direction == DirectionUtil.EightWayDirections.WEST && dx <= near) {
            this.BoxRender(blockPos, event, color);
         } else if (direction == DirectionUtil.EightWayDirections.SOUTH && dz >= far) {
            this.BoxRender(blockPos, event, color);
         } else if (direction == DirectionUtil.EightWayDirections.NORTH && dz <= near) {
            this.BoxRender(blockPos, event, color);
         } else if (direction == DirectionUtil.EightWayDirections.NORTHEAST && dz <= near && dx >= far) {
            this.BoxRender(blockPos, event, color);
         } else if (direction == DirectionUtil.EightWayDirections.NORTHWEST && dz <= near && dx <= near) {
            this.BoxRender(blockPos, event, color);
         } else if (direction == DirectionUtil.EightWayDirections.SOUTHEAST && dz >= far && dx >= far) {
            this.BoxRender(blockPos, event, color);
         } else if (direction == DirectionUtil.EightWayDirections.SOUTHWEST && dz >= far && dx <= near) {
            this.BoxRender(blockPos, event, color);
         }
      }

   }

   private void BoxRender(class_2338 blockPos, Render3DEvent event, Color color) {
      class_238 render1 = class_259.method_1077().method_1107();
      class_238 render = new class_238((double)blockPos.method_10263() + render1.field_1323, (double)blockPos.method_10264() + render1.field_1322, (double)blockPos.method_10260() + render1.field_1321, (double)blockPos.method_10263() + render1.field_1320, (double)blockPos.method_10264() + render1.field_1322, (double)blockPos.method_10260() + render1.field_1324);
      RenderUtil.drawBoxFilled(event.getMatrix(), render, color);
      Color lineColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (Integer)this.lineAlpha.getValue());
      RenderUtil.drawBox(event.getMatrix(), render, lineColor, (Double)this.lineWidth.getValue());
   }
}
