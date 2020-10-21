package com.oilStationMap.service;

import java.util.Map;

public interface WX_BatchImportService {

  /**
   * 将excel通讯录转换为vcf
   */
  void convertExcelToVcf(Map<String, Object> paramMap);
}
