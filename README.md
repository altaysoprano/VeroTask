# VeroTask

https://github.com/altaysoprano/VeroTask/assets/37440249/7d6dc95a-4fdd-452b-8c56-990f9ef9fb00

When the app is in offline mode, it displays data retrieved from local storage to the user. Simultaneously, it notifies the user with a toast message that the data couldn't be fetched. This applies both when swiping and when the main screen is initially loaded. However, this situation doesn't pose any issues when performing QR code scans or conducting searches through this method. Even in offline mode, the user can perform scans and search within the list.

Both during swiping and the initial loading of the main screen, the app checks whether the user is logged in. If the user isn't logged in, they are directed to the login screen. You can delve deeper into this scenario within the updateTasks and getTasks functions in BaseRepositoryImp. In updateTasks, which is invoked during swiping, the app first contacts the API because the user instructed the app to update data via swiping. If fetching data from the API is successful, the data is stored in the local database and immediately reflected to the user. If unsuccessful, it first checks if the local data is empty; if not, it displays the data to the user from the local database but notifies them with an error message that the data couldn't be fetched. If the data is empty, indicating both the local data is empty and data couldn't be fetched from the API, it only shows an error message. In any of these error cases, if it's an authentication error, the user is automatically directed to the login screen.

getTasks is the function called when the main screen is initially opened. Here, if the local database isn't empty, data is fetched from the local database. However, an attempt is still made to fetch data from the API because if the user isn't logged in, they need to be redirected to the login screen again. If the local database is empty, an attempt is made to fetch data from the API, and if successful, it's added to the local database. If unsuccessful, it means we don't have any data, and only an error message is displayed. As mentioned, if this is an authentication error, the user is redirected to the login screen.

The application also includes a Worker that updates the data every 60 minutes. This process uses the updateTasks from the repository. Since it runs in the background, it requires the user to either swipe or re-enter the app to reflect changes in the UI.

https://github.com/altaysoprano/VeroTask/assets/37440249/35c2855d-2c58-4210-979d-da063871cadf





