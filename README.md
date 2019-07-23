# Warbot for Twitter
<p align="center">
  <img src="https://img.shields.io/badge/version-1.0-blue.svg" alt="version">
</p>
Create a Twitter warbot that automatically tweets the state of a battle royale game. It randomly selects 2 participants from a list to fight each other: one wins, the other one dies. The last contender standing is the winner. The updates are periodically published on Twitter (including an image) until the game finishes.

## Getting Started

### Prerequisites
A [Twitter developer account](https://developer.twitter.com/) is needed to use this bot. Once the account is created, you'll see 4 codes in the 'Keys and tokens' section of your app. Place these codes in a file to allow the Java application the access of your account. You can find an example [here](config/tokens.env.example). This file can be placed wherever you want as long as it's referenced in the **Authentication** class constructor. The default path of this file is `config/tokens.env`.

Next, select a directory where all the content of the battle royale game will be stored. In the example from this repository, everything is inside the `example_battlefield` folder. The folder must contain a list of participants, one per line, as you can see [here](example_battlefield/battle.txt). If a line starts with `#`, this person is no longer in the game, since other player has killed it.

### Running
Create a main class similar to the provided in the [demo](src/test/java/com/saespmar/warbot/twitter/Demo.java). It's highly recommended to schedule the tweets using a tool such as `ScheduledExecutorService`.

If everything went OK, the tweets should look like this:
<p align="center">
  <img src="images/screenshot.png" alt="screenshot">
</p>


## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details