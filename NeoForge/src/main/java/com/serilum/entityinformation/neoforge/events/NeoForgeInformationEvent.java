package com.serilum.entityinformation.neoforge.events;

import com.serilum.entityinformation.cmds.CommandIst;
import com.serilum.entityinformation.events.InformationEvent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

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
}
