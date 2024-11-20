package me.spikestinger.Vonware.features;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.traits.Util;

public class Feature implements Util {
   public List<Setting<?>> settings = new ArrayList();
   private String name;

   public Feature() {
   }

   public Feature(String name) {
      this.name = name;
   }

   public static boolean nullCheck() {
      return mc.field_1724 == null;
   }

   public static boolean fullNullCheck() {
      return mc.field_1724 == null || mc.field_1687 == null;
   }

   public String getName() {
      return this.name;
   }

   public List<Setting<?>> getSettings() {
      return this.settings;
   }

   public boolean hasSettings() {
      return !this.settings.isEmpty();
   }

   public boolean isEnabled() {
      return false;
   }

   public boolean isDisabled() {
      return !this.isEnabled();
   }

   public <T> Setting<T> register(Setting<T> setting) {
      setting.setFeature(this);
      this.settings.add(setting);
      return setting;
   }

   public void unregister(Setting<?> settingIn) {
      ArrayList<Setting<?>> removeList = new ArrayList();
      Iterator var3 = this.settings.iterator();

      while(var3.hasNext()) {
         Setting<?> setting = (Setting)var3.next();
         if (setting.equals(settingIn)) {
            removeList.add(setting);
         }
      }

      if (!removeList.isEmpty()) {
         this.settings.removeAll(removeList);
      }

   }

   public Setting<?> getSettingByName(String name) {
      Iterator var2 = this.settings.iterator();

      Setting setting;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         setting = (Setting)var2.next();
      } while(!setting.getName().equalsIgnoreCase(name));

      return setting;
   }

   public void reset() {
      Iterator var1 = this.settings.iterator();

      while(var1.hasNext()) {
         Setting<?> setting = (Setting)var1.next();
         setting.reset();
      }

   }

   public void clearSettings() {
      this.settings = new ArrayList();
   }
}
