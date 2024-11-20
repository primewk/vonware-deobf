package me.spikestinger.Vonware.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import me.spikestinger.Vonware.Vonware;
import me.spikestinger.Vonware.features.Feature;
import me.spikestinger.Vonware.features.settings.Bind;
import me.spikestinger.Vonware.features.settings.EnumConverter;
import me.spikestinger.Vonware.features.settings.Setting;
import me.spikestinger.Vonware.util.traits.Jsonable;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
   private static final Path VONWARE_PATH = FabricLoader.getInstance().getGameDir().resolve("vonware");
   private static final Gson gson = (new GsonBuilder()).setLenient().setPrettyPrinting().create();
   private static final List<Jsonable> jsonables;

   public static void setValueFromJson(Feature feature, Setting setting, JsonElement element) {
      String var4 = setting.getType();
      byte var5 = -1;
      switch(var4.hashCode()) {
      case -1808118735:
         if (var4.equals("String")) {
            var5 = 4;
         }
         break;
      case -672261858:
         if (var4.equals("Integer")) {
            var5 = 3;
         }
         break;
      case 2070621:
         if (var4.equals("Bind")) {
            var5 = 5;
         }
         break;
      case 2165025:
         if (var4.equals("Enum")) {
            var5 = 6;
         }
         break;
      case 67973692:
         if (var4.equals("Float")) {
            var5 = 2;
         }
         break;
      case 1729365000:
         if (var4.equals("Boolean")) {
            var5 = 0;
         }
         break;
      case 2052876273:
         if (var4.equals("Double")) {
            var5 = 1;
         }
      }

      switch(var5) {
      case 0:
         setting.setValue(element.getAsBoolean());
         break;
      case 1:
         setting.setValue(element.getAsDouble());
         break;
      case 2:
         setting.setValue(element.getAsFloat());
         break;
      case 3:
         setting.setValue(element.getAsInt());
         break;
      case 4:
         String str = element.getAsString();
         setting.setValue(str.replace("_", " "));
         break;
      case 5:
         setting.setValue(new Bind(element.getAsInt()));
         break;
      case 6:
         try {
            EnumConverter converter = new EnumConverter(((Enum)setting.getValue()).getClass());
            Enum value = converter.doBackward(element);
            setting.setValue(value == null ? setting.getDefaultValue() : value);
         } catch (Exception var8) {
         }
         break;
      default:
         Logger var10000 = Vonware.LOGGER;
         String var10001 = feature.getName();
         var10000.error("Unknown Setting type for: " + var10001 + " : " + setting.getName());
      }

   }

   public static void load() {
      if (!VONWARE_PATH.toFile().exists()) {
         VONWARE_PATH.toFile().mkdirs();
      }

      Iterator var0 = jsonables.iterator();

      while(var0.hasNext()) {
         Jsonable jsonable = (Jsonable)var0.next();

         try {
            String read = Files.readString(VONWARE_PATH.resolve(jsonable.getFileName()));
            jsonable.fromJson(JsonParser.parseString(read));
         } catch (Throwable var3) {
            var3.printStackTrace();
         }
      }

   }

   public void save() {
      if (!VONWARE_PATH.toFile().exists()) {
         VONWARE_PATH.toFile().mkdirs();
      }

      Iterator var1 = jsonables.iterator();

      while(var1.hasNext()) {
         Jsonable jsonable = (Jsonable)var1.next();

         try {
            JsonElement json = jsonable.toJson();
            Files.writeString(VONWARE_PATH.resolve(jsonable.getFileName()), gson.toJson(json), new OpenOption[0]);
         } catch (Throwable var4) {
         }
      }

   }

   static {
      jsonables = List.of(Vonware.friendManager, Vonware.moduleManager, Vonware.commandManager);
   }
}
