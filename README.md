# movieapp-mvp
This version of the app is called movieapp-mvp, and provides a foundation for other samples in this project. The sample aims to:
- Provide a basic Model-View-Presenter (MVP) architecture without using any architectural frameworks.
- Act as a reference point for comparing and contrasting the other samples in this project.

## Libraries
This version of the app uses some other libraries:
- Picasso: used for loading, processing, caching and displaying remote and local images.
- ButterKnife: used for perform injection on objects, views and OnClickListeners.
- CardView: used for representing the information in a card manner with a drop shadow and corner radius which looks consistent across the platform.
- RecyclerView: The RecyclerView widget is a more advanced and flexible version of ListView.
- GSON: Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.
- Retrofit: This library used to send HTTP request to the server and retrieve response.
- ROOM Library: Room provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
- BlurView Library: It blurs its underlying content and draws it as a background for its children.

# The Movie DB API Key is required.
In order for the MoviesZone app to function properly as of January 7th, 2018 an API key for themoviedb.org must be included with the build.

Include the unique key for the build by adding the following line to util/Constants.java or find the TODO Line.

<code>
API_KEY = "";
</code>
