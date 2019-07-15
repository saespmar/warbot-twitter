package com.saespmar.warbot.twitter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import javax.imageio.ImageIO;

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
    
    /**
     *
     * <p>Creates an image in the directory specified in the
     * {@link #setPicturePath(String) setPicturePath} method. The file is 
     * stored as [alive]remaining.jpg in it. This image contains a table with 
     * information about all the participants status.</p>
     * 
     * @return the generated image file.
     */
    public File drawTable() {
        int size = participants.size();
        int columns = size/26 + 1; // Max. 25 participants per on each column
        int rows = size/columns; // Split participants equally in all columns
        
        /**
         *
         * The number of columns and rows may not fit perfectly because of the number of participants
         * (for example, an odd number can't be perfectly splitted in 2 columns). In these scenarios,
         * an additional row is needed
         */
        if (columns*rows != size) rows++;
        
        int width = columns * 250;
        int height = rows * 20;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Normal font
        Font aliveFont = new Font("Console", Font.PLAIN, 15);
        g2d.setFont(aliveFont);
        FontMetrics fm = g2d.getFontMetrics();
        
        // Crossed font for dead participants
        Font deadFont = new Font("Console", Font.PLAIN, 15);
        Hashtable<TextAttribute, Object> map = new Hashtable<>();
        map.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        deadFont = deadFont.deriveFont(map);
        
        // Background
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // Text
        for (int i = 0; i<columns; i++){
            int verticalOffset = 0; // A new column begins
            for (int j = 0; j<rows; j++){
                int index = j + i*rows;
                if (index < size){
                    Participant p = participants.get(index);
                    String text = p.getName();
                    
                    // Set font
                    if (p.isAlive()){
                        g2d.setFont(aliveFont);
                        g2d.setColor(Color.BLACK);
                    }
                    else{
                        g2d.setFont(deadFont);
                        g2d.setColor(Color.RED);
                    }
                    
                    // Draw string
                    // Very long names are cut and ended with ...
                    if (text.length() > 34)
                        g2d.drawString(text.substring(0, 31) + "...", i*250, fm.getAscent()+verticalOffset);
                    else
                        g2d.drawString(text, i*250, fm.getAscent()+verticalOffset);
                    
                    verticalOffset += 20;
                }
            }
        }
        g2d.dispose();
        File result = new File(picturePath + "/" + alive + "remaining.jpg");
        try {
            ImageIO.write(img, "jpg", result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
