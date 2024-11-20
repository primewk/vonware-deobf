package me.spikestinger.Vonware.features.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.Feature;
import me.spikestinger.Vonware.features.gui.items.Item;
import me.spikestinger.Vonware.features.gui.items.buttons.ModuleButton;
import me.spikestinger.Vonware.features.modules.Module;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class VonwareGui extends class_437 {
   private static VonwareGui vonwareGui;
   private static VonwareGui INSTANCE = new VonwareGui();
   private final ArrayList<Component> components = new ArrayList();

   public VonwareGui() {
      super(class_2561.method_43470("Vonware"));
      this.setInstance();
      this.load();
   }

   public static VonwareGui getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new VonwareGui();
      }

      return INSTANCE;
   }

   public static VonwareGui getClickGui() {
      return getInstance();
   }

   private void setInstance() {
      INSTANCE = this;
   }

   private void load() {
      int x = -84;
      Iterator var2 = Vonware.moduleManager.getCategories().iterator();

      while(var2.hasNext()) {
         final Module.Category category = (Module.Category)var2.next();
         ArrayList var10000 = this.components;
         String var10004 = category.getName();
         x += 90;
         var10000.add(new Component(var10004, x, 4, true) {
            public void setupItems() {
               counter1 = new int[]{1};
               Vonware.moduleManager.getModulesByCategory(category).forEach((module) -> {
                  if (!module.hidden) {
                     this.addButton(new ModuleButton(module));
                  }

               });
            }
         });
      }

      this.components.forEach((components) -> {
         components.getItems().sort(Comparator.comparing(Feature::getName));
      });
   }

   public void updateModule(Module module) {
      Iterator var2 = this.components.iterator();

      while(var2.hasNext()) {
         Component component = (Component)var2.next();
         Iterator var4 = component.getItems().iterator();

         while(var4.hasNext()) {
            Item item = (Item)var4.next();
            if (item instanceof ModuleButton) {
               ModuleButton button = (ModuleButton)item;
               Module mod = button.getModule();
               if (module != null && module.equals(mod)) {
                  button.initSettings();
               }
            }
         }
      }

   }

   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
      Item.context = context;
      context.method_25294(0, 0, context.method_51421(), context.method_51443(), (new Color(0, 0, 0, 120)).hashCode());
      this.components.forEach((components) -> {
         components.drawScreen(context, mouseX, mouseY, delta);
      });
   }

   public boolean method_25402(double mouseX, double mouseY, int clickedButton) {
      this.components.forEach((components) -> {
         components.mouseClicked((int)mouseX, (int)mouseY, clickedButton);
      });
      return super.method_25402(mouseX, mouseY, clickedButton);
   }

   public boolean method_25406(double mouseX, double mouseY, int releaseButton) {
      this.components.forEach((components) -> {
         components.mouseReleased((int)mouseX, (int)mouseY, releaseButton);
      });
      return super.method_25406(mouseX, mouseY, releaseButton);
   }

   public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (verticalAmount < 0.0D) {
         this.components.forEach((component) -> {
            component.setY(component.getY() - 10);
         });
      } else if (verticalAmount > 0.0D) {
         this.components.forEach((component) -> {
            component.setY(component.getY() + 10);
         });
      }

      return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      this.components.forEach((component) -> {
         component.onKeyPressed(keyCode);
      });
      return super.method_25404(keyCode, scanCode, modifiers);
   }

   public boolean method_25400(char chr, int modifiers) {
      this.components.forEach((component) -> {
         component.onKeyTyped(chr, modifiers);
      });
      return super.method_25400(chr, modifiers);
   }

   public boolean method_25421() {
      return false;
   }

   public final ArrayList<Component> getComponents() {
      return this.components;
   }

   public int getTextOffset() {
      return -6;
   }

   public Component getComponentByName(String name) {
      Iterator var2 = this.components.iterator();

      Component component;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         component = (Component)var2.next();
      } while(!component.getName().equalsIgnoreCase(name));

      return component;
   }
}
