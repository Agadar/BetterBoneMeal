package com.github.agadar.betterbonemeal;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = BetterBoneMeal.MODID, version = BetterBoneMeal.VERSION, name = BetterBoneMeal.NAME)
public class BetterBoneMeal
{
	@Instance(value = BetterBoneMeal.MODID)
	public static BetterBoneMeal instance;

	@SidedProxy(clientSide = BetterBoneMeal.CLIENTSIDE, serverSide = BetterBoneMeal.SERVERSIDE)
	public static CommonProxy proxy;

	// These are the references we use. These values are the same for our entire mod, so we only have 
	// to make them once here, and we can always access them.
	public static final String MODID = "betterbonemeal";
	public static final String VERSION = "1.0.0";
	public static final String NAME = "BetterBoneMeal";
	public static final String CLIENTSIDE = "com.github.agadar.betterbonemeal.ClientProxy";
	public static final String SERVERSIDE = "com.github.agadar.betterbonemeal.CommonProxy";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new HandlerOnBoneMeal());
	}
}
