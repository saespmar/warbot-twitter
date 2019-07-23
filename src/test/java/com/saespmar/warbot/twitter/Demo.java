package com.saespmar.warbot.twitter;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Demo {
    
    // Warbot configuration
    private static Authentication auth;
    private static Battlefield battlefield;
    
    // Twitter configuration
    private static ConfigurationBuilder twitterConfigBuilder;
    private static Twitter twitter;
    
    public static void main(String[] args){
        
        // Warbot initialization
        auth = new Authentication();
        battlefield = new Battlefield(new File("example_battlefield/battle.txt"));
        
        // Twitter initialization
        twitterConfigBuilder = new ConfigurationBuilder();
        twitterConfigBuilder.setDebugEnabled(true);
        twitterConfigBuilder.setOAuthConsumerKey(auth.getCONSUMER_KEY());
        twitterConfigBuilder.setOAuthConsumerSecret(auth.getCONSUMER_SECRET());
        twitterConfigBuilder.setOAuthAccessToken(auth.getACCESS_KEY());
        twitterConfigBuilder.setOAuthAccessTokenSecret(auth.getACCESS_SECRET());
        twitter = new TwitterFactory(twitterConfigBuilder.build()).getInstance();
        
        
        // ========= Select either a single tweet or a scheduled tweet =========
        // singleTweet();
        scheduledTweet(12); // Every 12 hours
        
    }
    
    public static void singleTweet(){
        
        // Simulate fight
        Participant[] battle = battlefield.fight();
        if (battle == null){
            System.out.println("Tweet not sent. The game is over!");
            return;
        }
        battlefield.updateFile();
        File image = battlefield.drawTable();
        
        // Send tweet
        String text = battle[0].getName() + " kills " + battle[1].getName() + " [" + battlefield.getAlive() + " remaining]";
        if (battlefield.getAlive() < 2) text += ". The game has ended";
        StatusUpdate status = new StatusUpdate(text);
        status.setMedia(image);
        try {
            twitter.updateStatus(status);
        } catch (TwitterException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void scheduledTweet(int hours){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable(){
            @Override
            public void run(){
                singleTweet();
                if (battlefield.getAlive() < 2) scheduler.shutdown();
            }
        }, 0, hours, TimeUnit.HOURS);
    }
}
