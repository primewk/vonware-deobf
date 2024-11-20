package me.spikestinger.Vonware.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import me.spikestinger.Vonware.features.Feature;
import me.spikestinger.Vonware.features.commands.Command;
import me.spikestinger.Vonware.features.commands.impl.BindCommand;
import me.spikestinger.Vonware.features.commands.impl.CoordsCommand;
import me.spikestinger.Vonware.features.commands.impl.FriendCommand;
import me.spikestinger.Vonware.features.commands.impl.HelpCommand;
import me.spikestinger.Vonware.features.commands.impl.ModuleCommand;
import me.spikestinger.Vonware.features.commands.impl.PrefixCommand;
import me.spikestinger.Vonware.features.commands.impl.ToggleCommand;
import me.spikestinger.Vonware.util.traits.Jsonable;
import net.minecraft.class_124;

public class CommandManager extends Feature implements Jsonable {
   private final List<Command> commands = new ArrayList();
   private String clientMessage = "";
   private String prefix = ",";

   public CommandManager() {
      super("Command");
      this.commands.add(new ToggleCommand());
      this.commands.add(new CoordsCommand());
      this.commands.add(new BindCommand());
      this.commands.add(new FriendCommand());
      this.commands.add(new ModuleCommand());
      this.commands.add(new PrefixCommand());
      this.commands.add(new HelpCommand());
   }

   public static String[] removeElement(String[] input, int indexToDelete) {
      LinkedList<String> result = new LinkedList();

      for(int i = 0; i < input.length; ++i) {
         if (i != indexToDelete) {
            result.add(input[i]);
         }
      }

      return (String[])result.toArray(input);
   }

   private static String strip(String str, String key) {
      return str.startsWith(key) && str.endsWith(key) ? str.substring(key.length(), str.length() - key.length()) : str;
   }

   public void executeCommand(String command) {
      String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      String name = parts[0].substring(1);
      String[] args = removeElement(parts, 0);

      for(int i = 0; i < args.length; ++i) {
         if (args[i] != null) {
            args[i] = strip(args[i], "\"");
         }
      }

      Iterator var7 = this.commands.iterator();

      Command c;
      do {
         if (!var7.hasNext()) {
            Command.sendMessage(class_124.field_1080 + "Command not found, type 'help' for the commands list.");
            return;
         }

         c = (Command)var7.next();
      } while(!c.getName().equalsIgnoreCase(name));

      c.execute(parts);
   }

   public Command getCommandByName(String name) {
      Iterator var2 = this.commands.iterator();

      Command command;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         command = (Command)var2.next();
      } while(!command.getName().equals(name));

      return command;
   }

   public List<Command> getCommands() {
      return this.commands;
   }

   public String getClientMessage() {
      return this.clientMessage;
   }

   public void setClientMessage(String clientMessage) {
      this.clientMessage = clientMessage;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   public JsonElement toJson() {
      JsonObject object = new JsonObject();
      object.addProperty("prefix", this.prefix);
      return object;
   }

   public void fromJson(JsonElement element) {
      this.setPrefix(element.getAsJsonObject().get("prefix").getAsString());
   }

   public String getFileName() {
      return "commands.json";
   }
}
