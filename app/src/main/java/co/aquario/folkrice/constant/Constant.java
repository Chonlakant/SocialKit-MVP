package co.aquario.folkrice.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Constant {
    public static final List<Integer> QUANTITY_LIST = new ArrayList<Integer>();

    static {
        for (int i = 1; i < 11; i++) QUANTITY_LIST.add(i);
    }

}
