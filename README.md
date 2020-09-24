# SpotiApi hyperskill project
First Stage:
Using the Spotify authorization guide and the information given here (you need the section Authorization Code Flow), improve your program by adding real authorization on Spotify.

    Choose any free port on your machine (for example, 8080), and add the http://localhost:your_port to the whitelist of redirect_uri in your application settings on the Spotify site (Dashboard -> your app -> edit settings -> redirect URIs).
    Note that you should use the http protocol for localhost, not https, like in the Spotify example.)
    On the auth command, before printing the auth link (from the previous stage), you should start an HTTP server that will listen for the incoming requests. When the user confirms or rejects the authorization, the server should return the following text to the browser:
        "Got the code. Return back to your program." if the query contains the authorization code.
        "Authorization code not found. Try again." otherwise.
    This code is bound to each user who has a Spotify account and uses your app. Actually, you should ask this code once for each new user and save it somewhere.
    After the code is received, the server must shut down and you should get access_token by making a POST request on https://accounts.spotify.com/api/token with parameters described in the guide, and then print the response body.

Also, in this stage, you should read the Spotify access server point from the command line argument. Server path should be passed to the program using -access argument. If this argument is not set, you should use a default argument, https://accounts.spotify.com. Make sure you replace constants to this argument value everywhere!

//komentarz: do wybrania portu użyłem pseudolosowości bo coś się nie zgadzało z ich testami i jak bł jeden port to wyskakiwało, że jest zajęty, więc każdy test sobie losował port.

NEXT STAGE:

In this stage, you should replace your fake responses with the real ones. You should make requests to Spotify API and parse responses with the json parser.

Let's remember what information should be provided for each user request:

    featured — list of Spotify featured playlists with their links fetched from API;
    new — list of new albums with artists and links on Spotify;
    categories — list of all available categories on Spotify (just their names);
    playlists C_NAME, where C_NAME — name of category. List contains playlists of this category and their links on Spotify;

In case of invalid category id in playlist request or other api error, the program should output the error message from the Spotify response. For example, if you get the response {"error":{"status":404,"message":"Specified id doesn't exist"}}, you should print the following line: "Specified id doesn't exist".

Also, in this stage, you should read 2 server points from command line arguments:

    -access argument should provide authorization server path. The default value should be https://accounts.spotify.com
    -resource argument should provide api server path. The default value should be https://api.spotify.com

Make sure you replace constants to these argument values everywhere.

LAST STAGE:


According to the MVC pattern, reorganize your application into three components (the controller should read input and make requests to the API and update the view) and create a paginated output that will display 5 entries per page. You should be able to navigate through the pages using the commands next (see the next page) and prev (see the previous page). If the user is viewing the last page or the first one and calls next or prev, then you need to display a message: "No more pages." and stay at the same page. Also, under each output should be a message containing the number of the current page and how many total pages there are.

Don't forget about -access and -resource arguments and add another argument: a number of entries that should be shown on a page. Your program should process argument -page. If ths argument isn't set, you should use the default value 5.
