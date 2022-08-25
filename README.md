# JustEatSample

————————————————————————————
-The JustEatSample project is written in MVVM architecture, using Kotlin, live data, room, hilt, coroutine, and glide.

-The sort process starts by the following order: favorites, opening status, and sorting values.

-Whenever user clicks on like button, for the second time, favorites goes to the top of the list.

-Default sorting value is best match. Just one option can be applied each time.
 
-There is some modifications to the original json such as adding image url and uuid.

-Image url added to each item of JSON for better UI. Id also added to each item for finding a best solution to like the item , save and apply sort values.

-The response from mock api for the first time is from the following mock address: 

https://mocki.io/v1/4f3f95f7-e227-4831-b484-bc9058a3a9a7

but later on for next entrance to the app, the list will be loaded from data base.

-DayNight theme will be applied to the application depends on the dark mode on or off

-Unit test for repository , view models and dataBase has been added

