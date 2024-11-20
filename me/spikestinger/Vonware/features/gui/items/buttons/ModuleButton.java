package me.spikestinger.Vonware.features.gui.items.buttons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.spikestinger.Vonware.features.gui.Component;
import me.spikestinger.Vonware.features.gui.items.Item;
import me.spikestinger.Vonware.features.modules.Module;
import me.spikestinger.Vonware.features.settings.Bind;
import me.spikestinger.Vonware.features.settings.Setting;
import net.minecraft.class_1109;
import net.minecraft.class_332;
import net.minecraft.class_3417;

public class ModuleButton extends Button {
   private final Module module;
   private List<Item> items = new ArrayList();
   private boolean subOpen;

   public ModuleButton(Module module) {
      super(module.getName());
      this.module = module;
      this.initSettings();
   }

   public void initSettings() {
      ArrayList<Item> newItems = new ArrayList();
      if (!this.module.getSettings().isEmpty()) {
         Iterator var2 = this.module.getSettings().iterator();

         label50:
         while(true) {
            while(true) {
               if (!var2.hasNext()) {
                  break label50;
               }

               Setting<?> setting = (Setting)var2.next();
               if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled")) {
                  newItems.add(new BooleanButton(setting));
               }

               if (setting.getValue() instanceof Bind && !setting.getName().equalsIgnoreCase("Keybind") && !this.module.getName().equalsIgnoreCase("Hud")) {
                  newItems.add(new BindButton(setting));
               }

               if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
                  newItems.add(new StringButton(setting));
               }

               if (setting.isNumberSetting() && setting.hasRestriction()) {
                  newItems.add(new Slider(setting));
               } else if (setting.isEnumSetting()) {
                  newItems.add(new EnumButton(setting));
               }
            }
         }
      }

      newItems.add(new BindButton(this.module.getSettingByName("Keybind")));
      this.items = newItems;
   }

   public void drawScreen(class_332 context, int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(context, mouseX, mouseY, partialTicks);
      if (!this.items.isEmpty() && this.subOpen) {
         float height = 1.0F;

         Item item;
         for(Iterator var6 = this.items.iterator(); var6.hasNext(); item.update()) {
            item = (Item)var6.next();
            int var10002 = Component.counter1[0]++;
            if (!item.isHidden()) {
               item.setLocation(this.x + 1.0F, this.y + (height += 15.0F));
               item.setHeight(15);
               item.setWidth(this.width - 9);
               item.drawScreen(context, mouseX, mouseY, partialTicks);
            }
         }
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (!this.items.isEmpty()) {
         if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            this.subOpen = !this.subOpen;
            mc.method_1483().method_4873(class_1109.method_47978(class_3417.field_15015, 1.0F));
         }

         if (this.subOpen) {
            Iterator var4 = this.items.iterator();

            while(var4.hasNext()) {
               Item item = (Item)var4.next();
               if (!item.isHidden()) {
                  item.mouseClicked(mouseX, mouseY, mouseButton);
               }
            }
         }
      }

   }

   public void onKeyTyped(char typedChar, int keyCode) {
      super.onKeyTyped(typedChar, keyCode);
      if (!this.items.isEmpty() && this.subOpen) {
         Iterator var3 = this.items.iterator();

         while(var3.hasNext()) {
            Item item = (Item)var3.next();
            if (!item.isHidden()) {
               item.onKeyTyped(typedChar, keyCode);
            }
         }
      }

   }

   public void onKeyPressed(int key) {
      super.onKeyPressed(key);
      if (!this.items.isEmpty() && this.subOpen) {
         Iterator var2 = this.items.iterator();

         while(var2.hasNext()) {
            Item item = (Item)var2.next();
            if (!item.isHidden()) {
               item.onKeyPressed(key);
            }
         }
      }

   }

   public int getHeight() {
      if (this.subOpen) {
         int height = 14;
         Iterator var2 = this.items.iterator();

         while(var2.hasNext()) {
            Item item = (Item)var2.next();
            if (!item.isHidden()) {
               height += item.getHeight() + 1;
            }
         }

         return height + 2;
      } else {
         return 14;
      }
   }

   public Module getModule() {
      return this.module;
   }

   public void toggle() {
      this.module.toggle();
   }

   public boolean getState() {
      return this.module.isEnabled();
   }
}
