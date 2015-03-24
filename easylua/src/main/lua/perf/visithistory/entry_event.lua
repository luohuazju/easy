-- position 1 
--   "latitude": 30.359988,
--   "longitude": -97.741921

-- position 2
--    "latitude": 30.403090, 
--    "longitude":  -97.726708


local fileutil = require("base/fileutil");

-- info for dev --
-- command to run 
-- wrk -c 100 -t 8 -d 1m --timeout 5m -s ./entry_event.lua  https://visithistory.console.dev.digby.com
brandCode = "visithistory"
apiId = "d9af5b47-dbc0-41f6-9a4b-44b1ffaab6ef"
uri = "/api/brands/visithistory/events"
method="POST"

request  = function()
   

   deviceId = 'device-ios-test' .. math.random(200)
   index = math.random(2)

   latitude = 30.359988
   longitude = -97.741921

   if index == 1 then
      latitude = 30.403090
      longitude = -97.726708
   end

   body = '{' ..
           '"geoFenceDelta": false,' ..
           '"appId": "' .. apiId .. '",' ..
           '"eventType": "DEVICE_REGISTRATION",' ..
           '"deviceId": "' .. deviceId .. '",' ..
           '"deviceRegKey": "' .. deviceId .. '",' ..
           '"deviceType": "PHONE",' ..
           '"osType": "IOS",' ..
           '"osVersion": "1.0",' ..
           '"latitude":' .. latitude .. ',' ..
           '"longitude":' .. longitude .. ',' ..
           '"timeZone": "America/Chicago",' ..
           '"accuracy": 60,'..
           '"searchRadius": 1600' ..
           '}'

   headers = {}
   headers["Content-Type"] = "application/json"
   
   reqbody =  wrk.format(method, uri, headers, body)
   -- print(reqbody .. '\n')
   return reqbody
end

response = function(status, headers, body)
   if(status ~= 200) then
      fileutil.appendResult("result.lua",body)
   end
end
