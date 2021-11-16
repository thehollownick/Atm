package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class AtmUtils {
    private static final Logger LOGGER= LoggerFactory.getLogger(AtmUtils.class);
    public static HashMap<Integer, Integer> fromIntToMoneys(int rub, HashMap<Integer, Integer> moneysInAtm) {
        LOGGER.debug("rub: {}, moneysInAtm: {}",rub,moneysInAtm);
        HashMap<Integer,Integer> temp = new HashMap<>();
        if (rub % 100 != 0) {
            LOGGER.error("Невозможно выдать такую сумму");
            throw new RuntimeException();
        }
        while (rub>0) {
            if (rub >= 5000 && moneysInAtm.get(5000) > 0) {
                temp.merge(5000, 1, Integer::sum);
                rub-=5000;
                moneysInAtm.put(5000,moneysInAtm.get(5000)-1);
            }
            else if (rub >= 2000 && moneysInAtm.get(2000) > 0) {
                temp.merge(2000, 1, Integer::sum);
                rub-=2000;
                moneysInAtm.put(2000,moneysInAtm.get(2000)-1);
            }
            else if (rub >= 1000 && moneysInAtm.get(1000) > 0) {
                temp.merge(1000, 1, Integer::sum);
                rub-=1000;
                moneysInAtm.put(1000,moneysInAtm.get(1000)-1);
            }
            else if (rub >= 500 && moneysInAtm.get(500) > 0) {
                temp.merge(500, 1, Integer::sum);
                rub-=500;
                moneysInAtm.put(500,moneysInAtm.get(500)-1);
            }
            else if (rub >= 200 && moneysInAtm.get(200) > 0) {
                temp.merge(200, 1, Integer::sum);
                rub-=200;
                moneysInAtm.put(200,moneysInAtm.get(200)-1);
            }
            else if (rub >= 100 && moneysInAtm.get(100) > 0) {
                temp.merge(100, 1, Integer::sum);
                rub-=100;
                moneysInAtm.put(100,moneysInAtm.get(100)-1);
            }
            else {
                LOGGER.error("Нет денег");
                throw new RuntimeException();
            }
        }
        LOGGER.debug("temp {}",temp);
        return temp;
    }
}
