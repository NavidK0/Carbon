package com.lastabyss.carbon.ai;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.IEntitySelector;

public class ImprovedEntitySelector implements IEntitySelector {

	private LogicOperaton logicOperation;
	private IEntitySelector[] selectors;

	private ImprovedEntitySelector(LogicOperaton logicOperation, IEntitySelector[] selectors) {
		this.logicOperation = logicOperation;
		this.selectors = selectors;
	}

	@Override
	public boolean a(Entity entity) {
		boolean result = selectors[0].a(entity);
		if (selectors.length > 1) {
			for (int i = 1; i < selectors.length; i++) {
				switch (logicOperation) {
					case AND: {
						result &= selectors[i].a(entity);
						continue;
					}
					case OR: {
						result |= selectors[i].a(entity);
						continue;
					}
					case XOR: {
						result ^= selectors[i].a(entity);
						continue;
					}
				}
			}
		}
		return result;
	}

	public static ImprovedEntitySelector and(IEntitySelector... selectors) {
		return create(LogicOperaton.AND, selectors);
	}

	public static ImprovedEntitySelector or(IEntitySelector... selectors) {
		return create(LogicOperaton.OR, selectors);
	}

	public static ImprovedEntitySelector xor(IEntitySelector... selectors) {
		return create(LogicOperaton.XOR, selectors);
	}

	private static ImprovedEntitySelector create(LogicOperaton logicOperation, IEntitySelector[] selectors) {
		if (selectors.length == 0) {
			throw new RuntimeException("Can't create empty ImprovedEntitySelector");
		}
		return new ImprovedEntitySelector(logicOperation, selectors);
	}

	private static enum LogicOperaton {
		AND, OR, XOR
	}

}
