package ru.gb;

public class Product {
    private String Title;
    private int Cost;
    Product(String Title, int Cost) {
        this.Title = Title;
        this.Cost = Cost;
    }
    public String getTitle() { return Title; }
    public int getCost() { return Cost; }
    public void setCost(int newCost) { Cost = newCost; }
}
