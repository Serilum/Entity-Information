package com.natamus.entityinformation.events;

import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class InformationEvent {
	public static boolean onEntityDamage(Level world, Entity entity, DamageSource damageSource, float damageAmount) {
		Entity source = damageSource.getEntity();
		if (source == null) {
			return true;
		}
		
		if (world.isClientSide) {
			return true;
		}
		
		if (!(source instanceof Player)) {
			return true;
		}
		
		Player player = (Player)source;

		ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
		if (!mainhand.getItem().equals(Items.STICK)) {
			return true;
		}
		
		if (!mainhand.getHoverName().getString().equals(ChatFormatting.BLUE + "The Information Stick")) {
			return true;
		}

		String name = "Name: " + entity.getName().getString();
		String entityName = "Entity" + name;
		try {
			entityName = "EntityName: " + entity.toString().split("\\[")[0];
		}
		catch (Exception ignored) {}

		String entityId = "EntityId: " + entity.getId();
		String UUID = "UUID: " + entity.getUUID();
		String position = "Position: " + entity.blockPosition().toString().replace("BlockPos{", "").replace("}", "");
		String isSilent = "isSilent: " + entity.isSilent();
		String ticksExisted = "ticksExisted: " + entity.tickCount;

		MessageFunctions.sendMessage(player, "---- Entity Information:", ChatFormatting.BLUE, true);
		MessageFunctions.sendMessage(player, name, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, entityName, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, entityId, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, UUID, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, position, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, isSilent, ChatFormatting.BLUE);
		MessageFunctions.sendMessage(player, ticksExisted, ChatFormatting.BLUE);
		
		return false;
	}
}
