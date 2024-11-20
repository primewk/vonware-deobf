package me.spikestinger.Vonware.features.modules.combat;

import java.awt.Color;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import me.spikestinger.Vonware.event.EventListener;
import me.spikestinger.Vonware.event.impl.AttackBlockEvent;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.event.impl.Render3DEvent;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.manager.FriendManager;
import me.spikestinger.Vonware.manager.HoleManager;
import me.spikestinger.Vonware.manager.InteractionManager;
import me.spikestinger.Vonware.manager.InventoryManager;
import me.spikestinger.Vonware.manager.NetworkManager;
import me.spikestinger.Vonware.manager.PositionManager;
import me.spikestinger.Vonware.manager.RotationManager;
import me.spikestinger.Vonware.util.EvictingQueue;
import me.spikestinger.Vonware.util.ExplosionUtil;
import me.spikestinger.Vonware.util.RenderUtil;
import me.spikestinger.Vonware.util.traits.Util;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_259;
import net.minecraft.class_2596;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2846;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_742;
import net.minecraft.class_2846.class_2847;
import net.minecraft.class_5253.class_5254;
import org.jetbrains.annotations.NotNull;

public class HazelMine extends Module {
   public final Setting<Boolean> multitaskConfig = this.register(new Setting("Multitask", false));
   public final Setting<Boolean> autoConfig = this.register(new Setting("Auto", true));
   public final Setting<Boolean> autoRemineConfig = this.register(new Setting("AutoRemine", true, (v) -> {
      return (Boolean)this.autoConfig.getValue();
   }));
   public final Setting<Boolean> strictDirectionConfig = this.register(new Setting("StrictDirection", false, (v) -> {
      return (Boolean)this.autoConfig.getValue();
   }));
   public final Setting<Float> enemyRangeConfig = this.register(new Setting("EnemyRange", 5.0F, 1.0F, 10.0F, (v) -> {
      return (Boolean)this.autoConfig.getValue();
   }));
   public final Setting<Boolean> doubleBreakConfig = this.register(new Setting("DoubleBreak", true));
   public final Setting<Boolean> safetyConfig = this.register(new Setting("Safety", true));
   public final Setting<Float> rangeConfig = this.register(new Setting("Range", 4.0F, 0.1F, 5.0F));
   public final Setting<Float> speedConfig = this.register(new Setting("Speed", 1.0F, 0.1F, 1.0F));
   public final Setting<Boolean> rotateConfig = this.register(new Setting("Rotate", true));
   public final Setting<Boolean> switchResetConfig = this.register(new Setting("SwitchReset", false));
   public final Setting<Boolean> grimConfig = this.register(new Setting("Grim", true));
   public final Setting<Boolean> instantConfig = this.register(new Setting("Instant", true));
   public final Setting<Boolean> headFreeConfig = this.register(new Setting("FreeHead", true));
   public final Setting<Double> linewidthConfig = this.register(new Setting("Linewidth", 2.0D, 0.1D, 4.0D));
   public final Setting<Integer> alphaConfig = this.register(new Setting("Box Alpha", 70, 0, 255));
   public final Setting<Integer> lineAlphaConfig = this.register(new Setting("Line Alpha", 70, 0, 255));
   private Deque<HazelMine.MiningData> miningQueue = new EvictingQueue(2);
   private long lastBreak;
   private boolean manualOverride;

   public HazelMine() {
      super("HazelMine", "hazle mine ^_^", Module.Category.COMBAT, true, false, true);
   }

   public void onEnable() {
      if ((Boolean)this.doubleBreakConfig.getValue()) {
         this.miningQueue = new EvictingQueue(2);
      } else {
         this.miningQueue = new EvictingQueue(1);
      }

   }

   public void onDisable() {
      this.miningQueue.clear();
      this.manualOverride = false;
      InventoryManager.syncToClient();
   }

