package com.newMall.service;

import java.util.Map;

public interface BatchImportService {

  /**
   * 将excel通讯录转换为vcf
   */
  void convertExcelToVcf(Map<String, Object> paramMap);
}
