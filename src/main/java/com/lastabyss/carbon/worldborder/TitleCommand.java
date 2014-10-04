package com.lastabyss.carbon.worldborder;

import net.minecraft.server.v1_7_R4.CommandAbstract;
import net.minecraft.server.v1_7_R4.ICommandListener;

/**
 *
 * @author Navid
 */
public class TitleCommand extends CommandAbstract {

    @Override
    public String getCommand() {
        return "title";
    }

    @Override
    public int a() {
        return 2;
    }
    
    @Override
    public String c(ICommandListener il) {
        return "commands.title.usage";
    }

    @Override
    public void execute(ICommandListener il, String[] strings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    /**
    @Override
    public void execute(ICommandListener listener, String[] args) {
        if (args.length < 2)
        {
            throw new ExceptionUsage("commands.title.usage", new Object[0]);
        }
        else
        {
            if (args.length < 3)
            {
                if ("title".equals(args[1]) || "subtitle".equals(args[1]))
                {
                    throw new ExceptionUsage("commands.title.usage.title", new Object[0]);
                }

                if ("times".equals(args[1]))
                {
                    throw new ExceptionUsage("commands.title.usage.times", new Object[0]);
                }
            }

            EntityPlayerMP var3 = getPlayer(listener.get, args[0]);
            S45PacketTitle.Type var4 = S45PacketTitle.Type.func_179969_a(args[1]);

            if (var4 != S45PacketTitle.Type.CLEAR && var4 != S45PacketTitle.Type.RESET)
            {
                if (var4 == S45PacketTitle.Type.TIMES)
                {
                    if (args.length != 5)
                    {
                        throw new ExceptionUsage("commands.title.usage", new Object[0]);
                    }
                    else
                    {
                        int var11 = parseInt(args[2]);
                        int var12 = parseInt(args[3]);
                        int var13 = parseInt(args[4]);
                        S45PacketTitle var14 = new S45PacketTitle(var11, var12, var13);
                        var3.playerNetServerHandler.sendPacket(var14);
                        notifyOperators(sender, this, "commands.title.success", new Object[0]);
                    }
                }
                else if (args.length < 3)
                {
                    throw new ExceptionUsage("commands.title.usage", new Object[0]);
                }
                else
                {
                    String var10 = func_180529_a(args, 2);
                    IChatComponent var6;

                    try
                    {
                        var6 = IChatComponent.Serializer.jsonToComponent(var10);
                    }
                    catch (JsonParseException var9)
                    {
                        Throwable var8 = ExceptionUtils.getRootCause(var9);
                        throw new SyntaxErrorException("commands.tellraw.jsonException", new Object[] {var8 == null ? "" : var8.getMessage()});
                    }

                    S45PacketTitle var7 = new S45PacketTitle(var4, ChatComponentProcessor.func_179985_a(sender, var6, var3));
                    var3.playerNetServerHandler.sendPacket(var7);
                    notifyOperators(sender, this, "commands.title.success", new Object[0]);
                }
            }
            else if (args.length != 2)
            {
                throw new ExceptionUsage("commands.title.usage", new Object[0]);
            }
            else
            {
                S45PacketTitle var5 = new S45PacketTitle(var4, (IChatComponent)null);
                var3.playerNetServerHandler.sendPacket(var5);
                notifyOperators(sender, this, "commands.title.success", new Object[0]);
            }
        }
    }

* **/
    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