   @EventListener
   public void onTick() {
      HazelMine.MiningData miningData = null;
      if (!this.miningQueue.isEmpty()) {
         miningData = (HazelMine.MiningData)this.miningQueue.getFirst();
      }

      if (mc.field_1724.method_20232() && (Boolean)this.headFreeConfig.getValue()) {
         class_2338 headBreakPos = PositionManager.getPlayerPos().method_10086(1);
         if (mc.field_1687.method_8320(headBreakPos).method_26204().method_36555() != -1.0F && !mc.field_1687.method_8320(headBreakPos).method_26215() && !mc.field_1724.method_7337()) {
            HazelMine.MiningData headBreak = new HazelMine.MiningData(headBreakPos, (Boolean)this.strictDirectionConfig.getValue() ? InteractionManager.getPlaceDirectionGrim(headBreakPos) : class_2350.field_11036);
            this.overrideSet(headBreak);
         }
      }

      double distance;
      if ((Boolean)this.autoConfig.getValue() && !this.manualOverride && (miningData == null || mc.field_1687.method_22347(miningData.getPos()))) {
         class_1657 playerTarget = null;
         distance = 3.4028234663852886E38D;
         FriendManager.refreshFriends();
         Iterator var5 = mc.field_1687.method_18456().iterator();

         while(var5.hasNext()) {
            class_742 abstractClientPlayerEntity = (class_742)var5.next();
            if (abstractClientPlayerEntity != mc.field_1724 && !FriendManager.isFriend(abstractClientPlayerEntity.method_5477().getString())) {
               double dist = (double)mc.field_1724.method_5739(abstractClientPlayerEntity);
               if (!(dist > (double)(Float)this.enemyRangeConfig.getValue()) && dist < distance) {
                  distance = dist;
                  playerTarget = abstractClientPlayerEntity;
               }
            }
         }

         if (playerTarget != null) {
            PriorityQueue<HazelMine.AutoMineCalc> miningPositions = this.getMiningPosition(playerTarget);
            PriorityQueue<HazelMine.AutoMineCalc> miningPositionsNoAir = this.getNoAir(miningPositions);
            PriorityQueue<HazelMine.AutoMineCalc> cityPositions = (Boolean)this.autoRemineConfig.getValue() ? miningPositions : miningPositionsNoAir;
            if (cityPositions.isEmpty()) {
               return;
            }

            HazelMine.AutoMineCalc cityPos;
            if ((Boolean)this.doubleBreakConfig.getValue()) {
               cityPos = (HazelMine.AutoMineCalc)cityPositions.poll();
               if (cityPos != null) {
                  miningPositionsNoAir.remove(cityPos);
                  class_2338 cityPos2 = null;
                  if (!miningPositionsNoAir.isEmpty()) {
                     cityPos2 = ((HazelMine.AutoMineCalc)miningPositionsNoAir.poll()).pos();
                  }

                  HazelMine.AutoMiningData data1;
                  if (cityPos2 != null && cityPos.pos() != cityPos2) {
                     if (!mc.field_1687.method_22347(cityPos.pos()) && !mc.field_1687.method_22347(cityPos2) && !this.isBlockDelayGrim()) {
                        data1 = new HazelMine.AutoMiningData(cityPos2, (Boolean)this.strictDirectionConfig.getValue() ? InteractionManager.getPlaceDirectionGrim(cityPos2) : class_2350.field_11036);
                        HazelMine.MiningData data2 = new HazelMine.AutoMiningData(cityPos.pos(), (Boolean)this.strictDirectionConfig.getValue() ? InteractionManager.getPlaceDirectionGrim(cityPos.pos()) : class_2350.field_11036);
                        this.startMining(data1);
                        this.startMining(data2);
                        this.miningQueue.addFirst(data1);
                        this.miningQueue.addFirst(data2);
                     }
                  } else if (!mc.field_1687.method_22347(cityPos.pos()) && !this.isBlockDelayGrim()) {
                     data1 = new HazelMine.AutoMiningData(cityPos.pos(), (Boolean)this.strictDirectionConfig.getValue() ? InteractionManager.getPlaceDirectionGrim(cityPos.pos()) : class_2350.field_11036);
                     this.startMining(data1);
                     this.miningQueue.addFirst(data1);
                  }
               }
            } else {
               cityPos = (HazelMine.AutoMineCalc)cityPositions.poll();
               if (cityPos != null && !this.isBlockDelayGrim()) {
                  if (miningData instanceof HazelMine.AutoMiningData && miningData.isInstantRemine() && !mc.field_1687.method_22347(miningData.getPos()) && (Boolean)this.autoRemineConfig.getValue()) {
                     this.stopMining(miningData);
                  } else if (!mc.field_1687.method_22347(cityPos.pos()) && !this.isBlockDelayGrim()) {
                     HazelMine.MiningData data = new HazelMine.AutoMiningData(cityPos.pos(), (Boolean)this.strictDirectionConfig.getValue() ? InteractionManager.getPlaceDirectionGrim(cityPos.pos()) : class_2350.field_11036);
                     this.startMining(data);
                     this.miningQueue.addFirst(data);
                  }
               }
            }
         }
      }

      if (!this.miningQueue.isEmpty()) {
         Iterator var13 = this.miningQueue.iterator();

         while(var13.hasNext()) {
            HazelMine.MiningData data = (HazelMine.MiningData)var13.next();
            if (this.isDataPacketMine(data) && data.getState().method_26215()) {
               InventoryManager.syncToClient();
               this.miningQueue.remove(data);
               return;
            }

            float damageDelta = InteractionManager.calcBlockBreakingDelta(data.getState(), mc.field_1687, data.getPos());
            data.damage(damageDelta);
            if (data.getBlockDamage() >= 1.0F && this.isDataPacketMine(data)) {
               if (mc.field_1724.method_6115() && !(Boolean)this.multitaskConfig.getValue()) {
                  return;
               }

               if (data.getSlot() != -1) {
                  InventoryManager.setSlot(data.getSlot());
               }
            }
         }

         HazelMine.MiningData miningData2 = (HazelMine.MiningData)this.miningQueue.getFirst();
         if (miningData2 != null) {
            distance = mc.field_1724.method_33571().method_1025(miningData2.getPos().method_46558());
            if (distance > ((Float)this.rangeConfig.getValue()).doubleValue() * ((Float)this.rangeConfig.getValue()).doubleValue()) {
               this.miningQueue.remove(miningData2);
               return;
            }

            if (miningData2.getState().method_26215()) {
               if (this.manualOverride) {
                  this.manualOverride = false;
                  this.miningQueue.remove(miningData2);
                  return;
               }

               if ((Boolean)this.instantConfig.getValue()) {
                  if (miningData2 instanceof HazelMine.AutoMiningData && !(Boolean)this.autoRemineConfig.getValue()) {
                     this.miningQueue.remove(miningData2);
                     return;
                  }

                  miningData2.setInstantRemine();
                  miningData2.setDamage(1.0F);
               } else {
                  miningData2.resetDamage();
               }

               return;
            }

            if (miningData2.getBlockDamage() >= (Float)this.speedConfig.getValue() || miningData2.isInstantRemine()) {
               if (mc.field_1724.method_6115() && !(Boolean)this.multitaskConfig.getValue()) {
                  return;
               }

               this.stopMining(miningData2);
            }
         }
      }

   }

