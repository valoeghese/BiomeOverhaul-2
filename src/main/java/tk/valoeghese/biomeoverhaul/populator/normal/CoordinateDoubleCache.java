package tk.valoeghese.biomeoverhaul.populator.normal;

class CoordinateDoubleCache {
	CoordinateDoubleCache(CoordinateDoubleProvider source) {
		this.source = source;
	}

	private int x, z = 0;
	private boolean init = false;
	private double value = 0;
	private final CoordinateDoubleProvider source;

	double get(int x, int z) {
		boolean retrieve = init && (this.x == x && this.z == z);

		if (!retrieve) {
			this.value = this.source.get(x, z);
		}

		return this.value;
	}
}

@FunctionalInterface
interface CoordinateDoubleProvider {
	double get(int x, int z);
}