package com.lastabyss.carbon.worldborder;

public interface WorldBorderChangeListener {
	void onSizeSet(WorldBorder worldborder, double size);

	void onSizeChange(WorldBorder worldborder, double oldRadius,
			double newRadius, long time);

	void onSetCenter(WorldBorder worldborder, double x, double z);

	void onSetWarningTime(WorldBorder worldborder, int time);

	void onSetWarningBlocks(WorldBorder worldborder, int blocks);

	void onSetDamageAmount(WorldBorder worldborder, double amount);

	void onSetDamageBuffer(WorldBorder worldborder, double buffer);
}