   @EventListener
   public void onAttackBlock(AttackBlockEvent event) {
      if (event.getState().method_26204().method_36555() != -1.0F && !event.getState().method_26215() && !mc.field_1724.method_7337()) {
         event.cancel();
         HazelMine.MiningData miningData = new HazelMine.MiningData(event.getPos(), event.getDirection());
         this.overrideSet(miningData);
      }

   }

   public void overrideSet(HazelMine.MiningData miningData) {
      int queueSize = this.miningQueue.size();
      if (queueSize == 0) {
         this.attemptMine(miningData.getPos(), miningData.getDirection());
      } else {
         HazelMine.MiningData data1;
         if (queueSize == 1) {
            data1 = (HazelMine.MiningData)this.miningQueue.getFirst();
            if (data1.getPos().equals(miningData.getPos())) {
               return;
            }

            if (data1 instanceof HazelMine.AutoMiningData) {
               this.manualOverride = true;
            }

            this.attemptMine(miningData.getPos(), miningData.getDirection());
         } else if (queueSize == 2) {
            data1 = (HazelMine.MiningData)this.miningQueue.getFirst();
            HazelMine.MiningData data2 = (HazelMine.MiningData)this.miningQueue.getLast();
            if (data1.getPos().equals(miningData.getPos()) || data2.getPos().equals(miningData.getPos())) {
               return;
            }

            if (data1 instanceof HazelMine.AutoMiningData || data2 instanceof HazelMine.AutoMiningData) {
               this.manualOverride = true;
            }

            this.attemptMine(miningData.getPos(), miningData.getDirection());
         }
      }

      mc.field_1724.method_6104(class_1268.field_5808);
   }

   @EventListener
   public void onPacketOutbound(PacketEvent.Outbound event) {
      if (event.getPacket() instanceof class_2596 && (Boolean)this.switchResetConfig.getValue()) {
         Iterator var2 = this.miningQueue.iterator();

         while(var2.hasNext()) {
            HazelMine.MiningData data = (HazelMine.MiningData)var2.next();
            data.resetDamage();
         }
      }

   }

   @EventListener
   public void onRender3D(Render3DEvent event) {
      Iterator var2 = this.miningQueue.iterator();

      while(var2.hasNext()) {
         HazelMine.MiningData data = (HazelMine.MiningData)var2.next();
         this.renderMiningData(event.getMatrix(), data);
      }

   }

