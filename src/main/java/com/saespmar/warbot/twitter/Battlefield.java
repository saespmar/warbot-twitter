package com.saespmar.warbot.twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * <p>Represents a battlefield where the participants fight.</p>
 *
 * @author saespmar
 * @version 0.0.1
 */
public class Battlefield {
    private ArrayList<Participant> participants = new ArrayList<>();
    private int alive;
    private File participantsFile;
    private String picturePath;
    
    /**
     *
     * <p>Create a new battlefield. The generated images are stored in the same
     * directory as the .txt list of participants. Only the first 100 items
     * will be taken into account.</p>
     *
     * @param participantsFile a list of participants, one per line. If the
     * participant is dead, the line starts with a #.
     *
     * @see <a href="example_battlefield/battle.txt">Example of the format of
     * the file.</a>
     */
    public Battlefield(File participantsFile) {
        this(participantsFile, participantsFile.getParent());
    }
    
    /**
     *
     * <p>Create a new battlefield.</p>
     *
     * @param participantsFile a list of participants, one per line. If the
     * participant is dead, the line starts with a #. Only the first 100 items
     * will be taken into account.
     * @param picturePath path where the generated images are stored.
     *
     * @see <a href="example_battlefield/battle.txt">Example of the format of
     * the file.</a>
     */
    public Battlefield(File participantsFile, String picturePath) {
        this.participantsFile = participantsFile;
        this.picturePath = picturePath;
        updateList();
    }
    
    /**
     *
     * <p>Get the list of current participants.</p>
     *
     * @return an array of participants.
     */
    public ArrayList<Participant> getParticipants() {
        return participants;
    }
    
    /**
     *
     * <p>Set the list of participants.</p>
     *
     * @param participants an ArrayList with the information from the
     * participants. The list can't contain more than 100 participants.
     */
    public void setParticipants(ArrayList<Participant> participants) {
        if (participants.size() > 100) throw new IllegalArgumentException("The list can't contain more than 100 participants");
        this.participants = participants;
        
        // Update the alive count
        alive = 0;
        for (Participant p : participants){
            if (p.isAlive()) alive++;
        }
    }
    
    /**
     *
     * <p>Get the number of people alive.</p>
     *
     * @return a positive integer with the number of participants that are
     * alive.
     */
    public int getAlive() {
        return alive;
    }
    
    /**
     *
     * <p>Get the file where the list of participants is stored and
     * retrieved.</p>
     *
     * @return the file with all the participants and their status.
     */
    public File getParticipantsFile() {
        return participantsFile;
    }
    
    /**
     *
     * <p>Set the file where the list of participants is stored and
     * retrieved.</p>
     *
     * @param participantsFile the file with all the participants and their
     * status. Only the first 100 items will be taken into account.
     *
     * @see <a href="example_battlefield/battle.txt">Example of the format of
     * the file.</a>
     */
    public void setParticipantsFile(File participantsFile) {
        this.participantsFile = participantsFile;
    }
    
    /**
     *
     * <p>Get the path where the generated images are stored.</p>
     *
     * @return the path where the generated images are stored.
     */
    public String getPicturePath() {
        return picturePath;
    }
    
    /**
     *
     * <p>Set the path where the generated images are stored.</p>
     *
     * @param picturePath the path where the generated images are stored.
     */
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
    
    /**
     *
     * <p>Retrieves the list of participants from the file and stores it in
     * memory, so it can be accessed through the
     * {@link #getParticipants() getParticipants} method</p>
     */
    public void updateList(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(participantsFile));
            
            int max = 100; // No more than 100 participants
            int i = 0;
            alive = 0;
            
            // Iterate through the list
            String current;
            while((current = br.readLine()) != null && i<max){
                current = current.trim();
                Participant p;
                
                // Discard empty lines
                if (!current.equals("")){
                    
                    // A # at the beginning means the participant is dead
                    if (current.startsWith("#")){
                        String name = current.substring(1, current.length()).trim();
                        p = new Participant(name, false);
                    } else {
                        p = new Participant(current);
                        alive++;
                    }
                    
                    // Add it to the list
                    participants.add(p);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     *
     * <p>Updates the list of participants file with the information stored in
     * memory. The file with this list of participants can be set through the
     * {@link #setParticipants(ArrayList<Participant>) setParticipants} method.
     * It's strongly recommended to call this method every time the ArrayList
     * of participants changes.</p>
     */
    public void updateFile(){
        FileWriter fw = null;
        try {
            fw = new FileWriter(participantsFile.getPath());
            for (Participant p : participants){
                if (!p.isAlive()) fw.write("# "); // If the participant is dead, add a # at the beginning
                fw.write(p.getName());
                fw.write(System.getProperty("line.separator"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    /**
     *
     * <p>Selects a random participant to kill other random participant.</p>
     *
     * @return an array which has the killer in position 0 and the victim in
     * position 1. If there are less than 2 participants, null is returned,
     * because the game has ended.
     */
    public Participant[] fight(){
        
        // If there are less than 2 participants alive, the game has ended
        if (alive < 2) return null;
        
        // Get the list of surviving participants
        ArrayList<Participant> surviving = new ArrayList<>();
        for (Participant p : participants){
            if (p.isAlive()) surviving.add(p);
        }
        
        // Random victim
        Random rand = new SecureRandom();
        int victimIndex = rand.nextInt(surviving.size());
        Participant victim = surviving.get(victimIndex);
        participants.get(participants.indexOf(victim)).setAlive(false); // Update status of victim in the general list
        surviving.remove(victimIndex); // Remove from alive list
        victim.setAlive(false); // Update status of victim in the returned value
        alive--;
        
        // Random killer
        int killerIndex = rand.nextInt(surviving.size());
        Participant killer = surviving.get(killerIndex);
        
        Participant[] result = {killer, victim};
        return result;
    }
}
