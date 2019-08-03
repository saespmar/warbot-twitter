package com.saespmar.warbot.twitter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BattlefieldTest {
    
    static Battlefield instance;
    static ArrayList<Participant> participants;
    
    @BeforeClass
    public static void setUpClass() {
        
        // Make temporary file
        File original = new File("test_case/battle.txt");
        File temp = new File("test_case/temp.txt");
        InputStream in = null;
        OutputStream out = null;
        
        try {
            in = new BufferedInputStream(new FileInputStream(original));
            out = new BufferedOutputStream(new FileOutputStream(temp));
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        instance = new Battlefield(temp);
        participants = new ArrayList<>();
        participants.add(new Participant("Test1", true));
        participants.add(new Participant("Test2", true));
        participants.add(new Participant("Test3", false));
        participants.add(new Participant("Test4", false));
        participants.add(new Participant("Test5", true));
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        File temp = new File("test_case/temp.txt");
        temp.delete();
        
        File picture = new File("test_case/3remaining.jpg");
        picture.delete();
    }
    
    @Test
    public void testGetParticipants() {
        assertEquals(participants, instance.getParticipants());
    }
    
    @Test
    public void testSetParticipants() throws NoSuchFieldException {
        instance.setParticipants(participants);
        assertEquals(participants, instance.getParticipants());
    }
    
    @Test
    public void testGetAlive() {
        assertEquals(3, instance.getAlive());
    }
    
    @Test
    public void testGetParticipantsFile() {
        File expResult = new File("test_case/temp.txt");
        File result = instance.getParticipantsFile();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetParticipantsFile() {
        File participantsFile = new File("test_case/temp.txt");
        instance.setParticipantsFile(participantsFile);
        assertEquals(participantsFile, instance.getParticipantsFile());
    }
    
    @Test
    public void testGetPicturePath() {
        assertEquals("test_case", instance.getPicturePath());
    }
    
    @Test
    public void testSetPicturePath() {
        String picturePath = "test_case";
        instance.setPicturePath(picturePath);
        assertEquals(picturePath, instance.getPicturePath());
    }
    
    @Test
    public void testFileSwapping() {
        participants.add(new Participant("New participant", false));
        instance.setParticipants(participants);
        instance.updateFile();
        instance.updateList();
        assertEquals(participants, instance.getParticipants());
    }
    
    @Test
    public void testFight() {
        Participant[] result = instance.fight();
        assertNotEquals(result[0], result[1]);
        assertEquals(2, instance.getAlive());
        
        result = instance.fight();
        assertNotEquals(result[0], result[1]);
        assertEquals(1, instance.getAlive());
        
        assertNull(instance.fight());
        
        // Reset the game to initial state
        instance.setParticipants(participants);
    }
    
    @Test
    public void testDrawTable() {
        File result = instance.drawTable();
        String expected = "test_case" + File.separator + instance.getAlive() + "remaining.jpg";
        assertEquals(expected, result.getPath());
    }
    
}
