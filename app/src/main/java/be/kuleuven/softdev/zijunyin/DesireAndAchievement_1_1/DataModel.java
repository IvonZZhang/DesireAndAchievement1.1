package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_1;

public class DataModel {
    protected int id;
    protected String name;
    protected String coins;

    public DataModel(int id, String name, String coins) {
        this.id = id;
        this.name = name;
        this.coins = coins;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins){
        this.coins = coins;
    }
}
