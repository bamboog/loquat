package com.loquat.core;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 *
 * @author gangwen.xu
 * Date  : 2018/11/27
 * Time  : 下午1:48
 * 类描述 :
 */
public class ParseSourceCodeTest {

    @Test
    public void  test(){
        ParseSourceCode parseSourceCode = new  ParseSourceCode();
        parseSourceCode.parse("src/test/java/Test.java");
    }

}