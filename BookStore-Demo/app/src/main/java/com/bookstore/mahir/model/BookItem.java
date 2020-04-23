package com.bookstore.mahir.model;

public class BookItem {

  String name;
  String address;
  String phone;
  String contentUri;

  public BookItem() {
  }

  String url;
  String key;

  public BookItem(String name, String address, String phone, String key, String contentUri,
      String url
      ) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.contentUri = contentUri;
    this.url = url;
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public String getContentUri() {
    return contentUri;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }

  public String getUrl() {
    return url;
  }
}
