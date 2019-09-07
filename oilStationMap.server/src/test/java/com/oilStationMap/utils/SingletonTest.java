package com.oilStationMap.utils;

import com.oilStationMap.MySuperTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by caihongwang on 2018/10/12.
 */
public class SingletonTest extends MySuperTest {



    @Test
    public void Test() {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println("singleton1 == singleton2  --->>>   " + (singleton1 == singleton2));
    }


}

public class Singleton {

    private static final AtomicReference<Singleton> singleton = new AtomicReference<Singleton>();

    private Singleton(){
        System.out.println("SingletonUtil 单例模式被执行了.");
    }

    public static Singleton getInstance(){
        for(;;){
            Singleton currentObj = singleton.get();
            if(currentObj!=null){
                return currentObj;
            }
            currentObj = new Singleton();
            if(singleton.compareAndSet(null, currentObj)){
                return currentObj;
            }
        }
    }
}


