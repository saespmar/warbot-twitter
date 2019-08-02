package com.saespmar.warbot.twitter;

import java.util.Objects;

/**
 *
 * <p>Represents a contender</p>
 *
 * @author saespmar
 * @version 1.0
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + (this.alive ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Participant other = (Participant) obj;
        if (this.alive != other.alive) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