   private void renderMiningData(class_4587 matrixStack, HazelMine.MiningData data) {
      if (data != null && !mc.field_1724.method_7337() && data.getBlockDamage() > 0.01F) {
         float miningSpeed = this.isDataPacketMine(data) ? 1.0F : (Float)this.speedConfig.getValue();
         class_2338 mining = data.getPos();
         class_265 outlineShape = class_259.method_1077();
         if (!data.isInstantRemine()) {
            outlineShape = data.getState().method_26218(mc.field_1687, mining);
            outlineShape = outlineShape.method_1110() ? class_259.method_1077() : outlineShape;
         }

         class_238 render1 = outlineShape.method_1107();
         class_238 render = new class_238((double)mining.method_10263() + render1.field_1323, (double)mining.method_10264() + render1.field_1322, (double)mining.method_10260() + render1.field_1321, (double)mining.method_10263() + render1.field_1320, (double)mining.method_10264() + render1.field_1325, (double)mining.method_10260() + render1.field_1324);
         class_243 center = render.method_1005();
         float scale = class_3532.method_15363(data.getBlockDamage() / miningSpeed, 0.0F, 1.0F);
         double dx = (render1.field_1320 - render1.field_1323) / 2.0D;
         double dy = (render1.field_1325 - render1.field_1322) / 2.0D;
         double dz = (render1.field_1324 - render1.field_1321) / 2.0D;
         class_238 scaled = (new class_238(center, center)).method_1009(dx * (double)scale, dy * (double)scale, dz * (double)scale);
         int colourint = data.getBlockDamage() > 0.95F * miningSpeed ? 1610678016 : 1627324416;
         Color colour = new Color(class_5254.method_27765(colourint), class_5254.method_27766(colourint), class_5254.method_27767(colourint), (Integer)this.alphaConfig.getValue());
         Color linecolour = new Color(class_5254.method_27765(colourint), class_5254.method_27766(colourint), class_5254.method_27767(colourint), (Integer)this.lineAlphaConfig.getValue());
         RenderUtil.drawBoxFilled(matrixStack, scaled, colour);
         RenderUtil.drawBox(matrixStack, scaled, linecolour, (Double)this.linewidthConfig.getValue());
      }

   }

   private PriorityQueue<HazelMine.AutoMineCalc> getNoAir(PriorityQueue<HazelMine.AutoMineCalc> calcs) {
      PriorityQueue<HazelMine.AutoMineCalc> noAir = new PriorityQueue();
      Iterator var3 = calcs.iterator();

      while(var3.hasNext()) {
         HazelMine.AutoMineCalc calc = (HazelMine.AutoMineCalc)var3.next();
         if (!mc.field_1687.method_22347(calc.pos())) {
            noAir.add(calc);
         }
      }

      return noAir;
   }

   private PriorityQueue<HazelMine.AutoMineCalc> getMiningPosition(class_1657 entity) {
      List<class_2338> entityIntersections = HoleManager.getSurroundEntities((class_1297)entity);
      PriorityQueue<HazelMine.AutoMineCalc> miningPositions = new PriorityQueue();
      Iterator var4 = entityIntersections.iterator();

      while(var4.hasNext()) {
         class_2338 blockPos = (class_2338)var4.next();
         double dist = mc.field_1724.method_33571().method_1025(blockPos.method_46558());
         if (!(dist > ((Float)this.rangeConfig.getValue()).doubleValue() * ((Float)this.rangeConfig.getValue()).doubleValue()) && !mc.field_1687.method_8320(blockPos).method_45474() && !this.isSelfBlock(blockPos)) {
            miningPositions.add(new HazelMine.AutoMineCalc(blockPos, Double.MAX_VALUE));
         }
      }

      List<class_2338> surroundBlocks = HoleManager.getEntitySurroundNoSupport(entity);
      Iterator var12 = surroundBlocks.iterator();

      while(var12.hasNext()) {
         class_2338 blockPos = (class_2338)var12.next();
         double dist = mc.field_1724.method_33571().method_1025(blockPos.method_46558());
         if (!(dist > ((Float)this.rangeConfig.getValue()).doubleValue() * ((Float)this.rangeConfig.getValue()).doubleValue())) {
            double damage = ExplosionUtil.getDamageTo(entity, blockPos.method_46558().method_1023(0.0D, -0.5D, 0.0D), true);
            if (!this.isSelfBlock(blockPos)) {
               miningPositions.add(new HazelMine.AutoMineCalc(blockPos, damage));
            }
         }
      }

      return miningPositions;
   }

   private void attemptMine(class_2338 pos, class_2350 direction) {
      if (!this.isBlockDelayGrim()) {
         HazelMine.MiningData miningData = new HazelMine.MiningData(pos, direction);
         this.startMining(miningData);
         this.miningQueue.addFirst(miningData);
      }

   }

