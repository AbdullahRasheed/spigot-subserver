package me.abdullah.elolib;

public enum Rank {

    BRONZE_1("Bronze I"),
    BRONZE_2("Bronze II"),
    BRONZE_3("Bronze III"),
    SILVER_1("Silver I"),
    SILVER_2("Silver II"),
    SILVER_3("Silver III"),
    GOLD_1("Gold I"),
    GOLD_2("Gold II"),
    GOLD_3("Gold III"),
    PLAT_1("Platinum I"),
    PLAT_2("Platinum II"),
    PLAT_3("Platinum III"),
    DIAMOND_1("Diamond I"),
    DIAMOND_2("Diamond II"),
    DIAMOND_3("Diamond III"),
    CHALLENGER("Challenger"),
    PANTHERA("Panthera");

    private final String name;
    Rank(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
