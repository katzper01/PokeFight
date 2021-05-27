package sample.model.fight;

import javafx.util.Pair;
import sample.model.Constants;
import sample.model.datamodels.Result;
import sample.model.datamodels.TypeEffects;
import sample.model.exceptions.HttpException;
import sample.model.fetchers.TypeEffectsFetcher;

import java.util.HashMap;

public class TypeEffectSupplier {
    private static final HashMap<Pair<String, String>, Double> effect = new HashMap<>();
    private static boolean upToDate = false;

    private static void update() throws HttpException {
        TypeEffectsFetcher fetcher = new TypeEffectsFetcher();

        for (int i = 1; i <= Constants.TYPES_NUMBER; i++) {
            TypeEffects type = (TypeEffects) fetcher.fetchAndParseFromId(i);

            for (Result res : type.damage_relations.double_damage_to) {
                effect.put(new Pair<>(type.name, res.name), 2.0);
            }

            for (Result res : type.damage_relations.half_damage_to) {
                effect.put(new Pair<>(type.name, res.name), 0.5);
            }

            for (Result res : type.damage_relations.no_damage_to) {
                effect.put(new Pair<>(type.name, res.name), 0.0);
            }
        }

        upToDate = true;
    }

    public static double getEffect(String type1, String type2) throws HttpException {
        if (!upToDate) {
            update();
        }
        if (effect.containsKey(new Pair<>(type1, type2))) {
            return effect.get(new Pair<>(type1, type2));
        }
        return 1.0;
    }

    public static double getEffect(String type1, String type2, String type3) throws HttpException {
        return getEffect(type1, type2) * getEffect(type1, type3);
    }
}
