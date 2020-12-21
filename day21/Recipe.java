import java.util.Set;

public class Recipe {
    private Set<String> ingredients;
    private Set<String> allergens;
    private Integer hashCode;

    public Recipe(Set<String> ingredients, Set<String> allergens) {
        this.ingredients = ingredients;
        this.allergens = allergens;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public Set<String> getAllergens() {
        return allergens;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Recipe))
            return false;
        
        Recipe recipe = (Recipe)obj;
        Set<String> otherIngrs = recipe.getIngredients();
        Set<String> otherAllergens = recipe.getAllergens();
        // two recipes are the same if their ingredients and allergens are same
        if (ingredients.size() != otherIngrs.size() ||
            allergens.size() != otherAllergens.size())
            return false;

        for (String ingr : ingredients) {
            if (!otherIngrs.contains(ingr))
                return false;
        }
        
        for (String allergen : allergens) {
            if (!otherAllergens.contains(allergen))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            StringBuilder sb = new StringBuilder();
            for (String ingr : ingredients)
                sb.append(ingr);
            for (String allergen : allergens)
                sb.append(allergen);
            hashCode = sb.toString().hashCode();
        }
        return hashCode;
    }
}
