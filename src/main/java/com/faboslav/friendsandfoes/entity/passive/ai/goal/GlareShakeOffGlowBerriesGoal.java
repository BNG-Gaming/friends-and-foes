package com.faboslav.friendsandfoes.entity.passive.ai.goal;

import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVines;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;

public class GlareShakeOffGlowBerriesGoal extends MoveToTargetPosGoal
{
	private final GlareEntity glare;
	private static final int COLLECTING_TIME = 40;
	private static final int RANGE = 8;
	private static final int MAX_Y_DIFFERENCE = 8;
	protected int timer;

	public GlareShakeOffGlowBerriesGoal(GlareEntity glare) {
		super(
			glare,
			glare.getMovementSpeed(),
			RANGE,
			MAX_Y_DIFFERENCE
		);

		this.glare = glare;
	}

	public double getDesiredSquaredDistanceToTarget() {
		return 4.0D;
	}

	public boolean shouldResetPath() {
		return this.tryingTime % 100 == 0;
	}

	protected boolean isTargetPos(WorldView world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos);
		return CaveVines.hasBerries(blockState);
	}

	protected void startMovingToTarget() {
		this.mob.getNavigation().startMovingTo(
			this.targetPos.getX() + 1.0D,
			this.targetPos.getY() - 1.0D,
			this.targetPos.getZ() + 1.0D,
			this.speed
		);
	}

	@Override
	public boolean canStart() {
		if (
			this.glare.isLeashed()
		) {
			return false;
		}

		return super.canStart();
	}

	@Override
	public void start() {
		this.timer = 0;
		super.start();
	}

	@Override
	public void tick() {
		if (this.hasReached()) {
			if (this.timer >= COLLECTING_TIME) {
				this.shakeOffGlowBerries();
			} else {
				++this.timer;
			}
		} else {
			if (this.glare.getRandom().nextFloat() < 0.05F) {
				this.glare.playAmbientSound();
			}
		}

		super.tick();
	}

	private void shakeOffGlowBerries() {
		if (this.glare.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) == false) {
			return;
		}

		BlockState blockState = this.glare.world.getBlockState(this.targetPos);

		if (CaveVines.hasBerries(blockState) == false) {
			return;
		}

		CaveVines.pickBerries(
			blockState,
			this.glare.getWorld(),
			this.targetPos
		);
	}
}
