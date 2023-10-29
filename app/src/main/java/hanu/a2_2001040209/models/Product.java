package hanu.a2_2001040209.models;

public class Product {
    private int id;
    private String thumbnail;
    private String name;
    private int unitPrice;
    private int numberInCart;

    public Product(String thumbnail, String name, int numberInCart, int unitPrice) {
        this.thumbnail = thumbnail;
        this.name = name;
        this.numberInCart = numberInCart;
        this.unitPrice = unitPrice;
    }

    public Product(String thumbnail, String name, int unitPrice) {
        this.thumbnail = thumbnail;
        this.name = name;
        this.unitPrice = unitPrice;
    }
    public Product(int id, String thumbnail, String name, int unitPrice) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Product(int id, String thumbnail, String name, int numberInCart, int unitPrice) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.name = name;
        this.numberInCart = numberInCart;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }


    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
