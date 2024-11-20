package me.spikestinger.Vonware.manager;

import com.google.common.eventbus.EventBus;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.spikestinger.Vonware.event.impl.AttackBlockEvent;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.event.impl.Render2DEvent;
import me.spikestinger.Vonware.event.impl.Render3DEvent;
import me.spikestinger.Vonware.features.Feature;
import me.spikestinger.Vonware.features.gui.VonwareGui;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.modules.client.ClickGui;
import me.spikestinger.Vonware.features.modules.client.FutureFriendSync;
import me.spikestinger.Vonware.features.modules.client.Hud;
import me.spikestinger.Vonware.features.modules.combat.Criticals;
import me.spikestinger.Vonware.features.modules.combat.ElytraSwap;
import me.spikestinger.Vonware.features.modules.combat.FutureMinDamage;
import me.spikestinger.Vonware.features.modules.combat.HazelMine;
import me.spikestinger.Vonware.features.modules.exploit.GrimDisabler;
import me.spikestinger.Vonware.features.modules.misc.AutoFirework;
import me.spikestinger.Vonware.features.modules.misc.MiddleClick;
import me.spikestinger.Vonware.features.modules.misc.NameHider;
import me.spikestinger.Vonware.features.modules.misc.SmartDisconnect;
import me.spikestinger.Vonware.features.modules.movement.NoJumpDelay;
import me.spikestinger.Vonware.features.modules.movement.PearlPhase;
import me.spikestinger.Vonware.features.modules.movement.ReverseStep;
import me.spikestinger.Vonware.features.modules.movement.Step;
import me.spikestinger.Vonware.features.modules.movement.Velocity;
import me.spikestinger.Vonware.features.modules.player.FakePlayer;
import me.spikestinger.Vonware.features.modules.render.KillEffects;
import me.spikestinger.Vonware.features.modules.render.PhaseESP;
import me.spikestinger.Vonware.features.modules.render.PlayerAnimation;
import me.spikestinger.Vonware.util.traits.Jsonable;
import me.spikestinger.Vonware.util.traits.Util;

public class ModuleManager implements Jsonable, Util {
   public List<Module> modules = new ArrayList();
   public List<Module> sortedModules = new ArrayList();
   public List<String> sortedModulesABC = new ArrayList();

   public void init() {
      this.modules.add(new ClickGui());
      this.modules.add(new FutureFriendSync());
      this.modules.add(new Hud());
      this.modules.add(new Criticals());
      this.modules.add(new HazelMine());
      this.modules.add(new FutureMinDamage());
      this.modules.add(new ElytraSwap());
      this.modules.add(new AutoFirework());
      this.modules.add(new GrimDisabler());
      this.modules.add(new MiddleClick());
      this.modules.add(new NameHider());
      this.modules.add(new FakePlayer());
      this.modules.add(new SmartDisconnect());
      this.modules.add(new NoJumpDelay());
      this.modules.add(new ReverseStep());
      this.modules.add(new Step());
      this.modules.add(new Velocity());
      this.modules.add(new PearlPhase());
      this.modules.add(new KillEffects());
      this.modules.add(new PhaseESP());
      this.modules.add(new PlayerAnimation());
   }

   public Module getModuleByName(String name) {
      Iterator var2 = this.modules.iterator();

      Module module;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         module = (Module)var2.next();
      } while(!module.getName().equalsIgnoreCase(name));

