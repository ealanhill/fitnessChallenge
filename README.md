# Fitness Challenge App

This is an app for a fitness challenge that I run at WillowTree Apps, Inc.

It allows participants to sign-in to the app and enter their scores for the day.
The app also displays the other participants in the users team, their points,
who is the "Meathead" (works out the most), or the "Snackmeister" (has the most
  snaks); where their current team is in the standings of all the teams.

### Scoring

When a user taps a date in the Calendar screen, they are displayed a dialog screen
for users to input their points. Scoring is done in the following ways:

 1. For each half-hour a person performs a physical activity during the day, they
    get 1 points
 2. Each time a participant eats some unhealthy food, they get a point deducted
 3. If the participant has met their fitness goal for the day, they get an extra
    point
 4. If the participant has met the plank challenge for the day, they get an extra
    point

All the points for the day are summed up and added to the participants total for
the month. Once that's done, all the teams average is taken, and that is how the
teams are placed within the standings. The team with the most points is in first
place, and so on.

### Architecture

The Architecture chosen for the app is Flux, unidirectional data flow. Each
fragment has it's own store and state. This is to keep the app from having a
god-class as a state (which is considered a code smell).
