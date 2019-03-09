package com.acme.http.alt;

/**
 * Jackson can serialize public fields without annotations
 */
public class ProductView {

    public final long id;
    public final String name;
    public final long version;

    ProductView(final long id, final String name, final long version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }
}
