package com.saespmar.warbot.twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
     * directory as the .txt list of participants.</p>
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
     * participant is dead, the line starts with a #.
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
     * participants.
     */
    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
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
     * status.
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
    
}
