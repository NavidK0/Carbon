package net.o2gaming.carbon.entity;

public class fa {

	protected final float a;
	protected final float b;
	protected final float c;

	public fa(float var1, float var2, float var3) {
		this.a = var1;
		this.b = var2;
		this.c = var3;
	}

        @Override
	public boolean equals(Object var1) {
		if (!(var1 instanceof fa)) {
			return false;
		} else {
			fa var2 = (fa) var1;
			return this.a == var2.a && this.b == var2.b && this.c == var2.c;
		}
	}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Float.floatToIntBits(this.a);
        hash = 43 * hash + Float.floatToIntBits(this.b);
        hash = 43 * hash + Float.floatToIntBits(this.c);
        return hash;
    }

	public float b() {
		return this.a;
	}

	public float c() {
		return this.b;
	}

	public float d() {
		return this.c;
	}
}