local fileutil = require("base/fileutil");

-- info for dev --
-- command to run 
-- wrk -c 100 -t 8 -d 1m --timeout 5m -s ./create_device.lua  https://visithistory.console.dev.digby.com
brandCode = "visithistory"
apiId = "d9af5b47-dbc0-41f6-9a4b-44b1ffaab6ef"
uri = "/api/brands/visithistory/events"
method="POST"
number = 0

request  = function()
   number = number + 1
   deviceId = 'device-ios-test' .. number

   body = '{"geoFenceDelta": false'..
          ',"appId":"' .. apiId .. '"' ..
          ',"eventType":"DEVICE_REGISTRATION"' ..
          ',"deviceId":"' .. deviceId .. '"' ..
          ',"deviceRegKey":"' .. deviceId .. '"' ..
          ',"deviceType":"PHONE"' ..
          ',"osType":"IOS"' ..
          ',"osVersion":"1.0"' ..
          ',"latitude": -62.5' ..
          ',"longitude": 98.5' ..
          ',"timeZone":"America/Chicago"' ..
          ',"accuracy":60' ..
          ',"searchRadius":1600' ..
          '}'

   headers = {}
   headers["Content-Type"] = "application/json"

   fileutil.appendResult("dic_device.lua",deviceId)
   
   reqbody =  wrk.format(method, uri, headers, body)
   --print(reqbody .. '\n')
   return reqbody
end

response = function(status, headers, body)
   if(status ~= 200) then
      fileutil.appendResult("result.lua",body)
   end
end
