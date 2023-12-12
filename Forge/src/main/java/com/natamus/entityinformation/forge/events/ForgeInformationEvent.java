package com.natamus.entityinformation.forge.events;

import com.natamus.entityinformation.cmds.CommandIst;
import com.natamus.entityinformation.events.InformationEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeInformationEvent {
    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent e) {
    	CommandIst.register(e.getDispatcher());
    }

	@SubscribeEvent
	public void onEntityDamage(LivingAttackEvent e) {
		LivingEntity livingEntity = e.getEntityLiving();
		if (!InformationEvent.onEntityDamage(livingEntity.level, livingEntity, e.getSource(), e.getAmount())) {
			e.setCanceled(true);
		}
	}
}
