package com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by caihongwang on 2018/10/12.
 */
public class IpDaiLIUtilTest extends MySuperTest {

    @Test
    public void Test() {
//        List<Map<String, String>> ipList = IpDaiLiUtil.getDaiLiIpList();
//        for(Map ipMap : ipList){
//            System.out.println("ip ---->>>>" + ipMap.toString());
//        }

        // TODO Auto-generated method stub
        List<Integer> listA=new ArrayList<>();
        listA.add(1);
        listA.add(2);
        listA.add(3);
        listA.add(4);
        listA.add(5);
        listA.add(6);
        Iterator<Integer> it_b=listA.iterator();
        while(it_b.hasNext()){
            Integer a=it_b.next();
            if (a==4) {
                it_b.remove();
            }
        }
        for(Integer b:listA){
            System.out.println(b);
        }
    }


}
