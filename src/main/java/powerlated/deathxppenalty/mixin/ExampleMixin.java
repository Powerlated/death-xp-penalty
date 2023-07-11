package powerlated.deathxppenalty.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ExampleMixin {

	@Inject(at = @At("HEAD"), method = "onDeath")
	private void init(CallbackInfo info) {
		var inst = (ServerPlayerEntity) (Object) this;
		if (inst.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
			var loss = inst.experienceLevel / 2;
			var text = Text.literal("You died and lost ").formatted(Formatting.GREEN)
					.append(Text.literal(Integer.toString(loss)).formatted(Formatting.RED))
					.append(Text.literal(" experience levels").formatted(Formatting.GREEN));

			inst.sendMessage(text);
			inst.setExperienceLevel(inst.experienceLevel - loss);
		}
	}
}