   private void startMining(HazelMine.MiningData data) {
      if (!data.getState().method_26215() && !data.isStarted()) {
         NetworkManager.sendSequencedPacket((id) -> {
            return new class_2846(class_2847.field_12973, data.getPos(), data.getDirection(), id);
         });
         NetworkManager.sendSequencedPacket((id) -> {
            return new class_2846(class_2847.field_12968, data.getPos(), data.getDirection(), id);
         });
         if ((Boolean)this.doubleBreakConfig.getValue()) {
            NetworkManager.sendSequencedPacket((id) -> {
               return new class_2846(class_2847.field_12973, data.getPos(), data.getDirection(), id);
            });
         }

         data.setStarted();
      }

   }

   private void abortMining(HazelMine.MiningData data) {
      if (data.isStarted() && !data.getState().method_26215() && !data.isInstantRemine() && !(data.getBlockDamage() >= 1.0F)) {
         NetworkManager.sendSequencedPacket((id) -> {
            return new class_2846(class_2847.field_12971, data.getPos(), data.getDirection(), id);
         });
         InventoryManager.syncToClient();
      }

   }

   private boolean isSelfBlock(class_2338 target) {
      return (Boolean)this.safetyConfig.getValue() ? target.equals(PositionManager.getPlayerPos()) : false;
   }

   private void stopMining(HazelMine.MiningData data) {
      if (data.isStarted() && !data.getState().method_26215()) {
         boolean canSwap = data.getSlot() != -1;
         if (canSwap) {
            InventoryManager.setSlot(data.getSlot());
         }

         if ((Boolean)this.rotateConfig.getValue()) {
            float[] rotations = RotationManager.getRotationsTo(mc.field_1724.method_33571(), data.getPos().method_46558());
            RotationManager.setRotationSilent(rotations[0], rotations[1], true);
         }

         NetworkManager.sendSequencedPacket((id) -> {
            return new class_2846(class_2847.field_12973, data.getPos(), data.getDirection(), id);
         });
         this.lastBreak = System.currentTimeMillis();
         if (canSwap) {
            InventoryManager.syncToClient();
         }

         if ((Boolean)this.rotateConfig.getValue()) {
            RotationManager.setRotationSilentSync(true);
         }
      }

   }

   private boolean isDataPacketMine(HazelMine.MiningData data) {
      return this.miningQueue.size() == 2 && data == this.miningQueue.getLast();
   }

   public boolean isBlockDelayGrim() {
      return System.currentTimeMillis() - this.lastBreak <= 280L && (Boolean)this.grimConfig.getValue();
   }

   public static class MiningData {
      private final class_2338 pos;
      private final class_2350 direction;
      private float blockDamage;
      private boolean instantRemine;
      private boolean started;

      public MiningData(class_2338 pos, class_2350 direction) {
         this.pos = pos;
         this.direction = direction;
      }

      public boolean isInstantRemine() {
         return this.instantRemine;
      }

      public void setInstantRemine() {
         this.instantRemine = true;
      }

      public float damage(float dmg) {
         this.blockDamage += dmg;
         return this.blockDamage;
      }

      public void setDamage(float blockDamage) {
         this.blockDamage = blockDamage;
      }

      public void resetDamage() {
         this.instantRemine = false;
         this.blockDamage = 0.0F;
      }

      public class_2338 getPos() {
         return this.pos;
      }

      public class_2350 getDirection() {
         return this.direction;
      }

      public int getSlot() {
         return InventoryManager.getBestToolNoFallback(this.getState());
      }

      public class_2680 getState() {
         return Util.mc.field_1687.method_8320(this.pos);
      }

      public boolean isStarted() {
         return this.started;
      }

      public void setStarted() {
         this.started = true;
      }

      public float getBlockDamage() {
         return this.blockDamage;
      }
   }

   private static record AutoMineCalc(class_2338 pos, double entityDamage) implements Comparable<HazelMine.AutoMineCalc> {
      private AutoMineCalc(class_2338 pos, double entityDamage) {
         this.pos = pos;
         this.entityDamage = entityDamage;
      }

      public int compareTo(@NotNull HazelMine.AutoMineCalc o) {
         return Double.compare(-this.entityDamage(), -o.entityDamage());
      }

      public class_2338 pos() {
         return this.pos;
      }

      public double entityDamage() {
         return this.entityDamage;
      }
   }

   public static class AutoMiningData extends HazelMine.MiningData {
      public AutoMiningData(class_2338 pos, class_2350 direction) {
         super(pos, direction);
      }
   }
}
