package me.spikestinger.Vonware.util;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_327;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_761;
import net.minecraft.class_7833;
import net.minecraft.class_293.class_5596;

public class RenderUtil implements me.spikestinger.Vonware.util.traits.Util {
   private final class_327 textRenderer;

   public RenderUtil() {
      this.textRenderer = mc.field_1772;
   }

   public static void rect(class_4587 stack, float x1, float y1, float x2, float y2, int color) {
      rectFilled(stack, x1, y1, x2, y2, color);
   }

   public static void rect(class_4587 stack, float x1, float y1, float x2, float y2, int color, float width) {
      drawHorizontalLine(stack, x1, x2, y1, color, width);
      drawVerticalLine(stack, x2, y1, y2, color, width);
      drawHorizontalLine(stack, x1, x2, y2, color, width);
      drawVerticalLine(stack, x1, y1, y2, color, width);
   }

   protected static void drawHorizontalLine(class_4587 matrices, float x1, float x2, float y, int color) {
      if (x2 < x1) {
         float i = x1;
         x1 = x2;
         x2 = i;
      }

      rectFilled(matrices, x1, y, x2 + 1.0F, y + 1.0F, color);
   }

   protected static void drawVerticalLine(class_4587 matrices, float x, float y1, float y2, int color) {
      if (y2 < y1) {
         float i = y1;
         y1 = y2;
         y2 = i;
      }

      rectFilled(matrices, x, y1 + 1.0F, x + 1.0F, y2, color);
   }

   protected static void drawHorizontalLine(class_4587 matrices, float x1, float x2, float y, int color, float width) {
      if (x2 < x1) {
         float i = x1;
         x1 = x2;
         x2 = i;
      }

      rectFilled(matrices, x1, y, x2 + width, y + width, color);
   }

   protected static void drawVerticalLine(class_4587 matrices, float x, float y1, float y2, int color, float width) {
      if (y2 < y1) {
         float i = y1;
         y1 = y2;
         y2 = i;
      }

      rectFilled(matrices, x, y1 + width, x + width, y2, color);
   }

   public static void rectFilled(class_4587 matrix, float x1, float y1, float x2, float y2, int color) {
      float i;
      if (x1 < x2) {
         i = x1;
         x1 = x2;
         x2 = i;
      }

      if (y1 < y2) {
         i = y1;
         y1 = y2;
         y2 = i;
      }

      float f = (float)(color >> 24 & 255) / 255.0F;
      float g = (float)(color >> 16 & 255) / 255.0F;
      float h = (float)(color >> 8 & 255) / 255.0F;
      float j = (float)(color & 255) / 255.0F;
      class_287 bufferBuilder = class_289.method_1348().method_1349();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(class_757::method_34540);
      bufferBuilder.method_1328(class_5596.field_27382, class_290.field_1576);
      bufferBuilder.method_22918(matrix.method_23760().method_23761(), x1, y2, 0.0F).method_22915(g, h, j, f).method_1344();
      bufferBuilder.method_22918(matrix.method_23760().method_23761(), x2, y2, 0.0F).method_22915(g, h, j, f).method_1344();
      bufferBuilder.method_22918(matrix.method_23760().method_23761(), x2, y1, 0.0F).method_22915(g, h, j, f).method_1344();
      bufferBuilder.method_22918(matrix.method_23760().method_23761(), x1, y1, 0.0F).method_22915(g, h, j, f).method_1344();
      class_286.method_43433(bufferBuilder.method_1326());
      RenderSystem.disableBlend();
   }

   public static void drawBoxFilled(class_4587 stack, class_238 box, Color c) {
      float minX = (float)(box.field_1323 - mc.method_1561().field_4686.method_19326().method_10216());
      float minY = (float)(box.field_1322 - mc.method_1561().field_4686.method_19326().method_10214());
      float minZ = (float)(box.field_1321 - mc.method_1561().field_4686.method_19326().method_10215());
      float maxX = (float)(box.field_1320 - mc.method_1561().field_4686.method_19326().method_10216());
      float maxY = (float)(box.field_1325 - mc.method_1561().field_4686.method_19326().method_10214());
      float maxZ = (float)(box.field_1324 - mc.method_1561().field_4686.method_19326().method_10215());
      class_289 tessellator = class_289.method_1348();
      class_287 bufferBuilder = tessellator.method_1349();
      setup3D();
      RenderSystem.setShader(class_757::method_34540);
      bufferBuilder.method_1328(class_5596.field_27382, class_290.field_1576);
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, minY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, minY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, minY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, minY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, maxY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, maxY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, maxY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, maxY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, minY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, maxY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, maxY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, minY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, minY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, maxY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, maxY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, minY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, minY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, minY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), maxX, maxY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, maxY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, minY, minZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, minY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, maxY, maxZ).method_39415(c.getRGB()).method_1344();
      bufferBuilder.method_22918(stack.method_23760().method_23761(), minX, maxY, minZ).method_39415(c.getRGB()).method_1344();
      tessellator.method_1350();
      clean3D();
   }

   public static void drawBoxFilled(class_4587 stack, class_243 vec, Color c) {
      drawBoxFilled(stack, class_238.method_29968(vec), c);
   }

   public static void drawBoxFilled(class_4587 stack, class_2338 bp, Color c) {
      drawBoxFilled(stack, new class_238(bp), c);
   }

   public static void drawBox(class_4587 stack, class_238 box, Color c, double lineWidth) {
      float minX = (float)(box.field_1323 - mc.method_1561().field_4686.method_19326().method_10216());
      float minY = (float)(box.field_1322 - mc.method_1561().field_4686.method_19326().method_10214());
      float minZ = (float)(box.field_1321 - mc.method_1561().field_4686.method_19326().method_10215());
      float maxX = (float)(box.field_1320 - mc.method_1561().field_4686.method_19326().method_10216());
      float maxY = (float)(box.field_1325 - mc.method_1561().field_4686.method_19326().method_10214());
      float maxZ = (float)(box.field_1324 - mc.method_1561().field_4686.method_19326().method_10215());
      class_289 tessellator = class_289.method_1348();
      class_287 bufferBuilder = tessellator.method_1349();
      setup3D();
      RenderSystem.lineWidth((float)lineWidth);
      RenderSystem.setShader(class_757::method_34535);
      RenderSystem.defaultBlendFunc();
      bufferBuilder.method_1328(class_5596.field_27377, class_290.field_29337);
      class_761.method_22980(stack, bufferBuilder, (double)minX, (double)minY, (double)minZ, (double)maxX, (double)maxY, (double)maxZ, (float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      tessellator.method_1350();
      clean3D();
   }

   public static void drawBox(class_4587 stack, class_243 vec, Color c, double lineWidth) {
      drawBox(stack, class_238.method_29968(vec), c, lineWidth);
   }

   public static void drawBox(class_4587 stack, class_2338 bp, Color c, double lineWidth) {
      drawBox(stack, new class_238(bp), c, lineWidth);
   }

   public static class_4587 matrixFrom(class_243 pos) {
      class_4587 matrices = new class_4587();
      class_4184 camera = mc.field_1773.method_19418();
      matrices.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
      matrices.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180.0F));
      matrices.method_22904(pos.method_10216() - camera.method_19326().field_1352, pos.method_10214() - camera.method_19326().field_1351, pos.method_10215() - camera.method_19326().field_1350);
      return matrices;
   }

   public static void setup() {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
   }

   public static void setup3D() {
      setup();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.disableCull();
   }

   public static void clean() {
      RenderSystem.disableBlend();
   }

   public static void clean3D() {
      clean();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
   }
}
