package com.faboslav.friendsandfoes.world.gen.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructureGeneratorFactory.Context;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

public class IllusionerShackFeature extends JigsawFeature
{
	public IllusionerShackFeature(Codec<StructurePoolFeatureConfig> configCodec) {
		super(configCodec, 0, true, true, IllusionerShackFeature::canGenerate);
	}

	private static boolean canGenerate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {

		if (
			FriendsAndFoes.CONFIG.generateIllusionerShackStructure == false
			|| isVillageNearby(context) == true
			|| isSuitableChunk(context) == false
		) {
			return false;
		}

		return true;
	}

	private static boolean isVillageNearby(Context<StructurePoolFeatureConfig> context) {
		int chunkPosX = context.chunkPos().x;
		int chunkPosZ = context.chunkPos().z;

		return context.chunkGenerator().method_41053(
			StructureSetKeys.VILLAGES,
			context.seed(),
			chunkPosX, chunkPosZ, 10
		);
	}

	private static boolean isSuitableChunk(Context<StructurePoolFeatureConfig> context) {
		int i = context.chunkPos().x >> 4;
		int j = context.chunkPos().z >> 4;

		ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(0L));
		chunkRandom.setSeed((long) (i ^ j << 4) ^ context.seed());
		chunkRandom.nextInt();

		if (chunkRandom.nextInt(5) != 0) {
			return false;
		}

		return true;
	}
}