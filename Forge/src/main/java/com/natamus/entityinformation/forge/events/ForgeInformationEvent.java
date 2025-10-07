package com.natamus.entityinformation.forge.events;

import com.natamus.entityinformation.cmds.CommandIst;
import com.natamus.entityinformation.events.InformationEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeInformationEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeInformationEvent.class);
	}

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent e) {
    	CommandIst.register(e.getDispatcher());
    }

	@SubscribeEvent
	public static boolean onEntityDamage(LivingAttackEvent e) {
		LivingEntity livingEntity = e.getEntity();
		if (!InformationEvent.onEntityDamage(livingEntity.level(), livingEntity, e.getSource(), e.getAmount())) {
			return true;
		}
		return false;
	}
}
