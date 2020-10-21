CREATE PROCEDURE updateOilStationPrice_by_Procedure(IN  province           VARCHAR(300),
                                                    IN  newOilStationPrice TEXT,
                                                    IN  price_0            DECIMAL(10, 2),
                                                    IN  price_92           DECIMAL(10, 2),
                                                    IN  price_95           DECIMAL(10, 2),
                                                    IN  price_98           DECIMAL(10, 2),
                                                    OUT updateNum          INT)
  BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE row_id INT;
    DECLARE row_oilStationPrice TEXT;
    -- 这个语句声明一个光标保存记录集。也可以在子程序中定义多个光标，但是一个块中的每一个光标必须有唯一的名字
    DECLARE cur CURSOR FOR SELECT
                             id                AS "id",
                             oil_station_price AS "oilStationPrice"
                           FROM o_oil_station
                           WHERE oil_station_adress LIKE
                                 concat('%', province, '%');
    DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
    OPEN cur;
    REPEAT -- 循环
      FETCH cur
      INTO row_id, row_oilStationPrice; -- 这个语句用指定的打开光标读取下一行（如果有下一行的话），并且前进光标指针
      IF NOT done
      THEN
        -- 1.更新 0#柴油 价格
        IF price_0 IS NOT NULL
        THEN
          SET row_oilStationPrice = JSON_REPLACE(
              row_oilStationPrice,
              SUBSTRING_INDEX(
                  REPLACE(JSON_SEARCH(row_oilStationPrice, 'all', '柴油'), '"',
                          ''), '.', 1),
              JSON_EXTRACT(newOilStationPrice,
                           SUBSTRING_INDEX(REPLACE(
                                               JSON_SEARCH(newOilStationPrice,
                                                           'all', '柴油'), '"',
                                               ''), '.', 1))
          );
          -- 随机 0#柴油 油价
          SET @price_0_new = 0;
          SET @randNum = (SELECT round(RAND()*0.1, 2));
          IF @randNum > 0.05
          THEN
            SET @price_0_new = round((price_0 - @randNum), 2);
          ELSE
            SET @price_0_new = round((price_0 + @randNum), 2);
          END IF;
          SET @price_0_str = concat(price_0, "");
          SET @price_0_new_str = concat(@price_0_new, "");
          SET row_oilStationPrice = REPLACE(row_oilStationPrice, @price_0_str,
                                            @price_0_new_str);
        END IF;

        -- 2.更新 92#汽油 价格
        IF price_92 IS NOT NULL
        THEN
          SET row_oilStationPrice = JSON_REPLACE(
              row_oilStationPrice,
              SUBSTRING_INDEX(
                  REPLACE(JSON_SEARCH(row_oilStationPrice, 'all', '92'), '"',
                          ''), '.', 1),
              JSON_EXTRACT(newOilStationPrice,
                           SUBSTRING_INDEX(REPLACE(
                                               JSON_SEARCH(newOilStationPrice,
                                                           'all', '92'), '"',
                                               ''), '.', 1))
          );
          -- 随机 92#汽油 油价
          SET @price_92_new = 0;
          SET @randNum = (SELECT round(RAND()*0.1, 2));
          IF @randNum > 0.05
          THEN
            SET @price_92_new = round((price_92 - @randNum), 2);
          ELSE
            SET @price_92_new = round((price_92 + @randNum), 2);
          END IF;
          SET @price_92_str = concat(price_92, "");
          SET @price_92_new_str = concat(@price_92_new, "");
          SET row_oilStationPrice = REPLACE(row_oilStationPrice, @price_92_str,
                                            @price_92_new_str);
        END IF;

        -- 3.更新 95#汽油 价格
        IF price_95 IS NOT NULL
        THEN
          SET row_oilStationPrice = JSON_REPLACE(
              row_oilStationPrice,
              SUBSTRING_INDEX(
                  REPLACE(JSON_SEARCH(row_oilStationPrice, 'all', '95'), '"',
                          ''), '.', 1),
              JSON_EXTRACT(newOilStationPrice,
                           SUBSTRING_INDEX(REPLACE(
                                               JSON_SEARCH(newOilStationPrice,
                                                           'all', '95'), '"',
                                               ''), '.', 1))
          );
          -- 随机 95#汽油 油价
          SET @price_95_new = 0;
          SET @randNum = (SELECT round(RAND()*0.1, 2));
          IF @randNum > 0.05
          THEN
            SET @price_95_new = round((price_95 - @randNum), 2);
          ELSE
            SET @price_95_new = round((price_95 + @randNum), 2);
          END IF;
          SET @price_95_str = concat(price_95, "");
          SET @price_95_new_str = concat(@price_95_new, "");
          SET row_oilStationPrice = REPLACE(row_oilStationPrice, @price_95_str,
                                            @price_95_new_str);
        END IF;

        -- 更新 油价
        UPDATE o_oil_station
        SET
          oil_station_price = row_oilStationPrice,
          update_time       = CURRENT_TIMESTAMP
        WHERE id = row_id and id not in (320124);

      END IF;
    UNTIL done END REPEAT; -- 结束循环
    CLOSE cur; -- 这个语句关闭先前打开的光标。

    -- 返回值
    SELECT count(id)
    INTO updateNum
    FROM o_oil_station
    WHERE oil_station_adress LIKE concat('%', province, '%');
    SELECT updateNum;
  END;
