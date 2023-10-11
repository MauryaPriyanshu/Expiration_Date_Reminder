package com.foodwastemanagement.Model;


import java.io.Serializable;

public class Product implements Serializable {
    public Product() {

    }



    private String pid;
   // private String	 prod_id;

    private String prod_code;

    private String prod_HSN_code;

    private String prod_name;

    private String prod_sortdesc;

    private boolean isSelected;
    private String prod_desc;

    private String prod_price;

    private String prod_discount;

    private String prod_netprice;

    private String prod_catid;

    private String prod_sub_catid;

    private String prod_quantity;

    private String prod_weight;

    private String prod_color_id;

    private String prod_size_id;

    private String prod_inventory_ctrl;

    private String prod_inventory_rule;

    private String prod_is_taxable;

    private String prod_tax_class;

    private String prod_tax_rate;

    private String prod_delivery;

    private String prod_warranty;

    private String prod_image_cover;

    private String prod_image1;

    private String prod_image2;
    private String prod_image3;
    private String prod_brand;
    private String prod_status;
    private String prod_bestsellers;
    private String prod_new;
    private String prod_offer;
    private String prod_offer_text;
    private String prod_rating;
    private String prod_ship_charge;
    private String prod_men;
    private String prod_women;
    private String prod_tag1;
    private String prod_tag2;
    private String prod_tag3;
    private String prod_tag4;
    private String prod_order;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getProd_HSN_code() {
        return prod_HSN_code;
    }

