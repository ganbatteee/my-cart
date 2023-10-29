package hanu.a2_2001040209.db;

import android.provider.BaseColumns;

public class DBSchema {

    public final class ProductsTable {
        public static final String NAME = "products";
        public final class Cols {
            public static final String ID = "id";
            public static final String THUMBNAIL = "thumbnail";
            public static final String NAME = "name";
            public static final String UNIT_PRICE = "unitprice";

        }
    }

    public final class ProductsCart {
        public static String NAME = "productsCart";
        public final class Cols {
            public static final String ID = "id";
            public static final String THUMBNAIL = "thumbnail";
            public static final String NAME = "name";
            public static final String QUANTITY = "quantity";
            public static final String UNIT_PRICE = "unitprice";

        }
    }
}
