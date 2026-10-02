package com.serilum.entityinformation;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import com.serilum.entityinformation.cmds.CommandIst;
import com.serilum.entityinformation.events.InformationEvent;
import com.serilum.entityinformation.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			CommandIst.register(dispatcher);
		});

		CollectiveEntityEvents.ON_LIVING_ATTACK.register((Level world, Entity entity, DamageSource damageSource, float damageAmount) -> {
			return InformationEvent.onEntityDamage(world, entity, damageSource, damageAmount);
		});
	}

	private static void setGlobalConstants() {

	}
}
