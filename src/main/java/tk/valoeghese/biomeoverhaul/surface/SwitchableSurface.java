package tk.valoeghese.biomeoverhaul.surface;

import net.minecraft.util.Identifier;
import tk.valoeghese.worldcomet.api.surface.Surface;

public abstract class SwitchableSurface extends Surface {
	public SwitchableSurface(int switchCode, Identifier id) {
		super(id);

		this.switchCode = switchCode;
	}

	private final int switchCode;

	public int getSwitchCode() {
		return this.switchCode;
	}
}
