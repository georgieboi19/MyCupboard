package com.w1667474.mycupboard;

import android.provider.BaseColumns;

public interface details extends BaseColumns {
    String tableName = "items";
    String col1 = "prodName";
    String col2 = "prodWeight";
    String col3 = "prodPrice";
    String col4 = "prodDesc";
    String col5 = "prodAvail";
}
