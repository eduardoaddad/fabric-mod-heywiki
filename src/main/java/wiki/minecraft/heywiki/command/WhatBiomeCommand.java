package wiki.minecraft.heywiki.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import wiki.minecraft.heywiki.WikiPage;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class WhatBiomeCommand {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @SuppressWarnings("UnusedReturnValue")
    public static LiteralCommandNode<FabricClientCommandSource> register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        return dispatcher.register(literal("whatbiome")
                .executes(ctx -> {
                    if (CLIENT.player == null || CLIENT.world == null) return 1;

                    var block = CLIENT.player.getBlockPos();
                    var biomeKey = CLIENT.world.getBiome(block).getKey();
                    if (biomeKey.isEmpty()) return 1;
                    String translationKey = biomeKey.get().getValue().toTranslationKey("biome");
                    WikiPage.fromTranslationKey(translationKey).openInBrowser(true);
                    return 0;
                }));
    }
}