package com.natamus.entityinformation.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

public class CommandIst {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("ist")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player && iCommandSender.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
			.executes((command) -> {
				processInformationstick(command);
				return 1;
			})
		);
		dispatcher.register(Commands.literal("informationstick")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player && iCommandSender.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
			.executes((command) -> {
				processInformationstick(command);
				return 1;
			})
		);
	}
	
	public static void processInformationstick(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
		CommandSourceStack source = command.getSource();
		Player player = source.getPlayerOrException();
		
		ItemStack informationstick = new ItemStack(Items.STICK, 1);
		informationstick.set(DataComponents.CUSTOM_NAME, Component.translatable("collective.entityinformation.gui.informationstick").withStyle(ChatFormatting.BLUE));

		CompoundTag tag = new CompoundTag();
		tag.putBoolean("informationstick", true);
		informationstick.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

		player.addItem(informationstick);
		MessageFunctions.sendTranslatableMessage(player, "collective.entityinformation.message.giveninformationstick", ChatFormatting.BLUE);
	}
}