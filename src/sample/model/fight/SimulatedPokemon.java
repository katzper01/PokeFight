package sample.model.fight;

import javafx.util.Pair;
import sample.model.datamodels.Move;
import sample.model.datamodels.PokemonInstance;
import sample.model.datamodels.Status;

import java.util.*;


public class SimulatedPokemon extends PokemonInstance {
    private int currentHP;
    private int[] movesPP;

    private int modAttack;
    private int modDefence;
    private int modSpAttack;
    private int modSpDefence;
    private int modAccuracy;
    private int modEvasion;

    Map<Status, Integer> statusCounter;


    public SimulatedPokemon(PokemonInstance pokemon){
        super(pokemon);
        currentHP = pokemon.getHp();
        Move[] moves = getMoves();
        movesPP = new int[moves.length];
        for(int i=0; i<moves.length; i++) movesPP[i]=moves[i].getPowerPoints();
        modAttack=modDefence=modSpAttack=modSpDefence=modAccuracy=modEvasion=0;
        statusCounter = new TreeMap<>();
        for(Status s: Status.values()){
            statusCounter.put(s, 0);
        }
    }

    public SimulatedPokemon(SimulatedPokemon pokemon){
        super(pokemon);

        currentHP = pokemon.getFinalHp();
        movesPP = pokemon.movesPP;

        modAttack = pokemon.modAttack;
        modDefence = pokemon.modDefence;
        modSpAttack = pokemon.modSpAttack;
        modSpDefence = pokemon.modSpDefence;
        modAccuracy = pokemon.modAccuracy;
        modEvasion = pokemon.modEvasion;

        statusCounter = pokemon.statusCounter;
    }

   public int getFinalHp(){
        return currentHP;
   }

   public int getMovePP(int d){
        if(d<0 || d>movesPP.length) throw new IndexOutOfBoundsException();
        return movesPP[d];
   }

   public int getFinalAttack(){ return (int)(getAttack()*getMdf(modAttack)); }

   public int getFinalDefence(){
       return (int)(getDefense()*getMdf(modDefence));
   }

   public int getFinalSpAttack(){
       return (int)(getSpAttack()*getMdf(modSpAttack));
   }

   public int getFinalSpDefence(){
       return (int)(getSpDef()*getMdf(modSpDefence));
   }

   public void addAttack(int x){ modAttack+=x; }

   public void addDefence(int x){ modDefence+=x; }

   public void addSpAttack(int x){ modSpAttack+=x; }

   public void addSpDefence(int x){ modSpDefence+=x; }

   public double getMdf(int mod){
        double a=Math.max(2, 2+mod);
        double b=Math.max(2, 2-mod);
        return a/b;
   }


   public void addStatus(Move m){
        int d=new Random().nextInt(m.getMeta().getMaxTurns())+m.getMeta().getMinTurns();
        Status status =  m.getMeta().getAilment().getStatus();
       if(statusCounter.get(status)<d){
           statusCounter.put(status, d);
       }
   }

   public void affectStatus(Status status){
        switch (status) {
            case PARALYSIS:
                //TODO
                break;
            case  BURN:
                //TODO
                break;
            case FREEZE:
                //TODO
                break;
            case  POISON:
                //TODO
                break;
            case SLEEP:
                //TODO
                break;
            case  INFATUATION:
                //TODO
                break;
            case CONFUSION:
                //TODO
                break;
        }
   }

   public TreeSet<Status> processStatus(){
        TreeSet<Status> res = new TreeSet<>();
        for(Map.Entry<Status, Integer> entry: statusCounter.entrySet()){
            if(entry.getValue()>0){
                affectStatus(entry.getKey());
                res.add(entry.getKey());
            }
            if(statusCounter.get(entry.getKey())>0) {
                statusCounter.put(entry.getKey(), entry.getValue() - 1);
            }
        }
        return res;
   }


   public void affectBuffDebuff(Move m){
        int d=m.getMeta().getStatChange().getIndex();
        switch (d){
            case 1 -> addAttack(m.getMeta().getStatChange().getChange());
            case 2 -> addDefence(m.getMeta().getStatChange().getChange());
            case 3 -> addSpAttack(m.getMeta().getStatChange().getChange());
            case 4 -> addSpDefence(m.getMeta().getStatChange().getChange());
        }

   }

   public int sumPP(){
        int res=0;
        for(Integer i: movesPP){
            res+=i;
        }
        return res;
   }

   public void decrementPP(int moveId){
        this.movesPP[moveId]--;
   }

   public void heal(int healVal){
        currentHP += healVal;
   }

   public void dealDMG (int dmg){

        System.out.println("Dostaje " + dmg + " dmg i mam teraz " + (currentHP - dmg));

        currentHP -= dmg;
   }

}
