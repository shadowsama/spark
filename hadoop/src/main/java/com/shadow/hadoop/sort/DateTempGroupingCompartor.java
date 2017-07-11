package com.shadow.hadoop.sort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Created by shadow on 2017/7/11 0011.
 */
public class DateTempGroupingCompartor extends WritableComparator {
    public DateTempGroupingCompartor() {
        super(DateTempPair.class, true);
    }

    // 对相同的可以 在进行分组，已使各自的reducer
    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        DateTempPair a1 = (DateTempPair) a;
        DateTempPair b1 = (DateTempPair) b;

        return a1.getYearMonth().compareTo(b1.getYearMonth());
    }
}
