import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FoodHandler {
    private Set<Recipe> recipes;

    public FoodHandler(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public int countSafeIngredients() {
        Set<String> safeIngrs = getAllIngredients();
        safeIngrs.removeAll(getAllergicIngredients().keySet());

        int count = 0;
        for (Recipe recipe : recipes) {
            Set<String> rIngrs = recipe.getIngredients();
            for (String ingr : safeIngrs) {
                if (rIngrs.contains(ingr))
                    count++;
            }
        }
        return count;
    }

    public String[] getSortedAllergicIngredients() {
        Map<String,String> allergicIngrs = getAllergicIngredients();
        List<String> sorted = new ArrayList<>();
        allergicIngrs.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .forEachOrdered(x -> sorted.add(x.getKey()));
        return sorted.toArray(String[]::new);
    }

    public Map<String,String> getAllergicIngredients() {
        Map<String,List<Recipe>> allergenMap = mapAllergens();
        Map<String,String> ingrAllergy = new HashMap<>();
        Map<Set<String>,String> unidentified = new HashMap<>();
        
        for (String allergen : allergenMap.keySet()) {
            List<Recipe> recipeList = allergenMap.get(allergen);
            Set<String> ingrs1 = new HashSet<>(recipeList.get(0)
                                               .getIngredients());
            for (int i = 1; i < recipeList.size(); i++) {
                Set<String> ingrs2 = recipeList.get(i).getIngredients();
                ingrs1.retainAll(ingrs2);
            }

            if (ingrs1.size() == 1) {
                String ingr = "";
                for (String str : ingrs1)
                    ingr = str;

                ingrAllergy.put(ingr, allergen);
            } else {
                unidentified.put(ingrs1, allergen);
            }
        }

        while (!unidentified.isEmpty()) {
            // ugly workaround to prevent concurrent modification
            Set<Set<String>> toRemove = new HashSet<>();
            Set<Map<Set<String>,String>> toAdd = new HashSet<>();

            for (Set<String> ingrs : unidentified.keySet()) {
                if (ingrs.size() == 1) {
                    String ingr = "";
                    for (String str : ingrs)
                        ingr = str;

                    ingrAllergy.put(ingr, unidentified.get(ingrs));
                    toRemove.add(ingrs);
                } else {
                    // ugly workaround to prevent modification to the key so
                    // that the map no longer recognizes it
                    Set<String> ingrsCopy = new HashSet<>(ingrs);
                    if (ingrsCopy.removeAll(ingrAllergy.keySet())) {
                        toRemove.add(ingrs);
                        Map<Set<String>,String> hm = new HashMap<>();
                        hm.put(ingrsCopy, unidentified.get(ingrs));
                        toAdd.add(hm);
                    }
                }
            }

            for (Set<String> ingrs : toRemove) {
                unidentified.remove(ingrs);
            }

            for (Map<Set<String>,String> ingrMap : toAdd) {
                unidentified.putAll(ingrMap);
            }
        }
        return ingrAllergy;
    }

    public Set<String> getAllIngredients() {
        Set<String> ingredients = new HashSet<>();
        for (Recipe recipe : recipes) {
            ingredients.addAll(recipe.getIngredients());
        }
        return ingredients;
    }

    private Map<String,List<Recipe>> mapAllergens() {
        Map<String,List<Recipe>> m = new HashMap<>();
        for (Recipe recipe : recipes) {
            for (String allergen : recipe.getAllergens()) {
                if (!m.containsKey(allergen)) {
                    List<Recipe> recipeList = new ArrayList<>();
                    m.put(allergen, recipeList);
                }
                m.get(allergen).add(recipe);
            }
        }
        return m;
    }
}
