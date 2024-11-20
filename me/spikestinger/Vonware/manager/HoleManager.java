package me.spikestinger.Vonware.manager;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.spikestinger.Vonware.event.impl.UpdateEvent;
import me.spikestinger.Vonware.features.Feature;
import me.spikestinger.Vonware.util.PositionUtil;
import net.minecraft.class_1297;
import net.minecraft.class_1303;
import net.minecraft.class_1511;
import net.minecraft.class_1542;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2338.class_2339;
import net.minecraft.class_2350.class_2353;
import org.jetbrains.annotations.Nullable;

public class HoleManager extends Feature {
   private final int range = 8;
   private final List<HoleManager.Hole> holes = new ArrayList();
   private final class_2339 pos = new class_2339();

   public HoleManager() {
      EVENT_BUS.register(this);
   }

   @Subscribe
   private void onTick(UpdateEvent event) {
      this.holes.clear();

      for(int x = -8; x < 8; ++x) {
         for(int y = -8; y < 8; ++y) {
            for(int z = -8; z < 8; ++z) {
               this.pos.method_10102(mc.field_1724.method_23317() + (double)x, mc.field_1724.method_23318() + (double)y, mc.field_1724.method_23321() + (double)z);
               HoleManager.Hole hole = this.getHole(this.pos);
               if (hole != null) {
                  this.holes.add(hole);
               }
            }
         }
      }

   }

   @Nullable
   public HoleManager.Hole getHole(class_2338 pos) {
      if (mc.field_1687.method_8320(pos).method_26204() != class_2246.field_10124) {
         return null;
      } else {
         HoleManager.HoleType type = HoleManager.HoleType.BEDROCK;
         Iterator var3 = class_2353.field_11062.iterator();

         while(var3.hasNext()) {
            class_2350 direction = (class_2350)var3.next();
            class_2248 block = mc.field_1687.method_8320(pos.method_10093(direction)).method_26204();
            if (block == class_2246.field_10540) {
               type = HoleManager.HoleType.UNSAFE;
            } else if (block != class_2246.field_9987) {
               return null;
            }
         }

         return new HoleManager.Hole(pos, type);
      }
   }

   public static List<class_2338> getSurroundEntities(class_1297 entity) {
      List<class_2338> entities = new LinkedList();
      entities.add(entity.method_24515());
      class_2350[] var3 = class_2350.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         class_2350 dir = var3[var5];
         if (dir.method_10166().method_10179()) {
            entities.addAll(PositionUtil.getAllInBox(entity.method_5829(), entity.method_24515()));
         }
      }

      return entities;
   }

   public List<class_2338> getSurroundEntities(class_2338 pos) {
      List<class_2338> entities = new LinkedList();
      entities.add(pos);
      class_2350[] var3 = class_2350.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         class_2350 dir = var3[var5];
         if (dir.method_10166().method_10179()) {
            class_2338 pos1 = pos.method_10081(dir.method_10163());
            List<class_1297> box = mc.field_1687.method_8335((class_1297)null, new class_238(pos1)).stream().filter((e) -> {
               return !this.isEntityBlockingSurround(e);
            }).toList();
            if (!box.isEmpty()) {
               Iterator var9 = box.iterator();

               while(var9.hasNext()) {
                  class_1297 entity = (class_1297)var9.next();
                  entities.addAll(PositionUtil.getAllInBox(entity.method_5829(), pos));
               }
            }
         }
      }

      return entities;
   }

   public boolean isEntityBlockingSurround(class_1297 entity) {
      return entity instanceof class_1542 || entity instanceof class_1303 || entity instanceof class_1511;
   }

   public static List<class_2338> getEntitySurroundNoSupport(class_1297 entity) {
      List<class_2338> entities = getSurroundEntities(entity);
      List<class_2338> blocks = new CopyOnWriteArrayList();
      Iterator var4 = entities.iterator();

      while(var4.hasNext()) {
         class_2338 epos = (class_2338)var4.next();
         class_2350[] var6 = class_2350.values();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            class_2350 dir2 = var6[var8];
            if (dir2.method_10166().method_10179()) {
               class_2338 pos2 = epos.method_10081(dir2.method_10163());
               if (!entities.contains(pos2) && !blocks.contains(pos2)) {
                  double dist = mc.field_1724.method_5707(pos2.method_46558());
                  if (!(dist > 16.0D)) {
                     blocks.add(pos2);
                  }
               }
            }
         }
      }

      return blocks;
   }

   private static record Hole(class_2338 pos, HoleManager.HoleType holeType) {
      private Hole(class_2338 pos, HoleManager.HoleType holeType) {
         this.pos = pos;
         this.holeType = holeType;
      }

      public class_2338 pos() {
         return this.pos;
      }

      public HoleManager.HoleType holeType() {
         return this.holeType;
      }
   }

   private static enum HoleType {
      BEDROCK,
      UNSAFE;

      // $FF: synthetic method
      private static HoleManager.HoleType[] $values() {
         return new HoleManager.HoleType[]{BEDROCK, UNSAFE};
      }
   }
}
