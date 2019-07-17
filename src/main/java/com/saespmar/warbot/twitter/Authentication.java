package com.saespmar.warbot.twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * <p>Stores the authentication configuration for accessing the Twitter API.</p>
 *
 * @author saespmar
 * @version 1.0
 */
public class Authentication {
    private String CONSUMER_KEY;
    private String CONSUMER_SECRET;
    private String ACCESS_KEY;
    private String ACCESS_SECRET;
    
    /**
     *
     * <p>Fill in all the tokens needed to use the Twitter API. It uses 
     * <a href="config/tokens.env">config/tokens.env</a> as the default path
     * for the configuration file.</p>
     * 
     * @see <a href="config/tokens.env.example">Example of the format of the 
     * file.</a>
     */
    public Authentication() {
        this(new File("config/tokens.env"));
    }
    
    /**
     *
     * <p>Fill in all the tokens needed to use the Twitter API.</p>
     *
     * @param configFile the file where the tokens are declared.
     * 
     * @see <a href="config/tokens.env.example">Example of the format of the 
     * file.</a>
     */
    public Authentication(File configFile) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(configFile));
            
            // Iterate through the list and asign the variables
            String current;
            while((current = br.readLine()) != null){
                current = current.trim();
                if (current.length() > 12 && current.substring(0, 12).equals("CONSUMER_KEY")){
                    String[] variable = current.split("=");
                    if (variable.length == 2){
                        CONSUMER_KEY = variable[1].trim();
                    }
                } else if (current.length() > 15 && current.substring(0, 15).equals("CONSUMER_SECRET")){
                    String[] variable = current.split("=");
                    if (variable.length == 2){
                        CONSUMER_SECRET = variable[1].trim();
                    }
                } else if (current.length() > 10 && current.substring(0, 10).equals("ACCESS_KEY")){
                    String[] variable = current.split("=");
                    if (variable.length == 2){
                        ACCESS_KEY = variable[1].trim();
                    }
                } else if (current.length() > 13 && current.substring(0, 13).equals("ACCESS_SECRET")){
                    String[] variable = current.split("=");
                    if (variable.length == 2){
                        ACCESS_SECRET = variable[1].trim();
                    }
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
     * <p>Get the OAuth API key from the 'Keys and tokens' tab of the Twitter 
     * app.</p>
     *
     * @return the String containing the OAuth API key.
     */
    public String getCONSUMER_KEY() {
        return CONSUMER_KEY;
    }
    
    /**
     *
     * <p>Get the OAuth API secret key from the 'Keys and tokens' tab of the 
     * Twitter app.</p>
     *
     * @return the String containing the OAuth API secret key.
     */
    public String getCONSUMER_SECRET() {
        return CONSUMER_SECRET;
    }
    
    /**
     *
     * <p>Get the OAuth access token from the 'Keys and tokens' tab of the 
     * Twitter app.</p>
     *
     * @return the String containing the OAuth access token.
     */
    public String getACCESS_KEY() {
        return ACCESS_KEY;
    }
    
    /**
     *
     * <p>Get the OAuth access token secret from the 'Keys and tokens' tab of 
     * the Twitter app.</p>
     *
     * @return the String containing the OAuth access token secret.
     */
    public String getACCESS_SECRET() {
        return ACCESS_SECRET;
    }
    
}
