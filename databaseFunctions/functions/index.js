
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
const rootDatabase = admin.database();

exports.updateUserInfo = functions.database.ref('/entries/{ID}/{year}/{month}/{day}').onWrite( event => {
   var userId = event.params.ID;
   var today = new Date();
   const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
   var currentMonth = monthNames[today.getMonth()];
   var currentYear = today.getFullYear();
   if (currentMonth != event.params.month, currentYear != event.params.year, event.params.day == 0) {
     return;
   };

   var formItemsPromise = rootDatabase.ref('/pointEntryForm').once('value').then(function(formItemsSnapshot) {
     return new Promise(function(fulfill, reject) {
       var items = [];
       formItemsSnapshot.forEach(function(item) {
         items.push(item.child('name').val());
       });
       fulfill(items);
     });
   });

   formItemsPromise.then(function(formItems) {
     var dayPath = '/entries/' + userId + '/' + currentYear + '/' + currentMonth + '/' + event.params.day;
     rootDatabase.ref(dayPath).once('value').then(function(daySnapshot) {
       var dayTotal = 0;
       formItems.forEach(function(item) {
         dayTotal += daySnapshot.child(item).val();
       });
       rootDatabase.ref(dayPath).child('total').set(dayTotal);

       var monthPath = '/entries/' + userId + '/' + currentYear + '/' + currentMonth;
       rootDatabase.ref(monthPath).once('value').then(function(daysSnapshot) {
         var monthTotal = 0;
         daysSnapshot.forEach(function(day) {
           if (day.key != 0) {
             monthTotal += day.child('total').val();
           }
         });
         rootDatabase.ref(monthPath).child(0).child('total').set(monthTotal);
         console.log(monthTotal);
       });
     });
   });
});

exports.updateTeamInfo = functions.database.ref('/entries/{ID}/{year}/{month}').onWrite(event => {
    var userID = event.params.ID;
    var today = new Date();
    const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var currentMonth = monthNames[today.getMonth()];
    var currentYear = today.getFullYear();
    if (currentMonth != event.params.month, currentYear != event.params.year) {
      return;
    };

    var teamIndexPromise = rootDatabase.ref('/users/'+userID+'/team').once('value').then(function(userSnapshot) {
        var team = userSnapshot.val();
        return team;
      });

    var teamMembersPromise = teamIndexPromise.then(function(teamIndex) {
        return new Promise(function(fulfill, reject) {
            rootDatabase.ref('/teams/'+teamIndex+'/members').once('value').then(function(membersSnapshot) {
                var members = [];
                membersSnapshot.forEach(function(member) {
                    members.push(member.val());
                  });
                fulfill(members);
              });
          });
      });

    teamMembersPromise.then(function(memberIDs) {
        var memberInfoPromises = [];
        for (var i = 0; i < memberIDs.length; i++) {
          var promise = new Promise(function(fulfill, reject) {
              var personID = memberIDs[i];
              var path = '/entries/'+personID+'/'+currentYear+'/'+currentMonth;
              rootDatabase.ref(path).once('value').then(function(personInfoSnapshot) {
                  var exercisePoints = 0;
                  var snacks = 0;
                  var totalPoints = personInfoSnapshot.child('0').child('total').val();
                  personInfoSnapshot.forEach(function(entrySnapshot) {
                      exercisePoints += entrySnapshot.child('exercise').val();
                      snacks += entrySnapshot.child('snacks').val();
                    });
                  fulfill({personID:personID, exercise:exercisePoints, snacks:snacks, totalPoints:totalPoints});
                });
            });
          memberInfoPromises.push(promise);
        };
        var membersInfoPromise = Promise.all(memberInfoPromises);
        Promise.all([membersInfoPromise, teamIndexPromise]).then(function(results) {
            var membersInfo = results[0];
            var teamIndex = results[1];
            if (membersInfo.length < 1) {
              return;
            }
            var firstPerson = membersInfo[0];
            var meatheadPoints = firstPerson.exercise;
            var meatheadID = firstPerson.personID;
            var snackmeisterPoints = firstPerson.snacks;
            var snackmeisterID = firstPerson.personID;
            var teamPoints = firstPerson.totalPoints;
            for (var i = 1; i < membersInfo.length; i++) {
              var person = membersInfo[i];
              if (person.exercise > meatheadPoints) {
                meatheadPoints = person.exercise;
                meatheadID = person.personID;
              };
              if (person.snacks < snackmeisterPoints) {
                snackmeisterPoints = person.snacks;
                snackmeisterID = person.personID;
              };
              teamPoints += person.totalPoints;
            };
            var teamPath = '/teams/'+teamIndex+'/superlatives';
            var averagePoints = teamPoints / membersInfo.length;
            rootDatabase.ref(teamPath).child('meathead').set(meatheadID);
            rootDatabase.ref(teamPath).child('snackmeister').set(snackmeisterID);
            rootDatabase.ref('/teams/' + teamIndex).child('averagePoints').set(averagePoints);

          });
      });
  });
