package tk.valoeghese.biomeoverhaul;

public class Options {
	private Options(Builder builder) {
		// NO-OP
		this.customBiomes = builder.customBiomes;
	}

	public final boolean customBiomes;

	public static class Builder {
		private boolean customBiomes = false;

		public Builder customBiomes(boolean value) {
			this.customBiomes = value;
			return this;
		}

		public Options build() {
			return new Options(this);
		}
	}
}
