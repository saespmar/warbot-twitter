package com.saespmar.warbot.twitter;

/**
 *
 * <p>Represents a contender</p>
 *
 * @author saespmar
 * @version 0.0.1
 */
public class Participant {
    private String name;
    private boolean alive;

    /**
     *
     * <p>Create a participant. The participant is alive by default.</p>
     * 
     * @param name the name shown on screen for this participant.
     */
    public Participant(String name) {
        this.name = name;
        alive = true;
    }

    /**
     *
     * <p>Create a participant.</p>
     * 
     * @param name the name shown on screen for this participant.
     * @param alive participant status, whether is alive or not.
     */
    public Participant(String name, boolean alive) {
        this.name = name;
        this.alive = alive;
    }

    /**
     *
     * <p>Get the name of the participant.</p>
     *
     * @return the String shown on screen for this participant.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * <p>Set a new name for the participant.</p>
     * 
     * @param name the name shown on screen for this participant.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * <p>Get whether the participant is still fighting or not.</p>
     *
     * @return false if the participant is dead, true if it's still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     *
     * <p>Set a new status for the participant.</p>
     * 
     * @param alive false if the participant is dead, true if it's still alive.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
}
