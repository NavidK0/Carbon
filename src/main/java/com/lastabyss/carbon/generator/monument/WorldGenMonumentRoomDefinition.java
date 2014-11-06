package com.lastabyss.carbon.generator.monument;

import com.lastabyss.carbon.utils.nmsclasses.BlockFace;

class WorldGenMonumentRoomDefinition {

	int id;
	WorldGenMonumentRoomDefinition[] b = new WorldGenMonumentRoomDefinition[6];
	boolean[] c = new boolean[6];
	boolean d;
	boolean e;
	int f;

	public WorldGenMonumentRoomDefinition(int id) {
		this.id = id;
	}

	public void a(BlockFace face, WorldGenMonumentRoomDefinition var2) {
		this.b[face.getId()] = var2;
		var2.b[face.getOpposite().getId()] = this;
	}

	public void a() {
		for (int var1 = 0; var1 < 6; ++var1) {
			this.c[var1] = this.b[var1] != null;
		}

	}

	public boolean a(int var1) {
		if (this.e) {
			return true;
		} else {
			this.f = var1;

			for (int var2 = 0; var2 < 6; ++var2) {
				if (this.b[var2] != null && this.c[var2] && this.b[var2].f != var1 && this.b[var2].a(var1)) {
					return true;
				}
			}

			return false;
		}
	}

	public boolean b() {
		return this.id >= 75;
	}

	public int c() {
		int var1 = 0;

		for (int i = 0; i < 6; ++i) {
			if (this.c[i]) {
				++var1;
			}
		}

		return var1;
	}
}
