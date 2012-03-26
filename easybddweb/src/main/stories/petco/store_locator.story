Store Locator Story

Narrative: 
Store Locator Function, we can locate a store.
As a user, we can get the current location.

#Scenario: Get Current Location
#Given open home page
#When click the store locator link
#Then get the store locator page button id is uselocation
#When click the button user location
#Then get the google map, the text is 'No stores found in the specified location'

Scenario: Search the Store Location with 78704
Given open home page
When click the store locator link
Then get the store locator page button id is uselocation
When type in the 78704 ZIP code
Then get some stores, see the map button

Scenario: Search the Store Location with 78730
Given open home page
When click the store locator link
Then get the store locator page button id is uselocation
When type in the 78730 ZIP code
Then get some stores, see the map button