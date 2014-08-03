package tonius.simplyjetpacks.nei;

import net.minecraft.item.ItemStack;
import tonius.simplyjetpacks.item.jetpack.Jetpack;
import tonius.simplyjetpacks.setup.SJItems;
import tonius.simplyjetpacks.util.StringUtils;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ArmoringRecipeHandler extends TemplateRecipeHandler {

    @Override
    public String getRecipeName() {
        return StringUtils.translate("gui.nei.recipe.armoring");
    }

    @Override
    public String getGuiTexture() {
        return "simplyjetpacks:textures/gui/empty.png";
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public void drawForeground(int recipe) {
        super.drawForeground(recipe);
        GuiDraw.drawString(StringUtils.translate("gui.nei.recipe.armoring.input"), 26, 24, 0x404040, false);
        GuiDraw.drawString(StringUtils.translate("gui.nei.recipe.armoring.plating"), 26, 52, 0x404040, false);
        GuiDraw.drawString(StringUtils.translate("gui.nei.recipe.armoring.output"), 26, 80, 0x404040, false);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        if (result.getItem() == SJItems.jetpacks) {
            int dmg = result.getItemDamage();
            Jetpack jetpack = Jetpack.getJetpack(dmg);
            if (jetpack != null && jetpack.isArmored() && jetpack.hasArmoredVersion()) {
                this.arecipes.add(new CachedArmoringRecipe(new ItemStack(SJItems.jetpacks, 1, dmg - 100), new ItemStack(SJItems.components, 1, jetpack.getPlatingMeta()), new ItemStack(SJItems.jetpacks, 1, dmg)));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        int dmg = ingredient.getItemDamage();
        if (ingredient.getItem() == SJItems.jetpacks) {
            Jetpack jetpack = Jetpack.getJetpack(dmg);
            if (jetpack != null && !jetpack.isArmored() && jetpack.hasArmoredVersion()) {
                this.arecipes.add(new CachedArmoringRecipe(new ItemStack(SJItems.jetpacks, 1, dmg), new ItemStack(SJItems.components, 1, jetpack.getPlatingMeta()), new ItemStack(SJItems.jetpacks, 1, dmg + 100)));
            }
        } else if (ingredient.getItem() == SJItems.components) {
            for (int i = 0; i <= Jetpack.getHighestMeta(); i++) {
                Jetpack jetpack = Jetpack.getJetpack(i);
                if (jetpack != null && !jetpack.isArmored() && jetpack.hasArmoredVersion() && jetpack.getPlatingMeta() == dmg) {
                    this.arecipes.add(new CachedArmoringRecipe(new ItemStack(SJItems.jetpacks, 1, i), new ItemStack(SJItems.components, 1, jetpack.getPlatingMeta()), new ItemStack(SJItems.jetpacks, 1, i + 100)));
                }
            }
        }
    }

    public class CachedArmoringRecipe extends CachedRecipe {

        private PositionedStack input;
        private PositionedStack plating;
        private PositionedStack output;

        public CachedArmoringRecipe(ItemStack input, ItemStack plating, ItemStack output) {
            this.input = new PositionedStack(input, 4, 20);
            this.plating = new PositionedStack(plating, 4, 48);
            this.output = new PositionedStack(output, 4, 76);
        }

        @Override
        public PositionedStack getIngredient() {
            return this.input;
        }

        @Override
        public PositionedStack getOtherStack() {
            return this.plating;
        }

        @Override
        public PositionedStack getResult() {
            return this.output;
        }

    }

}
