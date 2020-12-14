# news-feed
Showing news using pagination. Trying to use the latest jetpack libs and kotlin coroutines.

Here's a quick breakdown of how it was implemented:

1. Started by fetching the news from CBC API
   - Set up retrofit and okHttp
   - generate data classes using a sample JSON response
   
2. Build the UI with text
   - Decided to use the latest libs for pagination using coroutines, even though I had never
     used them before, because they seemed very promising. Coroutines did not look harder than Rx.
   - Followed the recommended steps to use ScrollView, data sources and ModelView
   - Data souce fetches only from network, but it could easily be extended to use database as well
     using Room, so we could get the app to show something offline
     
3. Added the image to the news cards
   - Using Glide is was pretty straightforward
   
4. Implemented filtering
   - Thought about using Filterable interfaces, but with coroutines it was easier to filter the
     source directly
   - Tried to work with hiding/showing the cards to keep the items lists unchanged, but UI got broken.
   - Not quite happy with the results, I can still think of some corner cases that will break the logic.
   
5. Write tests
   - Big struggle to set up instrumented tests
   - Mockserver did not work due to some dependency incompatibilities, unfortunately I did not have 
     time to investigate it further
   - Created a custom test runner and a test application so I could somehow mock the ViewModel to be 
     be able to show some data
   - All of that cost me a lot of time, so I ended up writing just two very silly tests
   - Unit tests followed the same path, I was not happy with anything so nothing was commited.
 
 Live code is available at: https://github.com/amattiuz/news-feed
