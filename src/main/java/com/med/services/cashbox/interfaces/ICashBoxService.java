package com.med.services.cashbox.interfaces;

import com.med.model.Card;
import com.med.model.CashBox;
import com.med.model.Response;

/**
 * Created by george on 3/9/18.
 */
public interface ICashBoxService {
	Response saveCash(CashBox cash);
    CashBox getCashBox(String id);
    int getCashBox();
    int getTodayGiven();


}
