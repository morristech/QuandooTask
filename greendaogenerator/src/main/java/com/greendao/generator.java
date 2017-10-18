package com.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class generator {

    public static void main(String[] args) {

        Schema schema = new Schema(1, "krtkush.github.io.quandootask.db");
        // Your app package name and the (.db) is the folder
        // where the DAO files will be generated into.

        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addTableEntities(schema);
    }

    // This is use to describe the columns of your table
    private static Entity addTableEntities(final Schema schema) {
        Entity table = schema.addEntity("Table");
        table.addIdProperty().primaryKey().autoincrement();
        table.addBooleanProperty("is_reserved").notNull();
        table.addIntProperty("customer_id");
        return table;
    }
}
