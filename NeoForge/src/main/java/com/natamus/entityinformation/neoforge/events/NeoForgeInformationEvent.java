package com.natamus.entityinformation.neoforge.events;

import com.natamus.entityinformation.cmds.CommandIst;
import com.natamus.entityinformation.events.InformationEvent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class NeoForgeInformationEvent {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent e) {
		CommandIst.register(e.getDispatcher());
	}

	@SubscribeEvent
	public static void onEntityDamage(LivingIncomingDamageEvent e) {
		LivingEntity livingEntity = e.getEntity();
		if (!InformationEvent.onEntityDamage(livingEntity.level(), livingEntity, e.getSource(), e.getAmount())) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem e) {
		InformationEvent.onStickUse(e.getEntity());
	}
}
