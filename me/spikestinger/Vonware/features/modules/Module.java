package me.spikestinger.Vonware.features.modules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.event.impl.AttackBlockEvent;
import me.spikestinger.Vonware.event.impl.ClientEvent;
import me.spikestinger.Vonware.event.impl.PacketEvent;
import me.spikestinger.Vonware.event.impl.Render2DEvent;
import me.spikestinger.Vonware.event.impl.Render3DEvent;
import me.spikestinger.Vonware.features.Feature;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.settings.Bind;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.manager.ConfigManager;
import me.spikestinger.Vonware.util.traits.Jsonable;
import net.minecraft.class_124;
import net.minecraft.class_2561;

public class Module extends Feature implements Jsonable {
   private final String description;
   private final Module.Category category;
   public Setting<Boolean> enabled = this.register(new Setting("Enabled", false));
   public Setting<Bind> bind = this.register(new Setting("Keybind", new Bind(-1)));
   public Setting<String> displayName;
   public boolean hasListener;
   public boolean alwaysListening;
   public boolean hidden;
   public List<String> silentModules = new ArrayList();

   public Module(String name, String description, Module.Category category, boolean hasListener, boolean hidden, boolean alwaysListening) {
      super(name);
      this.displayName = this.register(new Setting("DisplayName", name));
      this.description = description;
      this.category = category;
      this.hasListener = hasListener;
      this.hidden = hidden;
      this.alwaysListening = alwaysListening;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void onToggle() {
   }

   public void onLoad() {
   }

   public void onTick() {
   }

   public void onAttackBlock(AttackBlockEvent event) {
   }

   public void onPacketOutbound(PacketEvent.Outbound event) {
   }

   public void onUpdate() {
   }

   public void onRender2D(Render2DEvent event) {
   }

   public void onRender3D(Render3DEvent event) {
   }

   public void onUnload() {
   }

   public String getDisplayInfo() {
      return null;
   }

   public boolean isOn() {
      return (Boolean)this.enabled.getValue();
   }

   public boolean isOff() {
      return !(Boolean)this.enabled.getValue();
   }

   public void setEnabled(boolean enabled) {
      if (enabled) {
         this.enable();
      } else {
         this.disable();
      }

   }

   public void enable() {
      this.enabled.setValue(true);
      this.onToggle();
      this.onEnable();
      if (!this.getName().equals("ClickGui")) {
         String var10000 = Vonware.commandManager.getClientMessage();
         String message = var10000 + class_124.field_1065 + this.getDisplayName() + class_124.field_1080 + " has been" + class_124.field_1060 + class_124.field_1067 + " enabled!";
         class_2561 text = class_2561.method_43470(message);
         mc.field_1705.method_1743().method_1812(text);
      }

      if (this.isOn() && this.hasListener && !this.alwaysListening) {
         EVENT_BUS.register(this);
      }

   }

   public void disable() {
      if (this.hasListener && !this.alwaysListening) {
         EVENT_BUS.unregister(this);
      }

      if (!this.getName().equals("ClickGui")) {
         String var10000 = Vonware.commandManager.getClientMessage();
         String message = var10000 + class_124.field_1065 + this.getDisplayName() + class_124.field_1080 + " has been" + class_124.field_1061 + class_124.field_1067 + " disabled!";
         class_2561 text = class_2561.method_43470(message);
         mc.field_1705.method_1743().method_1812(text);
      }

      this.enabled.setValue(false);
      this.onToggle();
      this.onDisable();
   }

   public void toggle() {
      ClientEvent event = new ClientEvent(!this.isEnabled() ? 1 : 0, this);
      EVENT_BUS.post(event);
      if (!event.isCancelled()) {
         this.setEnabled(!this.isEnabled());
      }

   }

   public String getDisplayName() {
      return (String)this.displayName.getValue();
   }

   public void setDisplayName(String name) {
      Module module = Vonware.moduleManager.getModuleByDisplayName(name);
      Module originalModule = Vonware.moduleManager.getModuleByName(name);
      if (module == null && originalModule == null) {
         String var10000 = this.getDisplayName();
         Command.sendMessage(var10000 + ", name: " + this.getName() + ", has been renamed to: " + name);
         this.displayName.setValue(name);
      } else {
         Command.sendMessage(class_124.field_1061 + "A module of this name already exists.");
      }
   }

   public boolean isEnabled() {
      return this.isOn();
   }

   public String getDescription() {
      return this.description;
   }

   public Module.Category getCategory() {
      return this.category;
   }

   public String getInfo() {
      return null;
   }

   public Bind getBind() {
      return (Bind)this.bind.getValue();
   }

   public void setBind(int key) {
      this.bind.setValue(new Bind(key));
   }

   public boolean listening() {
      return this.hasListener && this.isOn() || this.alwaysListening;
   }

   public String getFullArrayString() {
      String var10000 = this.getDisplayName();
      return var10000 + class_124.field_1080 + (this.getDisplayInfo() != null ? " [" + class_124.field_1068 + this.getDisplayInfo() + class_124.field_1080 + "]" : "");
   }

   public JsonElement toJson() {
      JsonObject object = new JsonObject();
      Iterator var2 = this.getSettings().iterator();

      while(var2.hasNext()) {
         Setting setting = (Setting)var2.next();

         try {
            Object var5 = setting.getValue();
            if (var5 instanceof Bind) {
               Bind bind = (Bind)var5;
               object.addProperty(setting.getName(), bind.getKey());
            } else {
               object.addProperty(setting.getName(), setting.getValueAsString());
            }
         } catch (Throwable var6) {
         }
      }

      return object;
   }

   public void fromJson(JsonElement element) {
      JsonObject object = element.getAsJsonObject();
      String enabled = object.get("Enabled").getAsString();
      if (Boolean.parseBoolean(enabled)) {
         this.toggle();
      }

      Iterator var4 = this.getSettings().iterator();

      while(var4.hasNext()) {
         Setting setting = (Setting)var4.next();

         try {
            ConfigManager.setValueFromJson(this, setting, object.get(setting.getName()));
         } catch (Throwable var7) {
         }
      }

   }

   public static enum Category {
      COMBAT("Combat"),
      EXPLOIT("Exploit"),
      MISC("Misc"),
      PLAYER("Player"),
      MOVEMENT("Movement"),
      RENDER("Render"),
      CLIENT("Client");

      private final String name;

      private Category(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      // $FF: synthetic method
      private static Module.Category[] $values() {
         return new Module.Category[]{COMBAT, EXPLOIT, MISC, PLAYER, MOVEMENT, RENDER, CLIENT};
      }
   }
}