      return module;
   }

   public <T extends Module> T getModuleByClass(Class<T> clazz) {
      Iterator var2 = this.modules.iterator();

      Module module;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         module = (Module)var2.next();
      } while(!clazz.isInstance(module));

      return module;
   }

   public void enableModule(Class<Module> clazz) {
      Module module = this.getModuleByClass(clazz);
      if (module != null) {
         module.enable();
      }

   }

   public void disableModule(Class<Module> clazz) {
      Module module = this.getModuleByClass(clazz);
      if (module != null) {
         module.disable();
      }

   }

   public void enableModule(String name) {
      Module module = this.getModuleByName(name);
      if (module != null) {
         module.enable();
      }

   }

   public void disableModule(String name) {
      Module module = this.getModuleByName(name);
      if (module != null) {
         module.disable();
      }

   }

   public boolean isModuleEnabled(Class<Module> clazz) {
      Module module = this.getModuleByClass(clazz);
      return module != null && module.isOn();
   }

   public Module getModuleByDisplayName(String displayName) {
      Iterator var2 = this.modules.iterator();

      Module module;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         module = (Module)var2.next();
      } while(!module.getDisplayName().equalsIgnoreCase(displayName));

      return module;
   }

   public ArrayList<Module> getEnabledModules() {
      ArrayList<Module> enabledModules = new ArrayList();
      Iterator var2 = this.modules.iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         if (module.isEnabled()) {
            enabledModules.add(module);
         }
      }

      return enabledModules;
   }

   public ArrayList<String> getEnabledModulesName() {
      ArrayList<String> enabledModules = new ArrayList();
      Iterator var2 = this.modules.iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         if (module.isEnabled()) {
            enabledModules.add(module.getFullArrayString());
         }
      }

      return enabledModules;
   }

   public ArrayList<Module> getModulesByCategory(Module.Category category) {
      ArrayList<Module> modulesCategory = new ArrayList();
      this.modules.forEach((module) -> {
         if (module.getCategory() == category) {
            modulesCategory.add(module);
         }

      });
      return modulesCategory;
   }

   public List<Module.Category> getCategories() {
      return Arrays.asList(Module.Category.values());
   }

   public void onLoad() {
      Stream var10000 = this.modules.stream().filter(Module::listening);
      EventBus var10001 = EVENT_BUS;
      Objects.requireNonNull(var10001);
      var10000.forEach(var10001::register);
      this.modules.forEach(Module::onLoad);
   }

   public void onUpdate() {
      this.modules.stream().filter(Feature::isEnabled).forEach(Module::onUpdate);
   }

   public void onTick() {
      this.modules.stream().filter(Feature::isEnabled).forEach(Module::onTick);
   }

   public void AttackBlockEvent(AttackBlockEvent event) {
      this.modules.stream().filter(Feature::isEnabled).forEach((module) -> {
         module.onAttackBlock(event);
      });
   }

   public void Outbound(PacketEvent event) {
      this.modules.stream().filter(Feature::isEnabled).forEach((module) -> {
         module.onPacketOutbound((PacketEvent.Outbound)event);
      });
   }

   public void onRender2D(Render2DEvent event) {
      this.modules.stream().filter(Feature::isEnabled).forEach((module) -> {
         module.onRender2D(event);
      });
   }

   public void onRender3D(Render3DEvent event) {
      this.modules.stream().filter(Feature::isEnabled).forEach((module) -> {
         module.onRender3D(event);
      });
   }

   public void sortModules(boolean reverse) {
      this.sortedModules = (List)this.getEnabledModules().stream().sorted(Comparator.comparing((module) -> {
         return mc.field_1772.method_1727(module.getFullArrayString()) * (reverse ? -1 : 1);
      })).collect(Collectors.toList());
   }

   public void sortModulesABC() {
      this.sortedModulesABC = new ArrayList(this.getEnabledModulesName());
      this.sortedModulesABC.sort(String.CASE_INSENSITIVE_ORDER);
   }

   public void onUnload() {
      List var10000 = this.modules;
      EventBus var10001 = EVENT_BUS;
      Objects.requireNonNull(var10001);
      var10000.forEach(var10001::unregister);
      this.modules.forEach(Module::onUnload);
   }

   public void onUnloadPost() {
      Iterator var1 = this.modules.iterator();

      while(var1.hasNext()) {
         Module module = (Module)var1.next();
         module.enabled.setValue(false);
      }

   }

   public void onKeyPressed(int eventKey) {
      if (eventKey > 0 && !(mc.field_1755 instanceof VonwareGui)) {
         this.modules.forEach((module) -> {
            if (module.getBind().getKey() == eventKey) {
               module.toggle();
            }

         });
      }
   }

   public JsonElement toJson() {
      JsonObject object = new JsonObject();
      Iterator var2 = this.modules.iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         object.add(module.getName(), module.toJson());
      }

      return object;
   }

   public void fromJson(JsonElement element) {
      Iterator var2 = this.modules.iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         module.fromJson(element.getAsJsonObject().get(module.getName()));
      }

   }

   public String getFileName() {
      return "modules.json";
   }
}