    public void setProd_HSN_code(String prod_HSN_code) {
        this.prod_HSN_code = prod_HSN_code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    /*public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }*/

    public String getProd_code() {
        return prod_code;
    }

    public void setProd_code(String prod_code) {
        this.prod_code = prod_code;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_sortdesc() {
        return prod_sortdesc;
    }

    public void setProd_sortdesc(String prod_sortdesc) {
        this.prod_sortdesc = prod_sortdesc;
    }

    public String getProd_desc() {
        return prod_desc;
    }

    public void setProd_desc(String prod_desc) {
        this.prod_desc = prod_desc;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_discount() {
        return prod_discount;
    }

    public void setProd_discount(String prod_discount) {
        this.prod_discount = prod_discount;
    }

    public String getProd_netprice() {
        return prod_netprice;
    }

    public void setProd_netprice(String prod_netprice) {
        this.prod_netprice = prod_netprice;
    }

    public String getProd_catid() {
        return prod_catid;
    }

    public void setProd_catid(String prod_catid) {
        this.prod_catid = prod_catid;
    }

    public String getProd_sub_catid() {
        return prod_sub_catid;
    }

    public void setProd_sub_catid(String prod_sub_catid) {
        this.prod_sub_catid = prod_sub_catid;
    }

    public String getProd_quantity() {
        return prod_quantity;
    }

    public void setProd_quantity(String prod_quantity) {
        this.prod_quantity = prod_quantity;
    }

    public String getProd_weight() {
        return prod_weight;
    }

    public void setProd_weight(String prod_weight) {
        this.prod_weight = prod_weight;
    }

    public String getProd_color_id() {
        return prod_color_id;
    }

    public void setProd_color_id(String prod_color_id) {
        this.prod_color_id = prod_color_id;
    }

    public String getProd_size_id() {
        return prod_size_id;
    }

    public void setProd_size_id(String prod_size_id) {
        this.prod_size_id = prod_size_id;
    }

    public String getProd_inventory_ctrl() {
        return prod_inventory_ctrl;
    }

    public void setProd_inventory_ctrl(String prod_inventory_ctrl) {
        this.prod_inventory_ctrl = prod_inventory_ctrl;
    }

    public String getProd_inventory_rule() {
        return prod_inventory_rule;
    }

    public void setProd_inventory_rule(String prod_inventory_rule) {
        this.prod_inventory_rule = prod_inventory_rule;
    }

    public String getProd_is_taxable() {
        return prod_is_taxable;
    }

    public void setProd_is_taxable(String prod_is_taxable) {
        this.prod_is_taxable = prod_is_taxable;
    }

    public String getProd_tax_class() {
        return prod_tax_class;
    }

    public void setProd_tax_class(String prod_tax_class) {
        this.prod_tax_class = prod_tax_class;
    }

    public String getProd_tax_rate() {
        return prod_tax_rate;
    }

    public void setProd_tax_rate(String prod_tax_rate) {
        this.prod_tax_rate = prod_tax_rate;
    }

    public String getProd_delivery() {
        return prod_delivery;
    }

    public void setProd_delivery(String prod_delivery) {
        this.prod_delivery = prod_delivery;
    }

    public String getProd_warranty() {
        return prod_warranty;
    }

    public void setProd_warranty(String prod_warranty) {
        this.prod_warranty = prod_warranty;
    }

    public String getProd_image_cover() {
        return prod_image_cover;
    }

    public void setProd_image_cover(String prod_image_cover) {
        this.prod_image_cover = prod_image_cover;
    }

    public String getProd_image1() {
        return prod_image1;
    }

    public void setProd_image1(String prod_image1) {
        this.prod_image1 = prod_image1;
    }

    public String getProd_image2() {
        return prod_image2;
    }

    public void setProd_image2(String prod_image2) {
        this.prod_image2 = prod_image2;
    }

    public String getProd_image3() {
        return prod_image3;
    }

    public void setProd_image3(String prod_image3) {
        this.prod_image3 = prod_image3;
    }

    public String getProd_brand() {
        return prod_brand;
    }

    public void setProd_brand(String prod_brand) {
        this.prod_brand = prod_brand;
    }

    public String getProd_status() {
        return prod_status;
    }

    public void setProd_status(String prod_status) {
        this.prod_status = prod_status;
    }

    public String getProd_bestsellers() {
        return prod_bestsellers;
    }

    public void setProd_bestsellers(String prod_bestsellers) {
        this.prod_bestsellers = prod_bestsellers;
    }

    public String getProd_new() {
        return prod_new;
    }

    public void setProd_new(String prod_new) {
        this.prod_new = prod_new;
    }

    public String getProd_offer() {
        return prod_offer;
    }

    public void setProd_offer(String prod_offer) {
        this.prod_offer = prod_offer;
    }

    public String getProd_offer_text() {
        return prod_offer_text;
    }

    public void setProd_offer_text(String prod_offer_text) {
        this.prod_offer_text = prod_offer_text;
    }

    public String getProd_rating() {
        return prod_rating;
    }

    public void setProd_rating(String prod_rating) {
        this.prod_rating = prod_rating;
    }

    public String getProd_ship_charge() {
        return prod_ship_charge;
    }

    public void setProd_ship_charge(String prod_ship_charge) {
        this.prod_ship_charge = prod_ship_charge;
    }

    public String getProd_men() {
        return prod_men;
    }

    public void setProd_men(String prod_men) {
        this.prod_men = prod_men;
    }

    public String getProd_women() {
        return prod_women;
    }

    public void setProd_women(String prod_women) {
        this.prod_women = prod_women;
    }

    public String getProd_tag1() {
        return prod_tag1;
    }

    public void setProd_tag1(String prod_tag1) {
        this.prod_tag1 = prod_tag1;
    }

    public String getProd_tag2() {
        return prod_tag2;
    }

    public void setProd_tag2(String prod_tag2) {
        this.prod_tag2 = prod_tag2;
    }

    public String getProd_tag3() {
        return prod_tag3;
    }

    public void setProd_tag3(String prod_tag3) {
        this.prod_tag3 = prod_tag3;
    }

    public String getProd_tag4() {
        return prod_tag4;
    }

    public void setProd_tag4(String prod_tag4) {
        this.prod_tag4 = prod_tag4;
    }

    public String getProd_order() {
        return prod_order;
    }

    public void setProd_order(String prod_order) {
        this.prod_order = prod_order;
    }
}
