package com.bcstatistic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MyCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private Logger logger;
    public  static com.bcstatistic.MyCommand instance;
    private static ConfigFile mangerConInstance;
    public List<String> subCommands;
    public MyCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        subCommands = new ArrayList<>();
        // 添加所有的二级指令
        subCommands.add("help");
        subCommands.add("list");
        subCommands.add("reload");
        logger = plugin.getLogger();
        mangerConInstance = ConfigFile.getInstance(plugin);
    }

    public static com.bcstatistic.MyCommand getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new com.bcstatistic.MyCommand(plugin);

        }
        return instance;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查命令参数是否足够
        if((sender instanceof Player)&&sender.isOp()){
            if(args.length<1||args.length>1){
                sender.sendMessage("§b§l[§6§lBcStatistic§b§l] §cparameter error,enter '/bcstatistic help' for help");

            }
            else if(args.length==1){
                if(args[0].equals("help")){
                    sender.sendMessage("§b=============§6[BcStatistic] Help§b=============");
                    sender.sendMessage("§7-§a/bcstatistic help §eview help");
                    sender.sendMessage("§7-§a/bcstatistic list §eList the currently successfully loaded variables");
                    sender.sendMessage("§7-§a/bcstatistic reload §eOverloading configuration files");
                    sender.sendMessage("§b===========================================");
                }
                else if(args[0].equals("list")){
                    sender.sendMessage("§b=======§6[BcStatistic] Custom Placeholder List§b=======");
                    for (String holder : mangerConInstance.map.keySet()) {
                        sender.sendMessage("§3➝ §a"+String.format("%%bcstatistic_%s%%",holder));
                    }
                }
                else if(args[0].equals("reload")){
                    reloadPlugin((Player)sender);
                }
                else {
                    sender.sendMessage("§b§l[§6§lBcStatistic§b§l] §cparameter error,enter '/bcstatistic help' for help");
                }
            }
        }



        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            String input = args[0].toLowerCase();

            // 检查输入是否与二级指令匹配，如果匹配则添加到补全列表中
            for (String subCommand : subCommands) {
                if (subCommand.startsWith(input)) {
                    completions.add(subCommand);
                }
            }

            return completions;
        }

        return null;
    }


    private void reloadPlugin(Player player) {
        mangerConInstance.loadConfig();
        player.sendMessage(String.format("§b§l[§6§lBcStatistic§b§l] §aPlugin overloaded, success %d, failure %d",mangerConInstance.map.size(),mangerConInstance.failed));
    }
}
