package com.saespmar.warbot.twitter;

import java.io.File;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthenticationTest {
    
    static Authentication instance;
    
    @BeforeClass
    public static void setUpClass() {
        instance = new Authentication(new File("test_case/tokens"));
    }

    @Test
    public void testGetCONSUMER_KEY() {
        String expResult = "oauth_api_key";
        String result = instance.getCONSUMER_KEY();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCONSUMER_SECRET() {
        String expResult = "oauth_api_secret_key";
        String result = instance.getCONSUMER_SECRET();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetACCESS_KEY() {
        String expResult = "oauth_access_token";
        String result = instance.getACCESS_KEY();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetACCESS_SECRET() {
        String expResult = "oauth_access_token_secret";
        String result = instance.getACCESS_SECRET();
        assertEquals(expResult, result);
    }
    
}
