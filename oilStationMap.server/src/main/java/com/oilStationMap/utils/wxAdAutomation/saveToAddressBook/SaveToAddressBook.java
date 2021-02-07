package com.oilStationMap.utils.wxAdAutomation.saveToAddressBook;

import java.util.Map;

public interface SaveToAddressBook {

    /**
     * 将群保存到通讯录
     * @param paramMap
     * @throws Exception
     */
    public boolean saveToAddressBook(Map<String, Object> paramMap) throws Exception;
}
