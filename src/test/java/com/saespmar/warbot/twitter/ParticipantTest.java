package com.saespmar.warbot.twitter;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParticipantTest {
    
    static Participant instance;
    
    @BeforeClass
    public static void setUpClass() {
        instance = new Participant("name", true);
    }

    @Test
    public void testGetName() {
        assertEquals("name", instance.getName());
    }

    @Test
    public void testSetName() {
        instance.setName("name");
        assertEquals("name", instance.getName());
    }

    @Test
    public void testIsAlive() {
        assertTrue(instance.isAlive());
    }

    @Test
    public void testSetAlive() {
        instance.setAlive(true);
        assertTrue(instance.isAlive());
    }

    @Test
    public void testHashCode() {
        Participant equal = new Participant("name", true);
        Participant notEqual = new Participant("NAME", false);
        assertEquals(equal.hashCode(), instance.hashCode());
        assertNotEquals(notEqual.hashCode(), instance.hashCode());
    }

    @Test
    public void testEquals() {
        Participant equal = new Participant("name", true);
        Participant notEqual = new Participant("NAME", false);
        assertTrue(equal.equals(instance) && instance.equals(equal));
        assertFalse(notEqual.equals(instance) || instance.equals(notEqual));
    }
    
}
